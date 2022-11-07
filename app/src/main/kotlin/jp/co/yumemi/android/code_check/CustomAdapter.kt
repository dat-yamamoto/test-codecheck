package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, CustomAdapter.ViewHolder>(CodeUtils.listCallback){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener{
        fun itemClick(item: item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val listItem= getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text=
            listItem.name
        if((position % 2) == 1){
            (holder.itemView.findViewById<View>(R.id.repositoryNameLayout) as ConstraintLayout).setBackgroundResource(R.color.list_even)
        } else {
            (holder.itemView.findViewById<View>(R.id.repositoryNameLayout) as ConstraintLayout).setBackgroundResource(R.color.list_odd)
        }

        holder.itemView.setOnClickListener{
            itemClickListener.itemClick(listItem)
        }
    }
}