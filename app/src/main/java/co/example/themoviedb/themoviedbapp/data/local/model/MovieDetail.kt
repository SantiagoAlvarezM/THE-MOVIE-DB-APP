package co.example.themoviedb.themoviedbapp.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import co.example.themoviedb.themoviedbapp.data.Cast
import co.example.themoviedb.themoviedbapp.data.Crew

/**
 * Database model for Movie detail with
 *
 * @author santiagoalvarez
 */
@Entity(tableName = "MOVIE_DETAIL")
data class MovieDetail(
        var adult: Boolean,
        var backdropPath: String,
        var budget: Int,
        var homepage: String,
        @PrimaryKey var id: Int,
        var imdbId: String,
        var originalLanguage: String,
        var originalTitle: String,
        var overview: String,
        var popularity: Double,
        var posterPath: String,
        var productionCompanies: List<ProductionCompany>,
        var productionCountries: List<ProductionCountry>,
        var releaseDate: String,
        var revenue: Int,
        var runtime: Int,
        var spokenLanguages: List<SpokenLanguage>,
        var status: String,
        var tagline: String,
        var title: String,
        var video: Boolean,
        var voteAverage: Double,
        var voteCount: Int,
        val credits: Credits
)

data class SpokenLanguage(var iso: String, var name: String)

data class ProductionCountry(var iso: String, var name: String)

data class ProductionCompany(var id: Int, var name: String)

data class Credits(val cast: List<Cast>, val crew: List<Crew>)

data class Network(var id: Int, var name: String)