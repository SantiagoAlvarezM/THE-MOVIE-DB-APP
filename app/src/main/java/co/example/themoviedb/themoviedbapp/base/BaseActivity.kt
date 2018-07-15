package co.example.themoviedb.themoviedbapp.base

import android.os.Bundle
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.navigation.Navigator
import co.example.themoviedb.themoviedbapp.navigation.Navigator.NavigationListener
import dagger.android.support.DaggerAppCompatActivity

/**
 * BaseActivity responsible of init [Navigator] and Dagger
 *
 * @author santiagoalvarez
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity"
    }

    protected lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = Navigator(this, savedInstanceState, R.id.container, null)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            navigator.saveInstanceState(outState)
        }
    }
}