package co.example.themoviedb.themoviedbapp.data

import android.arch.lifecycle.LiveData
import co.example.themoviedb.themoviedbapp.data.Resource
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration
import co.example.themoviedb.themoviedbapp.data.local.model.Movie
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail

/**
 * This manager is the entry point to data layer, exposes all available operations over data for clients
 *
 * @author santiagoalvarez
 */
interface MovieManager {

    /**
     * Get the [Configuration] wrapped in [Resource] to exposes it's state, returned as [LiveData]
     */
    fun getConfiguration(): LiveData<Resource<Configuration>>

    /**
     * Get a [List] of Popular [Movie]'s as [LiveData]
     */
    fun getPopularMovies(): LiveData<Resource<List<Movie>>>

    /**
     * Get the [MovieDetail] wrapped in [Resource] to exposes it's state, returned as [LiveData]
     * @param id of the wanted [MovieDetail]
     */
    fun getMovieDetail(id: Int): LiveData<Resource<MovieDetail>>
}