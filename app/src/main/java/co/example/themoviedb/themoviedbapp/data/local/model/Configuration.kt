package co.example.themoviedb.themoviedbapp.data.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import co.example.themoviedb.themoviedbapp.data.Image
import java.util.*

/**
 * Database model for Configuration
 *
 * @author santiagoalvarez
 */
@Entity(tableName = "CONFIGURATION")
data class Configuration(var image: Image, var changeKeys: List<String>) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var dateAdded: Long = Date().time
}