package co.example.themoviedb.themoviedbapp.data.service

import co.example.themoviedb.themoviedbapp.data.service.model.ApiConfigurationResponse
import co.example.themoviedb.themoviedbapp.data.service.model.ApiMovie
import co.example.themoviedb.themoviedbapp.data.service.model.ApiMovieDetail
import co.example.themoviedb.themoviedbapp.data.service.model.BaseApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author santiagoalvarez
 */
interface MoviesApiService {

    @GET("configuration?api_key")
    fun getConfiguration(): Call<ApiConfigurationResponse>

    @GET("movie/popular?api_key&page=1")
    fun getPopularMovies(): Call<BaseApiResponse<ApiMovie>>

    @GET("movie/{movie_id}?api_key&append_to_response=credits")
    fun getMovieDetail(@Path("movie_id") id: Int): Call<ApiMovieDetail>
}