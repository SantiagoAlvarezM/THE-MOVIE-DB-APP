package co.example.themoviedb.themoviedbapp.navigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.*

/**
 * Class in charge of the navigation between fragments and activities
 *
 * @author santiagoalvarez
 */
class Navigator(private val activity: FragmentActivity, savedInstanceState: Bundle?, private val layoutId: Int, private val listener: NavigationListener?) {

    private val fragmentManager: FragmentManager = activity.supportFragmentManager
    private var flowMarkers: LinkedList<Int>? = null

    /**
     * Interface that listen for navigation events.
     */
    interface NavigationListener {

        /**
         * Event called before the navigator action is executed.
         *
         * @param navigator the instance of the navigator that will execute the navigation action.
         * @param entry     navigator action to be executed.
         * @return if the navigator action was handled externally.
         */
        fun onNavigate(navigator: Navigator, entry: NavigationEntry<*>): Boolean
    }

    /**
     * Construct a new navigator instance with [android.R.id.content] as the fragment container id.
     *
     * @param activity
     * @param savedInstanceState
     */
    constructor(activity: FragmentActivity, savedInstanceState: Bundle) : this(activity, savedInstanceState, android.R.id.content, null)

    init {
        flowMarkers = if (savedInstanceState?.get(MARKERS) == null) {
            LinkedList()
        } else {
            LinkedList(savedInstanceState.get(MARKERS) as List<Int>)
        }
        fragmentManager.addOnBackStackChangedListener {
            val backStackEntries = (0 until fragmentManager.backStackEntryCount)
                    .map { fragmentManager.getBackStackEntryAt(it).id }
            flowMarkers?.retainAll(backStackEntries)
        }
    }

    /**
     * Store the internal state of the navigator to be able to restore it in the future.
     *
     * @param outState
     */
    fun saveInstanceState(outState: Bundle) {
        outState.putSerializable(MARKERS, flowMarkers)
    }

    /**
     * Replace current container (layoutId) with specified fragment, and specified tag.
     *
     * @param fragment       fragment to show
     * @param tag            tag to associate with the fragment.
     * @param addToBackStack flag to add fragment in back stack.
     * @param containerId    Container id used for the fragment navigation
     * @param animation      fragment animation
     */
    private fun pushFragment(containerId: Int, fragment: Fragment, tag: String?, addToBackStack: Boolean,
                             animation: NavigationEntry.CustomAnimations?): Int {
        val res = activity.resources.getResourceName(containerId)
        checkNotNull(res) {
            "Layout id provided to navigator is null."
        }
        check(res.startsWith(activity.packageName)) {
            "Layout id provided to navigator is invalid."
        }
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true) // setAllowOptimization before 26.1.0
        if (animation != null) {
            transaction.setCustomAnimations(animation.enter, animation.exit, animation.popEnter, animation.popExit)
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        return transaction.replace(containerId, fragment, tag).commit()
    }

    /**
     * Enable navigation between fragments
     *
     * @param fragment - target of the navigation
     */
    fun to(fragment: Fragment): FragmentNavigationEntry.Builder {
        check(!fragment.isAdded) {
            "The fragment was already added to an activity before calling the navigator."
        }
        return FragmentNavigationEntry.Builder(this, fragment)
    }

    /**
     * Enable navigation between activities
     *
     * @param intent - target of the navigation
     */
    fun to(intent: Intent): IntentNavigationEntry.Builder = IntentNavigationEntry.Builder(this, intent)

    /**
     * Navigate between fragments
     *
     * @param entry
     */
    protected fun navigateTo(entry: FragmentNavigationEntry) {
        if (listener != null && listener.onNavigate(this, entry)) {
            return
        }
        if (entry.isClearHistory) {
            clearFragmentNavigationHistory()
        }

        val target = entry.target
        val tag = entry.getTag()
        val animations = entry.animations
        var containerId = entry.containerId
        if (containerId == null) {
            containerId = layoutId
        }

        if ((activity as AppCompatActivity).supportActionBar != null) {
            activity.supportActionBar!!.title = entry.title
        }

        if (entry.isNoPush) {
            pushFragment(containerId, target, tag, false, animations)
        } else {
            if (entry.isSubFlow) {
                check(flowMarkers!!.isEmpty()) {
                    "You can only have one subflow. Nested subflows are not supported"
                }
                flowMarkers!!.add(pushFragment(containerId, target, null, true, animations))
            } else {
                pushFragment(containerId, target, tag, true, animations)
            }
        }
    }

    /**
     * Navigate between activities
     *
     * @param entry
     */
    protected fun navigateTo(entry: IntentNavigationEntry) {
        if (listener != null && listener.onNavigate(this, entry)) {
            return
        }

        val requestCode = entry.requestCode
        val target = entry.target
        val activityOptionsCompat: Bundle? = entry.activityOptionsCompat
        if (requestCode == -1) {
            activity.startActivity(target, activityOptionsCompat)
        } else {
            val startFromFragmentId = entry.startFromFragmentId
            if (startFromFragmentId == -1) {
                activity.startActivityForResult(target, requestCode)
            } else {
                val fragment = fragmentManager.findFragmentById(startFromFragmentId)
                if (fragment == null) {
                    Log.d(TAG, "Trying to start an activity from an unexisting fragment with id: " + startFromFragmentId)
                    return
                } else {
                    activity.startActivityFromFragment(fragment, target, requestCode, activityOptionsCompat)
                }
            }
        }

        //Perform animation if any exist
        val animations = entry.animations
        if (animations != null) {
            activity.overridePendingTransition(animations.enter, animations.exit)
        }
    }

    /**
     * Clears the entire fragment navigation history.
     * This removes all the entries in the back stack.
     */
    fun clearFragmentNavigationHistory() {
        if (fragmentManager.backStackEntryCount > 0) {
            val firstFragment = fragmentManager.getBackStackEntryAt(0)
            fragmentManager.popBackStack(firstFragment.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            Log.d(TAG, "No backstack found trying to clear backstack.")
        }
    }

    /**
     * Go in step back in the fragment navigation history.
     */
    fun navigateOneStepBack() {
        fragmentManager.popBackStack()
    }

    /**
     * Executes a navigation action
     *
     * @param entry navigation action to execute
     */
    fun navigateTo(entry: NavigationEntry<*>) {
        if (entry is FragmentNavigationEntry) {
            navigateTo(entry)
        } else if (entry is IntentNavigationEntry) {
            navigateTo(entry)
        }
    }

    companion object {

        private val TAG = "Navigator"

        private val MARKERS = "navigation.navigator.markers"
    }
}
