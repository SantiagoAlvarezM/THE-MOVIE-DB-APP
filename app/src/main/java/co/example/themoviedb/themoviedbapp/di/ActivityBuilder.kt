package co.example.themoviedb.themoviedbapp.di

import co.example.themoviedb.themoviedbapp.detail.DetailActivity
import co.example.themoviedb.themoviedbapp.detail.DetailModule
import co.example.themoviedb.themoviedbapp.home.HomeActivity
import co.example.themoviedb.themoviedbapp.home.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger [Module] responsible of create sub components of activities that includes the specified modules for parent [AppComponent]
 *
 * @author santiagoalvarez
 */
@Module
abstract class ActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindHomeActivity(): HomeActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [DetailModule::class])
    internal abstract fun bindDetailActivity(): DetailActivity
}