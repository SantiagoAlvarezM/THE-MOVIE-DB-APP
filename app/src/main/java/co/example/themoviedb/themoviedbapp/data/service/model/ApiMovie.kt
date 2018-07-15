package co.example.themoviedb.themoviedbapp.data.service.model

import com.google.gson.annotations.SerializedName

/**
 * @author santiagoalvarez
 */
data class ApiMovie(
        @SerializedName("id") val id: Int,
        @SerializedName("overview") val overview: String,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("title") val title: String,
        @SerializedName("video") val video: Boolean
)