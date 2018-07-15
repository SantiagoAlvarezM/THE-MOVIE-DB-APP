package co.example.themoviedb.themoviedbapp.detail

import co.example.themoviedb.themoviedbapp.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Dagger [Module] for make [HomeFragment] injectable
 *
 * @author santiagoalvarez
 */
@Module
abstract class DetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindDetailFragment(): DetailFragment
}