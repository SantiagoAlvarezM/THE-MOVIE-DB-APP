package co.example.themoviedb.themoviedbapp.home

import android.view.View

/**
 * Callback that notifies user interactions on Home section
 *
 * @author santiagoalvarez
 */
interface UserInteractionListener {

    fun onRefresh()

    fun onListItemClick(view: View, position: Int, id: Int)
}