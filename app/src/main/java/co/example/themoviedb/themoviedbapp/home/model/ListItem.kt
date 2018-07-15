package co.example.themoviedb.themoviedbapp.home.model

import co.example.themoviedb.themoviedbapp.data.local.model.Movie

/**
 * Class that maps a [Movie] or [TvShow] to UI model for Home section
 *
 * @author santiagoalvarez
 */
data class ListItem(
        val id: Int,
        val releaseDate: String,
        val posterPath: String,
        val overview: String,
        val genreIds: List<Int>,
        val originalTitle: String,
        val originalLanguage: String,
        val title: String,
        val backdropPath: String,
        val popularity: Double,
        val voteCount: Int,
        val voteAverage: Float
) {
    constructor(movie: Movie) : this(
            movie.id,
            movie.releaseDate,
            movie.posterPath,
            movie.overview,
            movie.genreIds,
            movie.originalTitle,
            movie.originalLanguage,
            movie.title,
            movie.backdropPath,
            movie.popularity,
            movie.voteCount,
            movie.voteAverage.toFloat())
}