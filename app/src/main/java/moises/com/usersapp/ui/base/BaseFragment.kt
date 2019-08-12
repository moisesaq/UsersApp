package moises.com.usersapp.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import moises.com.usersapp.R

open class BaseFragment : Fragment() {
    protected val isLandScape: Boolean
        get() {
            val currentOrientation = resources.configuration.orientation
            return currentOrientation == Configuration.ORIENTATION_LANDSCAPE
        }

    protected val isTablet: Boolean
        get() = activity!!.resources.getBoolean(R.bool.isTablet)

    protected val isPhoneLarge: Boolean
        get() = activity!!.resources.getBoolean(R.bool.isPhoneLarge)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    protected fun changeTitle(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = title
    }

    protected fun showMessageInToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
