package co.example.themoviedb.themoviedbapp.detail

import android.databinding.BindingAdapter
import android.widget.TextView
import co.example.themoviedb.themoviedbapp.data.Cast
import co.example.themoviedb.themoviedbapp.data.CastType
import co.example.themoviedb.themoviedbapp.data.Crew
import co.example.themoviedb.themoviedbapp.data.local.model.Genre

/**
 * Utility object that handle [BindingAdapter] for Detail section
 *
 * @author santiagoalvarez
 */
object DetailBindings {

    /**
     * Maps a [List<Genre>] to a [List<String>] to the given [TextView], it uses the [Genre.name] to create the list
     */
    @BindingAdapter("genres")
    @JvmStatic
    fun setGenres(textView: TextView?, list: List<Genre>?) {
        if (textView == null || list == null || list.isEmpty()) return
        val name = list.map { it.name.plus(", ") }
        textView.text = name.toString().removePrefix("[").removeSuffix("]")
    }

    /**
     * Set the [castType] to the given [textView] to display character/job text to the view
     */
    @BindingAdapter("cast")
    @JvmStatic
    fun setCast(textView: TextView?, castType: CastType?) {
        if (textView == null || castType == null) return
        textView.text = when (castType) {
            is Cast -> castType.character
            is Crew -> castType.job
        }
    }
}