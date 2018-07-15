package co.example.themoviedb.themoviedbapp.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.data.Status
import co.example.themoviedb.themoviedbapp.databinding.FragmentDetailBinding
import co.example.themoviedb.themoviedbapp.detail.model.DetailItem
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class DetailFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding
    private var itemId: Int = 0
    private lateinit var detailItem: DetailItem

    companion object {
        const val EXTRA_ID = "id"

        fun newInstance(itemId: Int): DetailFragment {
            val args = Bundle().apply {
                putInt(EXTRA_ID, itemId)
            }
            return DetailFragment().apply { arguments = args }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = arguments?.getInt(EXTRA_ID)!!
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailItem.observe(activity!!, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    detailItem = it.data!!
                    with(dataBinding.rVDetailCredits) {
                        layoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                        layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
                        adapter = DetailRecyclerViewAdapter(detailItem.credits.cast.take(4).union(detailItem.credits.crew.take(4)).toList())
                    }
                    dataBinding.item = detailItem
                }
                Status.LOADING -> {// no-op
                }
                Status.ERROR -> {
                    Snackbar.make(activity!!.window.decorView, R.string.general_error, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
}
