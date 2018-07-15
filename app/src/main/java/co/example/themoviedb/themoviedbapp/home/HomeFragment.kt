package co.example.themoviedb.themoviedbapp.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.example.themoviedb.themoviedbapp.App
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.databinding.FragmentHomeBinding
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment @Inject constructor() : DaggerFragment(), UserInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel
    private lateinit var dataBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(dataBinding) {
            userInteractionListener = this@HomeFragment
        }
        with(rCList) {
            adapter = HomeRecyclerViewAdapter(kotlin.collections.mutableListOf(), this@HomeFragment)
            layoutManager = android.support.v7.widget.LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.configuration.observe(this, Observer {
            if (it?.data != null) {
                App.instance.configuration = it.data
            }
        })
        viewModel.resources.observe(this, Observer {
            dataBinding.resource = it
        })
    }

    override fun onResume() {
        super.onResume()
        val selectedPosition = (activity as HomeActivity?)?.currentSelectedPosition
                ?: RecyclerView.NO_POSITION
        if (selectedPosition != RecyclerView.NO_POSITION) {
            rCList.scrollToPosition(selectedPosition)
            ((activity as HomeActivity).supportStartPostponedEnterTransition())
        }
    }

    override fun onRefresh() {
        viewModel.refreshData()
    }

    override fun onListItemClick(view: View, position: Int, id: Int) {
        (activity as HomeActivity?)?.navigateToDetail(view, position, id)
    }
}
