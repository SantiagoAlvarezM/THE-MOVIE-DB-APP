package co.example.themoviedb.themoviedbapp.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail

/**
 * Dao for [MovieDetail] entity
 *
 * @author santiagoalvarez
 */
@Dao
abstract class MovieDetailDao : BaseDao<MovieDetail> {

    @Query("SELECT * FROM movie_detail WHERE id = :id")
    abstract fun getMovieDetail(id: Int): LiveData<MovieDetail>

    @Query("DELETE FROM movie_detail")
    abstract fun deleteAll()

    @Transaction
    open fun updateMovieDetail(movieDetail: MovieDetail) {
        deleteAll()
        insert(movieDetail)
    }
}