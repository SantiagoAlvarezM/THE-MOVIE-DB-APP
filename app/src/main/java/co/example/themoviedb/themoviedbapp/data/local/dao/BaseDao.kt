package co.example.themoviedb.themoviedbapp.data.local.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

/**
 * Base class that declares common transactions
 *
 * @author santiagoalvarez
 */
interface BaseDao<T> {

    @Insert
    fun insert(obj: T)

    @Insert
    fun insert(vararg list: T)

    @Update
    fun update(obj: T)

    @Update
    fun update(vararg list: T)

    @Delete
    fun delete(obj: T)

    @Delete
    fun delete(vararg list: T)
}