package co.example.themoviedb.themoviedbapp.base

/**
 * Listener that notify when an image resource is loaded or if there is a exception
 *
 * @author santiagoalvarez
 */
interface ImageLoaderListener {
    fun onLoadSuccess()
    fun onLoadFail(e: Throwable)
}