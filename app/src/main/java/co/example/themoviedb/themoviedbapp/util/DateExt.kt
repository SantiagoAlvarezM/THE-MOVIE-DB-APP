package co.example.themoviedb.themoviedbapp.util

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * [Date] extension for common operations
 *
 * @author santiagoalvarez
 */

/**
 * Calculate the numbers of days between this [Date] and the given day [Date]
 * @param date
 * @return the amount of days between the dates,
 * > 0 if the given date is after this date
 * < 0 if the given date is before this date
 */
fun Date.daysBetween(date: Date): Long {
    val diff = this.time - date.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}

/**
 * Calculate the numbers of days between this [Date] and the given day [Date]
 * @param time in millis
 * @return the amount of days between the dates,
 * > 0 if the given date is after this date
 * < 0 if the given date is before this date
 */
fun Date.daysBetween(time: Long): Long {
    val diff = this.time - time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
}