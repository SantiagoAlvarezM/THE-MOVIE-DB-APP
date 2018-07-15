package co.example.themoviedb.themoviedbapp.home

import android.databinding.BindingAdapter
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.data.Resource
import co.example.themoviedb.themoviedbapp.data.Status
import co.example.themoviedb.themoviedbapp.home.model.ListItem

/**
 * Utility object that handle [BindingAdapter] for Home section
 *
 * @author santiagoalvarez
 */
object HomeBindings {

    /**
     * Set the [Resource<List>] to the [recyclerView] adapter
     */
    @BindingAdapter("resource")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView?, resource: Resource<List<*>>?) {
        if (recyclerView == null || resource?.data == null) return
        with(recyclerView.adapter as HomeRecyclerViewAdapter) {
            updateData(resource.data as List<ListItem>)
            if (resource.status == Status.ERROR) {
                Snackbar.make(recyclerView, R.string.general_error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Set the [SwipeRefreshLayout.OnRefreshListener] to the [SwipeRefreshLayout] and notify to [UserInteractionListener]
     * of refresh actions
     */
    @BindingAdapter("refreshListener")
    @JvmStatic
    fun setSwipeRefreshListener(refreshLayout: SwipeRefreshLayout?, userInteractionListener: UserInteractionListener?) {
        if (refreshLayout == null || userInteractionListener == null) return
        refreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            userInteractionListener.onRefresh()
        })
    }

    /**
     * Set refreshing to the [SwipeRefreshLayout] depending of the [Resource] state
     */
    @BindingAdapter("setRefreshing")
    @JvmStatic
    fun setRefreshing(refreshLayout: SwipeRefreshLayout?, resource: Resource<*>?) {
        if (refreshLayout == null || resource == null) return
        with(refreshLayout) {
            isRefreshing = resource.status == Status.LOADING
        }
    }
}