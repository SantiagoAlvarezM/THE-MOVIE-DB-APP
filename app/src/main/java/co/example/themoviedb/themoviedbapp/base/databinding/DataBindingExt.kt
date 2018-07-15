package co.example.themoviedb.themoviedbapp.base.databinding

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import co.example.themoviedb.themoviedbapp.App
import co.example.themoviedb.themoviedbapp.base.ImageLoaderListener
import co.example.themoviedb.themoviedbapp.data.local.model.Configuration
import co.example.themoviedb.themoviedbapp.data.service.ApiConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Utility object that holds common [BindingAdapter]'s
 *
 * @author santiagoalvarez
 */
object DataBindingExt {

    /**
     * Binding adapter for common setters on [ImageView]'s
     * @param imageView the ImageView in which the resource will load
     * @param path the url path to the image resource
     * @param circleImage to apply a [RequestOptions.circleCropTransform] to image resource
     * @param listener a [ImageLoaderListener] to notify when image resource is loaded or if there is a exception
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = [("imagePath"), ("circleImage"), ("imageListener")], requireAll = false)
    @JvmStatic
    fun loadImage(imageView: ImageView?, path: String?, circleImage: Boolean? = false, listener: ImageLoaderListener? = null) {
        if (imageView == null || path == null) return
        val configuration: Configuration? = App.instance.configuration
        if (configuration != null) {
            val imageUrl = StringBuilder(configuration.image.baseUrl)
                    .append(ApiConfig.API_BACKDROP_IMAGE_SIZE)
                    .append(path)
                    .toString()

            val builder: RequestBuilder<Drawable> = Glide.with(imageView.context).load(imageUrl)
            if (circleImage != null && circleImage) {
                builder.apply(RequestOptions.circleCropTransform())
            }
            if (listener != null) {
                builder.listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        listener.onLoadFail(e as Throwable)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        listener.onLoadSuccess()
                        return false
                    }

                })
            }
            builder.into(imageView)
        }
    }

    /**
     * Binding adapter for set [View.setTransitionName] on [View]'s that take care of OS version
     * @param view the view to set the transition name
     */
    @BindingAdapter("transitionName")
    @JvmStatic
    fun setTransitionName(view: View, transitionName: String?) {
        if (transitionName.isNullOrEmpty()) return
        view.transitionName = transitionName
    }
}
