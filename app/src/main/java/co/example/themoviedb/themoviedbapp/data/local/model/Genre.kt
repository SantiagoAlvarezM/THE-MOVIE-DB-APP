package co.example.themoviedb.themoviedbapp.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Database model for Genre
 *
 * @author santiagoalvarez
 */
@Entity(tableName = "GENRE")
data class Genre(@PrimaryKey val id: Int, val name: String)