package co.example.themoviedb.themoviedbapp.di

import co.example.themoviedb.themoviedbapp.detail.DetailViewModel
import co.example.themoviedb.themoviedbapp.home.HomeViewModel
import dagger.Subcomponent

/**
 * Dagger [Subcomponent] responsible of create View Model instances
 *
 * @author santiagoalvarez
 */
@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun homeViewModel(): HomeViewModel
    fun detailViewModel(): DetailViewModel
}