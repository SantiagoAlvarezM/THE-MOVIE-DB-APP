package co.example.themoviedb.themoviedbapp.data

import android.arch.lifecycle.LiveData
import co.example.themoviedb.themoviedbapp.data.local.dao.ConfigurationDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MovieDetailDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MoviesDao
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration
import co.example.themoviedb.themoviedbapp.data.local.model.Movie
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail
import co.example.themoviedb.themoviedbapp.data.service.MoviesApiService
import co.example.themoviedb.themoviedbapp.data.service.model.ApiConfigurationResponse
import co.example.themoviedb.themoviedbapp.data.service.model.ApiMovie
import co.example.themoviedb.themoviedbapp.data.service.model.ApiMovieDetail
import co.example.themoviedb.themoviedbapp.data.service.model.BaseApiResponse
import co.example.themoviedb.themoviedbapp.util.apiMoviesToMovies
import co.example.themoviedb.themoviedbapp.util.daysBetween
import co.example.themoviedb.themoviedbapp.util.toConfiguration
import co.example.themoviedb.themoviedbapp.util.toMovieDetail
import retrofit2.Call
import java.util.*
import javax.inject.Inject

/**
 * Implementation of the [MovieManager]
 *
 * @author santiagoalvarez
 */
class MovieManagerImpl @Inject constructor(
        private val moviesApiService: MoviesApiService,
        private val configurationDao: ConfigurationDao,
        private var moviesDao: MoviesDao,
        private val movieDetailDao: MovieDetailDao) : MovieManager {

    private val MAX_CONFIG_AGE = 5
    private var dirty: Boolean = false

    fun refreshData() {
        dirty = true
    }

    override fun getConfiguration(): LiveData<Resource<Configuration>> {
        return object : NetworkBoundResource<Configuration, ApiConfigurationResponse>() {

            override fun saveCallResult(item: ApiConfigurationResponse) {
                configurationDao.updateConfiguration(item.toConfiguration())
            }

            override fun loadFromDb(): LiveData<Configuration> {
                return configurationDao.getConfiguration()
            }

            override fun createCall(): Call<ApiConfigurationResponse> {
                return moviesApiService.getConfiguration()
            }

            override fun shouldFetch(data: Configuration?): Boolean {
                return data == null || Date().daysBetween(data.dateAdded) > MAX_CONFIG_AGE
            }
        }.asLiveData
    }

    override fun getPopularMovies(): LiveData<Resource<List<Movie>>> {
        val request: Call<BaseApiResponse<ApiMovie>> = moviesApiService.getPopularMovies()
        val response: LiveData<List<Movie>> = moviesDao.getPopularMovies()

        return object : NetworkBoundResource<List<Movie>, BaseApiResponse<ApiMovie>>() {

            override fun saveCallResult(item: BaseApiResponse<ApiMovie>) {
                moviesDao.updateMovies(item.results.apiMoviesToMovies())
            }

            override fun loadFromDb(): LiveData<List<Movie>> {
                return response
            }

            override fun createCall(): Call<BaseApiResponse<ApiMovie>> {
                return request
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return dirty || data == null || data.isEmpty()
            }

        }.asLiveData
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieDetail>> {
        return object : NetworkBoundResource<MovieDetail, ApiMovieDetail>() {

            override fun saveCallResult(item: ApiMovieDetail) {
                movieDetailDao.insert(item.toMovieDetail())
            }

            override fun loadFromDb(): LiveData<MovieDetail> {
                return movieDetailDao.getMovieDetail(id)
            }

            override fun createCall(): Call<ApiMovieDetail> {
                return moviesApiService.getMovieDetail(id)
            }

            override fun shouldFetch(data: MovieDetail?): Boolean {
                return dirty || data == null
            }
        }.asLiveData
    }
}