package co.example.themoviedb.themoviedbapp.data.service.model

import com.google.gson.annotations.SerializedName

/**
 * @author santiagoalvarez
 */
data class ApiNameIdPair(@SerializedName("id") val id: Int, @SerializedName("name") val name: String)