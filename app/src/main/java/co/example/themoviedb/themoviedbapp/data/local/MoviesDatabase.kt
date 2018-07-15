package co.example.themoviedb.themoviedbapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import co.example.themoviedb.themoviedbapp.data.local.dao.ConfigurationDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MovieDetailDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MoviesDao
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration
import co.example.themoviedb.themoviedbapp.data.local.model.Movie
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail
import co.example.themoviedb.themoviedbapp.util.Converters

/**
 * Class that represent a SQLite Database
 *
 * @author santiagoalvarez
 */
@Database(entities = [(Configuration::class), (Movie::class), (MovieDetail::class)], version = 2)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun configurationDao(): ConfigurationDao

    abstract fun moviesDao(): MoviesDao

    abstract fun movieDetailDao(): MovieDetailDao

}