package co.example.themoviedb.themoviedbapp.home

import co.example.themoviedb.themoviedbapp.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Dagger [Module] for make [HomeFragment] injectable
 *
 * @author santiagoalvarez
 */
@Module
abstract class HomeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment
}