package co.example.themoviedb.themoviedbapp.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Database model for Movie
 *
 * @author santiagoalvarez
 */
@Entity(tableName = "MOVIE")
data class Movie(
        @PrimaryKey var id: Int,
        var overview: String,
        var posterPath: String,
        var backdropPath: String,
        var genreIds: List<Int>,
        var popularity: Double,
        var voteCount: Int,
        var voteAverage: Double,
        var adult: Boolean,
        var releaseDate: String,
        var originalTitle: String,
        var originalLanguage: String,
        var title: String,
        var video: Boolean
)