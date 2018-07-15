package co.example.themoviedb.themoviedbapp.navigation

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat

/**
 * This class defines a [Intent] navigation transaction
 *
 * @author santiagoalvarez
 */
class IntentNavigationEntry : NavigationEntry<Intent> {


    /**
     * @return the request code with a default value of -1
     */
    var requestCode = -1
        private set
    /**
     * @return the originator fragment id.
     */
    var startFromFragmentId = -1
        private set

    internal constructor(builder: Builder) : super(builder) {
        this.requestCode = builder.code
        this.startFromFragmentId = builder.originatorFragmentId
    }

    internal constructor(parcelIn: Parcel) : super(parcelIn, parcelIn.readValue(null) as Intent) {
        requestCode = parcelIn.readInt()
        startFromFragmentId = parcelIn.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(target)
        super.writeToParcel(dest, flags)
        dest.writeInt(requestCode)
        dest.writeInt(startFromFragmentId)
    }

    @Throws(ClassNotFoundException::class)
    override fun getInternalTargetClass(): Class<*>? {
        val component = target.component
        return if (component != null) {
            Class.forName(component.className)
        } else null
    }

    /**
     * Builder to create an Activity Navigation entry.
     */
    class Builder(navigator: Navigator, target: Intent) : NavigationEntry.Builder<Builder, Intent>(navigator, target) {

        internal var code = -1
        internal val originatorFragmentId = -1

        /**
         * something like forResultIn(int code) or forResultIn(Fragment origin, int code)
         */
        fun withRequestCode(code: Int): Builder {
            this.code = code
            return self()
        }

        /**
         * Set the flags to the intent, replacing the ones that already has.<br></br>
         * <br></br>
         * See [Intent.setFlags] for more information.
         *
         * @param flags New flags to set to the intent
         * @return Builder
         */
        fun withFlags(flags: Int): Builder {
            this.target!!.flags = flags
            return self()
        }

        /**
         * Set the [ActivityOptionsCompat] for transitions between activities.
         */
        fun withOptions(activityOptionsCompat: Bundle?): Builder {
            this.activityOptionsCompat = activityOptionsCompat
            return self()
        }

        /**
         * Add additional flags to the intent (or with existing flags value).<br></br>
         * <br></br>
         * See [Intent.addFlags] for more information.
         *
         * @param flags The new flags to set.
         * @return Builder
         */
        fun addFlags(flags: Int): Builder {
            this.target!!.addFlags(flags)
            return self()
        }

        /**
         * If set, and the activity being launched is already running in the current task, then instead of launching a new instance of that activity,
         * all of the other activities on top of it will be closed and this Intent will be delivered to the (now on top) old activity as a new Intent.
         * The flags that the intent contains will not be replaced.<br></br>
         * <br></br>
         * See [Intent.FLAG_ACTIVITY_CLEAR_TOP] for more information.
         */
        fun clearTop(): Builder = addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        /**
         * If set, the activity will not be launched if it is already running at the top of the history stack.
         * The Intent will be delivered to the current instance's onNewIntent().
         * The flags that the intent contains will not be replaced.<br></br>
         * <br></br>
         * See [Intent.FLAG_ACTIVITY_SINGLE_TOP] for more information.
         */
        fun singleTop(): Builder = addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        /**
         * If set, this flag will cause any existing task that would be associated with the activity to be cleared before the activity is started.
         * That is, the activity becomes the new root of an otherwise empty task, and any old activities are finished.
         * This can only be used in conjunction with FLAG_ACTIVITY_NEW_TASK.<br></br>
         * <br></br>
         * See [Intent.FLAG_ACTIVITY_CLEAR_TASK] and [Intent.FLAG_ACTIVITY_NEW_TASK] for more information.
         */
        fun clearStack(): Builder = withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        override fun self(): Builder = this

        /**
         * Builds the [IntentNavigationEntry]
         *
         * @return the navigation event.
         */
        public override fun build(): IntentNavigationEntry = IntentNavigationEntry(this)
    }

    companion object {

        val CREATOR: Parcelable.Creator<IntentNavigationEntry> = object : Parcelable.Creator<IntentNavigationEntry> {
            override fun createFromParcel(parcelIn: Parcel): IntentNavigationEntry {
                return IntentNavigationEntry(parcelIn)
            }

            override fun newArray(size: Int): Array<IntentNavigationEntry> {
                return emptyArray()
            }
        }
    }
}
