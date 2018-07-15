package co.example.themoviedb.themoviedbapp.data.service.model

import com.google.gson.annotations.SerializedName

/**
 * @author santiagoalvarez
 */
class BaseApiResponse<T>(
        @SerializedName("page")
        val page: Int,
        @SerializedName("results")
        val results: List<T>,
        @SerializedName("total_results")
        val totalResults: Int,
        @SerializedName("total_pages")
        val totalPages: Int
)