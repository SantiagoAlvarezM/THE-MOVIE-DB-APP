package co.example.themoviedb.themoviedbapp.navigation

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

/**
 * Defines a navigation transaction
 *
 * @author santiagoalvarez
 */
abstract class NavigationEntry<T> : Parcelable {

    internal val target: T
    internal val animations: CustomAnimations?
    private val isHome: Boolean
    internal val title: String?
    internal var activityOptionsCompat: Bundle? = null

    /**
     * private full options constructor.  Exposes all of the available information within the event.
     *
     * @param builder the target navigation class, can be a fragment or an intent.
     */
    protected constructor(builder: Builder<*, T>) {
        this.target = builder.target!!
        this.animations = builder.animations
        this.title = builder.title
        this.isHome = builder.home
        this.activityOptionsCompat = builder.activityOptionsCompat
    }

    protected constructor(parcelIn: Parcel, target: T) {
        isHome = parcelIn.readValue(null) as Boolean
        title = parcelIn.readValue(null) as String
        animations = parcelIn.readValue(null) as CustomAnimations
        activityOptionsCompat = parcelIn.readBundle()
        this.target = target
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeValue(isHome)
        parcel.writeValue(title)
        parcel.writeValue(animations)
        parcel.writeBundle(activityOptionsCompat)
    }

    /**
     * @return the internal class of the target.
     * @throws ClassNotFoundException
     */
    @Throws(ClassNotFoundException::class)
    protected abstract fun getInternalTargetClass(): Class<*>?

    /**
     * The custom transition animation configuration class.
     *
     * [CustomAnimations] constructor.
     *
     * @param enter    the enter animation.
     * @param exit     the exit animation.
     * @param popEnter the pop backstack enter animation (only used for fragment transactions).
     * @param popExit  the pop backstack exit animation (only used for fragment transactions).
     */
    class CustomAnimations(internal val enter: Int, internal val exit: Int, internal val popEnter: Int = 0, internal val popExit: Int = 0)

    /**
     * Builder to create the Navigation entry.
     */
    abstract class Builder<T : Builder<T, P>, P> internal constructor(protected var navigator: Navigator?, val target: P?) {

        internal var animations: CustomAnimations? = null
        internal var title: String? = null
        internal var home = false
        internal var activityOptionsCompat: Bundle? = null

        init {
            checkNotNull(navigator, { "target can't be null." })
        }

        protected abstract fun self(): T

        /**
         * Builds and executes the navigation entry.
         */
        fun navigate() {
            checkNotNull(navigator, {
                "The navigator instance is null when trying to execute a navigation entry."
            })
            navigator?.navigateTo(build())
        }

        fun withHome(isHome: Boolean): T {
            this.home = isHome
            return self()
        }

        fun withTitle(title: String): T {
            this.title = title
            return self()
        }

        /**
         * Sets the custom transition animation configuration class.
         * By default is slide left.
         */
        fun withAnimations(animations: CustomAnimations): T {
            this.animations = animations
            return self()
        }

        /**
         * Builds the [NavigationEntry]
         *
         * @return the navigation event.
         */
        protected abstract fun build(): NavigationEntry<P>

    }
}
