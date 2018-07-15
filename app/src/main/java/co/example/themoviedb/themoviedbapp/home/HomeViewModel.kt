package co.example.themoviedb.themoviedbapp.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import co.example.themoviedb.themoviedbapp.data.MovieManagerImpl
import co.example.themoviedb.themoviedbapp.data.Resource
import co.example.themoviedb.themoviedbapp.data.Status
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration
import co.example.themoviedb.themoviedbapp.data.local.model.Movie
import co.example.themoviedb.themoviedbapp.home.model.ListItem
import co.example.themoviedb.themoviedbapp.util.moviesToListItems
import javax.inject.Inject

/**
 * @author santiagoalvarez
 */
class HomeViewModel @Inject constructor(private val manager: MovieManagerImpl) : ViewModel() {

    var configuration: LiveData<Resource<Configuration>> = manager.getConfiguration()
    val resources: LiveData<Resource<List<ListItem>>> = loadData()

    fun refreshData() {
        manager.refreshData()
    }

    fun loadData(): LiveData<Resource<List<ListItem>>> {
        return Transformations.map(loadPopularMovies()) {
            when (it.status) {
                Status.SUCCESS -> Resource.success(it.data?.moviesToListItems())
                Status.LOADING -> Resource.loading(it.data?.moviesToListItems())
                Status.ERROR -> Resource.error(it.message, it.data?.moviesToListItems())
            }
        }
    }

    private fun loadPopularMovies(): LiveData<Resource<List<Movie>>> = manager.getPopularMovies()
}