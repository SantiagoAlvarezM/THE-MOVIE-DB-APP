package co.example.themoviedb.themoviedbapp.navigation

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.util.Log

/**
 * This class defines a [Fragment] navigation transaction
 *
 * @author santiagoalvarez
 */
class FragmentNavigationEntry : NavigationEntry<Fragment> {

    private val tag: String?

    /**
     * Indicates if the fragments starts a sub flow
     *
     * @return
     */
    var isSubFlow: Boolean

    /**
     * Calls [Navigator.clearFragmentNavigationHistory] before navigating.
     * This will remove all the entries in the back stack before the navigation action is executed.
     *
     * By default is false.
     *
     * @return if the history is going to be cleared
     */
    var isClearHistory: Boolean = false
        private set
    /**
     * Gets the container id where the navigation is going to take place.
     *
     * @return the id of the navigation container or null if non was set.
     */
    var containerId: Int? = null

    /**
     * Tells if the target fragment is going to be pushed to the backstack.
     */
    var isNoPush: Boolean

    internal constructor(builder: Builder) : super(builder) {
        this.tag = builder.tag
        this.isSubFlow = builder.subFlow
        this.isNoPush = builder.noPush
        this.isClearHistory = builder.clearHistory
        this.containerId = builder.containerId
    }

    fun getTag(): String = tag ?: target::class.java.simpleName

    @Throws(ClassNotFoundException::class)
    override fun getInternalTargetClass(): Class<*>? = target::class.java

    override fun writeToParcel(dest: Parcel, flags: Int) {
        storeFragment(target, dest)
        super.writeToParcel(dest, flags)
        dest.writeValue(tag)
        dest.writeValue(isSubFlow)
        dest.writeValue(isNoPush)
        dest.writeValue(isClearHistory)
    }

    private fun storeFragment(fragment: Fragment, dest: Parcel) {
        dest.writeValue(fragment.javaClass)
        dest.writeValue(fragment.arguments)
    }

    internal constructor(parcelIn: Parcel) : super(parcelIn, restoreFragment(parcelIn)!!) {
        tag = parcelIn.readValue(null) as String
        isSubFlow = parcelIn.readValue(null) as Boolean
        isNoPush = parcelIn.readValue(null) as Boolean
        isClearHistory = parcelIn.readValue(null) as Boolean
    }

    /**
     * Builder to create a Fragment Navigation entry.
     */
    class Builder(navigator: Navigator, target: Fragment) : NavigationEntry.Builder<Builder, Fragment>(navigator, target) {

        internal var tag: String? = null
        internal var subFlow: Boolean = false
        internal var noPush: Boolean = false
        internal var clearHistory: Boolean = false
        internal var containerId: Int? = null

        /**
         * Adds an optional tag to the fragment.
         *
         * By default is null.
         */
        fun withTag(tag: String): Builder {
            this.tag = tag
            return self()
        }

        /**
         * Tells if the target fragment is not going to be pushed to the backstack.<br></br>
         * If it isn't pushed the current content of the fragment will be replaced with the new fragment.<br></br>
         * This is intended to be used when there are no entries in the backstack
         *
         * By default the fragment will be pushed.
         */
        fun noPush(): Builder {
            this.noPush = true
            return self()
        }

        /**
         * Calls [Navigator.clearFragmentNavigationHistory] before navigating.
         * This will remove all the entries in the back stack before the navigation action is executed.
         *
         * By default is false
         */
        fun clearHistory(): Builder {
            this.clearHistory = true
            return self()
        }

        /**
         * Sets the container id where the navigation is going to take place.
         */
        fun withContainerId(containerId: Int): Builder {
            this.containerId = containerId
            return self()
        }

        /**
         * Starts a navigation subflow inside an activity
         */
        fun startSubFlow() {
            subFlow = true
            navigate()
        }

        override fun self(): Builder = this

        /**
         * Builds the [FragmentNavigationEntry]
         *
         * @return the navigation event.
         */
        public override fun build(): FragmentNavigationEntry = FragmentNavigationEntry(this)
    }

    companion object {

        private const val TAG = "FragmentNavigationEntry"

        val CREATOR: Parcelable.Creator<FragmentNavigationEntry> = object : Parcelable.Creator<FragmentNavigationEntry> {
            override fun createFromParcel(parcelIn: Parcel): FragmentNavigationEntry {
                return FragmentNavigationEntry(parcelIn)
            }

            override fun newArray(size: Int): Array<FragmentNavigationEntry> {
                return emptyArray()
            }
        }

        private fun restoreFragment(parcelIn: Parcel): Fragment? {
            var fragment: Fragment? = null
            try {
                fragment = (parcelIn.readValue(null) as Class<*>).newInstance() as Fragment
                fragment.arguments = parcelIn.readValue(null) as android.os.Bundle
            } catch (e: InstantiationException) {
                Log.e(TAG, "Error restoring fragment")
            } catch (e: IllegalAccessException) {
                Log.e(TAG, "Error restoring fragment")
            }

            return fragment
        }
    }
}
