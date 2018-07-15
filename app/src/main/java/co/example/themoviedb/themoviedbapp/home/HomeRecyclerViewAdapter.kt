package co.example.themoviedb.themoviedbapp.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.example.themoviedb.themoviedbapp.R
import co.example.themoviedb.themoviedbapp.databinding.FragmentHomeItemBinding
import co.example.themoviedb.themoviedbapp.home.model.ListItem

/**
 * This [RecyclerView] is responsible of handle [ListItem] data for [HomeFragment]
 *
 * @author santiagoalvarez
 */
class HomeRecyclerViewAdapter(private var items: MutableList<ListItem>,
                              private val userInteractionListener: UserInteractionListener) :
        RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    private lateinit var dataBinding: FragmentHomeItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.fragment_home_item, parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItemForPosition(position), userInteractionListener)
    }

    private fun getItemForPosition(position: Int) = items[position]

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(data: List<ListItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: FragmentHomeItemBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(listItem: ListItem, userActionListener: UserInteractionListener) {
            itemView.setOnClickListener {
                userActionListener.onListItemClick(it, adapterPosition, listItem.id)
            }
            with(itemBinding) {
                item = listItem
                executePendingBindings()
            }

        }
    }
}
