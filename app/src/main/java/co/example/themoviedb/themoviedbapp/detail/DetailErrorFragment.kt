package co.example.themoviedb.themoviedbapp.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.example.themoviedb.themoviedbapp.R

/**
 * A Error state [Fragment]
 *
 * @author santiagoalvarez
 */
class DetailErrorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_error, container, false)
    }

}
