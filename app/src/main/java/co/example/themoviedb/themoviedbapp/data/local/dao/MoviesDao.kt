package co.example.themoviedb.themoviedbapp.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import co.example.themoviedb.themoviedbapp.data.local.model.Movie

/**
 * Dao for [Movie] entity
 *
 * @author santiagoalvarez
 */
@Dao
abstract class MoviesDao : BaseDao<Movie> {

    @Query("SELECT * FROM movie")
    abstract fun getMovies(): List<Movie>

    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    abstract fun getPopularMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY voteAverage DESC")
    abstract fun getTopRatedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE releaseDate > date('now') ORDER BY releaseDate DESC")
    abstract fun getUpcomingMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE id = :id")
    abstract fun getMovie(id: Int): LiveData<Movie>

    @Query("DELETE FROM movie")
    abstract fun deleteAll()

    @Transaction
    open fun updateMovies(list: List<Movie>) {
        //TODO replace this, should be a more intelligent work: find, drop, insert
        deleteAll()
        insert(*list.toTypedArray())
    }
}