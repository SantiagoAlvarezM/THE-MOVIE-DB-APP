package co.example.themoviedb.themoviedbapp.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.base.BaseActivity
import co.example.themoviedb.themoviedbapp.base.ImageLoaderListener
import co.example.themoviedb.themoviedbapp.data.Status
import co.example.themoviedb.themoviedbapp.databinding.ActivityDetailBinding
import co.example.themoviedb.themoviedbapp.navigation.FragmentNavigationEntry
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity() {

    private lateinit var dataBinding: ActivityDetailBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    private var itemId: Int = 0


    companion object {
        const val EXTRA_ID = "id"

        fun createIntent(context: Context, id: Int) =
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(EXTRA_ID, id)
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        itemId = intent.getIntExtra(EXTRA_ID, 0)

        initViewModel()

        fab.setOnClickListener { view ->
            //TODO set movie as favorite
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.detailItem.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    supportPostponeEnterTransition()
                    it.data?.let {
                        dataBinding.item = it
                        dataBinding.listener = object : ImageLoaderListener {
                            override fun onLoadFail(e: Throwable) {
                                supportStartPostponedEnterTransition()
                            }

                            override fun onLoadSuccess() {
                                supportStartPostponedEnterTransition()
                            }
                        }
                        FragmentNavigationEntry.Builder(navigator, DetailFragment.newInstance(it.id))
                                .noPush()
                                .navigate()
                    }
                }
                Status.ERROR -> {
                    appBar.setExpanded(false)
                    FragmentNavigationEntry.Builder(navigator, DetailErrorFragment())
                            .noPush()
                            .navigate()
                }
                Status.LOADING -> {//no-op
                }
            }
        })
        viewModel.currentUserSelection.value = itemId
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
