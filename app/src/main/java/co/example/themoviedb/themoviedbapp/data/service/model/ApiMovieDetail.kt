package co.example.themoviedb.themoviedbapp.data.service.model

import com.google.gson.annotations.SerializedName

/**
 * @author santiagoalvarez
 */
data class ApiMovieDetail(
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
        @SerializedName("belongs_to_collection") val belongsToCollection: Any,
        @SerializedName("budget") val budget: Int,
        @SerializedName("genres") val genres: List<ApiNameIdPair>,
        @SerializedName("homepage") val homepage: String,
        @SerializedName("imdb_id") val imdbId: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("production_companies") val apiProductionCompanies: List<ApiNameIdPair>,
        @SerializedName("production_countries") val apiProductionCountries: List<ApiProductionCountry>,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("revenue") val revenue: Int,
        @SerializedName("runtime") val runtime: Int,
        @SerializedName("spoken_languages") val apiSpokenLanguages: List<ApiSpokenLanguage>,
        @SerializedName("status") val status: String,
        @SerializedName("tagline") val tagline: String,
        @SerializedName("title") val title: String,
        @SerializedName("video") val video: Boolean,
        @SerializedName("credits") val credits: ApiCredits
)

data class ApiSpokenLanguage(
        @SerializedName("iso_639_1") val iso: String,
        @SerializedName("name") val name: String
)

data class ApiProductionCountry(
        @SerializedName("iso_3166_1") val iso: String,
        @SerializedName("name") val name: String
)

data class ApiCredits(
        @SerializedName("cast") val cast: List<ApiCast>,
        @SerializedName("crew") val crew: List<ApiCrew>
)

data class ApiCrew(
        @SerializedName("credit_id") val creditId: String,
        @SerializedName("department") val department: String,
        @SerializedName("gender") val gender: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("job") val job: String,
        @SerializedName("name") val name: String,
        @SerializedName("profile_path") val profilePath: String
)

data class ApiCast(
        @SerializedName("cast_id") val castId: Int,
        @SerializedName("character") val character: String,
        @SerializedName("credit_id") val creditId: String,
        @SerializedName("gender") val gender: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("order") val order: Int,
        @SerializedName("profile_path") val profilePath: String
)