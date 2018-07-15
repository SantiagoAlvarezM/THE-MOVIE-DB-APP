package co.example.themoviedb.themoviedbapp.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.home.HomeActivity
import co.example.themoviedb.themoviedbapp.navigation.IntentNavigationEntry
import co.example.themoviedb.themoviedbapp.navigation.Navigator

/**
 * @author santiagoalvarez
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var navigator: Navigator
    private val handler = Handler()
    private val runnable = Runnable {
        IntentNavigationEntry.Builder(navigator, HomeActivity.createIntent(this))
                .clearStack()
                .navigate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        navigator = Navigator(this, savedInstanceState, R.id.container, null)
        supportActionBar?.hide()
        handler.postDelayed(runnable, NAVIGATION_TRANSACTION_DELAY.toLong())
    }

    companion object {

        private val NAVIGATION_TRANSACTION_DELAY = 500
    }
}
