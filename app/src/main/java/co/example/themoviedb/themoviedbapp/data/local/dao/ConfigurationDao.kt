package co.example.themoviedb.themoviedbapp.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration

/**
 * Dao for [Configuration] entity
 *
 * @author santiagoalvarez
 */
@Dao
abstract class ConfigurationDao : BaseDao<Configuration> {

    @Query("SELECT * FROM configuration")
    abstract fun getConfiguration(): LiveData<Configuration>

    @Query("DELETE FROM configuration")
    abstract fun deleteAll()

    @Transaction
    open fun updateConfiguration(configuration: Configuration) {
        deleteAll()
        insert(configuration)
    }
}