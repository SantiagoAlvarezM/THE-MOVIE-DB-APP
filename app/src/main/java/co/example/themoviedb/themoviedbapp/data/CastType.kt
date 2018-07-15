package co.example.themoviedb.themoviedbapp.data

/**
 * @author santiagoalvarez
 */
sealed class CastType(val castName: String, val castProfilePath: String?)

data class Cast(
        val castId: Int,
        val character: String,
        val creditId: String,
        val gender: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val profilePath: String?
) : CastType(name, profilePath)

data class Crew(
        val creditId: String,
        val department: String,
        val gender: Int,
        val id: Int,
        val job: String,
        val name: String,
        val profilePath: String?
) : CastType(name, profilePath)

