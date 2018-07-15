package co.example.themoviedb.themoviedbapp.data

/**
 * Data class for Image
 *
 * @author santiagoalvarez
 */
data class Image(
        var baseUrl: String,
        var secureBaseUrl: String,
        var backdropSizes: List<String>,
        var logoSizes: List<String>,
        var posterSizes: List<String>,
        var profileSizes: List<String>,
        var stillSizes: List<String>
)