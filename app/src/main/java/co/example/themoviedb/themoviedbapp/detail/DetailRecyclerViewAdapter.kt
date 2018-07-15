package co.example.themoviedb.themoviedbapp.detail

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.data.CastType
import co.example.themoviedb.themoviedbapp.databinding.DetailCreditsItemBinding

/**
 * This [RecyclerView] is responsible of handle [CastType] data for [DetailFragment]
 *
 * @author santiagoalvarez
 */
class DetailRecyclerViewAdapter(private var items: List<CastType>) :
        RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder>() {

    private lateinit var dataBinding: DetailCreditsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.detail_credits_item, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(private val itemBinding: DetailCreditsItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(castType: CastType) {
            with(itemBinding) {
                item = castType
                executePendingBindings()
            }
        }
    }
}
