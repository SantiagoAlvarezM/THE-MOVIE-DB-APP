package co.example.themoviedb.themoviedbapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.SharedElementCallback
import android.support.v7.widget.RecyclerView
import android.view.View
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.base.BaseActivity
import co.example.themoviedb.themoviedbapp.detail.DetailActivity
import co.example.themoviedb.themoviedbapp.navigation.FragmentNavigationEntry
import co.example.themoviedb.themoviedbapp.navigation.IntentNavigationEntry
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private val CURRENT_SELECTED_POSITION_KEY = "current_selected_position"
    var currentSelectedPosition: Int = RecyclerView.NO_POSITION

    @Inject
    lateinit var homeFragmentProvider: Lazy<HomeFragment>

    companion object {

        fun createIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) {
            currentSelectedPosition = savedInstanceState[CURRENT_SELECTED_POSITION_KEY] as Int
        }

        initSharedTransitions()

        val homeFragment = supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName) as HomeFragment?
                ?: homeFragmentProvider.get()
        FragmentNavigationEntry.Builder(navigator, homeFragment)
                .noPush()
                .withTitle(getString(R.string.home_title))
                .navigate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(CURRENT_SELECTED_POSITION_KEY, currentSelectedPosition)
        super.onSaveInstanceState(outState)
    }

    fun navigateToDetail(view: View, position: Int, id: Int) {
        currentSelectedPosition = position
        IntentNavigationEntry.Builder(navigator, DetailActivity.createIntent(this, id))
                .withOptions(ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, id.toString()).toBundle())
                .navigate()
    }

    private fun initSharedTransitions() {
        supportPostponeEnterTransition()
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
                super.onMapSharedElements(names, sharedElements)
                val viewHolder = rCList.findViewHolderForAdapterPosition(currentSelectedPosition)
                if (viewHolder != null && names != null && !names.isEmpty() && sharedElements != null) {
                    sharedElements[names[0]] = viewHolder.itemView.findViewById(R.id.itemImage)
                }
            }
        })
    }
}
