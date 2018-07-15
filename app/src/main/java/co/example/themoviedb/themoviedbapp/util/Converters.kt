package co.example.themoviedb.themoviedbapp.util

import android.arch.persistence.room.TypeConverter
import co.example.themoviedb.themoviedbapp.data.Cast
import co.example.themoviedb.themoviedbapp.data.Crew
import co.example.themoviedb.themoviedbapp.data.Image
import co.example.themoviedb.themoviedbapp.data.local.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Class that holds [TypeConverter]'s to map custom data objects to objects that Room can store
 *
 * @author santiagoalvarez
 */
class Converters {

    @TypeConverter
    fun fromStringList(strings: List<String>) = strings.joinToString(",")

    @TypeConverter
    fun toStringList(string: String) = string.split(",").map { it.trim() }

    @TypeConverter
    fun fromIntList(integers: List<Int>) = integers.joinToString(",")

    @TypeConverter
    fun toIntList(string: String?) = string?.split(",")?.map { it.trim().toInt() }
            ?: emptyList<Int>()

    @TypeConverter
    fun fromImage(image: Image): String = Gson().toJson(image)

    @TypeConverter
    fun fromImageJson(string: String): Image = Gson().fromJson<Image>(string, Image::class.java)

    @TypeConverter
    fun fromGenreList(genres: List<Genre>): String = Gson().toJson(genres)

    @TypeConverter
    fun fromGenreJson(string: String): List<Genre> = Gson().fromJson<List<Genre>>(string, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromProductionCompany(list: List<ProductionCompany>): String = Gson().toJson(list)

    @TypeConverter
    fun fromProductionCompanyJson(string: String): List<ProductionCompany> = Gson().fromJson<List<ProductionCompany>>(string, object : TypeToken<List<ProductionCompany>>() {}.type)

    @TypeConverter
    fun fromProductionCountry(list: List<ProductionCountry>): String = Gson().toJson(list)

    @TypeConverter
    fun fromProductionCountryJson(string: String) = Gson().fromJson<List<ProductionCountry>>(string, object : TypeToken<List<ProductionCountry>>() {}.type)

    @TypeConverter
    fun fromSpokenLanguage(list: List<SpokenLanguage>): String = Gson().toJson(list)

    @TypeConverter
    fun fromSpokenLanguageJson(string: String): List<SpokenLanguage> = Gson().fromJson<List<SpokenLanguage>>(string, object : TypeToken<List<SpokenLanguage>>() {}.type)

    @TypeConverter
    fun fromNetwork(list: List<Network>): String = Gson().toJson(list)

    @TypeConverter
    fun fromNetworkJson(string: String): List<Network> = Gson().fromJson<List<Network>>(string, object : TypeToken<List<Network>>() {}.type)

    @TypeConverter
    fun fromCredits(credits: Credits): String = Gson().toJson(credits)

    @TypeConverter
    fun fromCreditsJson(string: String): Credits = Gson().fromJson<Credits>(string, Credits::class.java)

    @TypeConverter
    fun fromCast(list: List<Cast>) = Gson().toJson(list)

    @TypeConverter
    fun fromCastJson(string: String) = Gson().fromJson<List<Cast>>(string, object : TypeToken<List<Cast>>() {}.type)

    @TypeConverter
    fun fromCrew(list: List<Crew>) = Gson().toJson(list)

    @TypeConverter
    fun fromCrewJson(string: String) = Gson().fromJson<List<Crew>>(string, object : TypeToken<List<Crew>>() {}.type)
}