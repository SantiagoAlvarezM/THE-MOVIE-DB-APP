package co.example.themoviedb.themoviedbapp.detail.model

import co.example.themoviedb.themoviedbapp.data.local.model.Credits
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail
import co.example.themoviedb.themoviedbapp.data.local.model.ProductionCompany
import co.example.themoviedb.themoviedbapp.data.local.model.ProductionCountry

/**
 * Class that maps a [MovieDetail] or [TvShowDetail] to UI model for Detail section
 *
 * @author santiagoalvarez
 */
data class DetailItem(
        val adult: Boolean,
        val backdropPath: String,
        val budget: Int,
        val homepage: String,
        val id: Int,
        val imdbId: String,
        val originalLanguage: String,
        val originalTitle: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val productionCompanies: List<ProductionCompany>,
        val productionCountries: List<ProductionCountry>?,
        val releaseDate: String,
        val revenue: Int,
        val runtime: Int,
        val spokenLanguages: List<String>,
        val status: String,
        val tagline: String,
        val title: String,
        val video: Boolean,
        val voteAverage: Float,
        val voteCount: Int,
        val credits: Credits)