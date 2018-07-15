package co.example.themoviedb.themoviedbapp.util

import co.example.themoviedb.themoviedbapp.data.Cast
import co.example.themoviedb.themoviedbapp.data.Crew
import co.example.themoviedb.themoviedbapp.data.Image
import co.example.themoviedb.themoviedbapp.data.local.model.*
import co.example.themoviedb.themoviedbapp.data.service.model.*
import co.example.themoviedb.themoviedbapp.detail.model.DetailItem
import co.example.themoviedb.themoviedbapp.home.model.ListItem

/**
 * Kotlin File that holds mappers to transform data between layers
 *
 * @author santiagoalvarez
 */

fun Movie.toListItem() = ListItem(this)

fun List<Movie>.moviesToListItems() = map { it.toListItem() }

fun ApiImages.toImage() = Image(baseUrl, secureBaseUrl, backdropSizes, logoSizes, posterSizes, profileSizes, stillSizes)

fun ApiConfigurationResponse.toConfiguration() = Configuration(apiImages.toImage(), changeKeys)

fun ApiMovie.toMovie() = Movie(id, overview, posterPath, backdropPath, genreIds, popularity, voteCount, voteAverage, adult, releaseDate, originalTitle, originalLanguage, title, video)

fun List<ApiMovie>.apiMoviesToMovies() = map { it.toMovie() }

fun ApiMovieDetail.toMovieDetail() = MovieDetail(
        adult,
        backdropPath,
        budget,
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        apiProductionCompanies.toProductionCompanies(),
        apiProductionCountries.toProductionCountries(),
        releaseDate,
        revenue,
        runtime,
        apiSpokenLanguages.toSpokenLanguages(),
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount,
        credits.toCredits())

fun ApiNameIdPair.toNetwork() = Network(id, name)

fun ApiNameIdPair.toGenre() = Genre(id, name)

fun ApiNameIdPair.toProductionCompany() = ProductionCompany(id, name)

fun List<ApiNameIdPair>.toGenres() = map { it.toGenre() }

fun List<ApiNameIdPair>.toNetworks() = map { it.toNetwork() }

fun List<ApiNameIdPair>.toProductionCompanies() = map { it.toProductionCompany() }

fun ApiSpokenLanguage.toSpokenLanguage() = SpokenLanguage(iso, name)

fun ApiProductionCountry.toProductionCountry() = ProductionCountry(iso, name)

fun List<ApiSpokenLanguage>.toSpokenLanguages() = map { it.toSpokenLanguage() }

fun List<ApiProductionCountry>.toProductionCountries() = map { it.toProductionCountry() }

fun ApiCredits.toCredits() = Credits(cast.toCasts(), crew.toCrews())

fun List<ApiCast>.toCasts() = map { it.toCast() }

fun ApiCast.toCast() = Cast(castId, character, creditId, gender, id, name, order, profilePath)

fun List<ApiCrew>.toCrews() = map { it.toCrew() }

fun ApiCrew.toCrew() = Crew(creditId, department, gender, id, job, name, profilePath)

fun MovieDetail.movieDetailToDetailItem() = DetailItem(
        adult,
        backdropPath,
        budget,
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        productionCompanies,
        productionCountries,
        releaseDate,
        revenue,
        runtime,
        spokenLanguages.map { it.name },
        status,
        tagline,
        title,
        video,
        voteAverage.toFloat(),
        voteCount,
        credits)