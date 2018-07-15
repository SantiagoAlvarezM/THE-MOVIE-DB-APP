package co.example.themoviedb.themoviedbapp.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import co.example.themoviedb.themoviedbapp.data.MovieManagerImpl
import co.example.themoviedb.themoviedbapp.data.Resource
import co.example.themoviedb.themoviedbapp.data.Status
import co.example.themoviedb.themoviedbapp.data.local.model.MovieDetail
import co.example.themoviedb.themoviedbapp.detail.model.DetailItem
import co.example.themoviedb.themoviedbapp.util.movieDetailToDetailItem
import javax.inject.Inject

/**
 * Class that represents a [ViewModel] for Detail section. Responsible of communication between Views and data layers
 *
 * @author santiagoalvarez
 */
class DetailViewModel @Inject constructor(private val manager: MovieManagerImpl) : ViewModel() {

    var currentUserSelection = MutableLiveData<Int>()
    var detailItem: LiveData<Resource<DetailItem>> = Transformations.switchMap(currentUserSelection) {
        loadData(it)
    }

    private fun loadData(id: Int): LiveData<Resource<DetailItem>> {
        return Transformations.map(loadMovieDetail(id)) {
            when (it.status) {
                Status.SUCCESS -> Resource.success(it.data?.movieDetailToDetailItem())
                Status.LOADING -> Resource.loading(it.data?.movieDetailToDetailItem())
                Status.ERROR -> Resource.error(it.message, it.data?.movieDetailToDetailItem())
            }
        }
    }

    private fun loadMovieDetail(id: Int): LiveData<Resource<MovieDetail>> = manager.getMovieDetail(id)
}