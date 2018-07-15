package co.example.themoviedb.themoviedbapp.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import co.example.themoviedb.themoviedbapp.detail.DetailViewModel
import co.example.themoviedb.themoviedbapp.di.ViewModelSubComponent
import co.example.themoviedb.themoviedbapp.home.HomeViewModel
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory in charge of provide [ViewModel] instances to consumers
 *
 * [Source](https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-2-di-1a6b1f96d84b)
 */
@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, Callable<out ViewModel>> = ArrayMap()

    init {
        creators[HomeViewModel::class.java] = Callable<ViewModel> { viewModelSubComponent.homeViewModel() }
        creators[DetailViewModel::class.java] = Callable<ViewModel> { viewModelSubComponent.detailViewModel() }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Callable<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class " + modelClass)
        }
        try {
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
