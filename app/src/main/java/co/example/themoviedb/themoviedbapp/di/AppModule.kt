package co.example.themoviedb.themoviedbapp.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import co.example.themoviedb.themoviedbapp.base.ViewModelFactory
import co.example.themoviedb.themoviedbapp.data.local.MoviesDatabase
import co.example.themoviedb.themoviedbapp.data.local.dao.ConfigurationDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MovieDetailDao
import co.example.themoviedb.themoviedbapp.data.local.dao.MoviesDao
import co.example.themoviedb.themoviedbapp.data.service.ApiConfig.API_BASE_URL
import co.example.themoviedb.themoviedbapp.data.service.ApiConfig.API_KEY
import co.example.themoviedb.themoviedbapp.data.service.ApiConfig.API_KEY_QUERY
import co.example.themoviedb.themoviedbapp.data.service.MoviesApiService
import co.example.themoviedb.themoviedbapp.data.service.QueryParamInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Dagger [Module] responsible of make Application-wide components injectable
 *
 * @author santiagoalvarez
 */
@Module(subcomponents = [(ViewModelSubComponent::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideMoviesApiService(): MoviesApiService {
        val okHttpClientBuilder = OkHttpClient.Builder()
        val interceptor = QueryParamInterceptor(API_KEY_QUERY, API_KEY)
        if (!okHttpClientBuilder.interceptors().contains(interceptor)) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build()
                .create(MoviesApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(context.applicationContext,
                MoviesDatabase::class.java, "Movies.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideConfigurationDao(db: MoviesDatabase): ConfigurationDao = db.configurationDao()

    @Singleton
    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao = db.moviesDao()

    @Singleton
    @Provides
    fun provideMovieDetailDao(db: MoviesDatabase): MovieDetailDao = db.movieDetailDao()

    @Singleton
    @Provides
    fun provideViewModelFactory(viewModelSubComponent: ViewModelSubComponent.Builder): ViewModelProvider.Factory =
            ViewModelFactory(viewModelSubComponent.build())
}