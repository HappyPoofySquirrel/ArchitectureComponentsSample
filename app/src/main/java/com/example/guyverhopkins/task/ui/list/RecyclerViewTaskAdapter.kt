package com.example.guyverhopkins.task.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.guyverhopkins.task.R
import com.example.guyverhopkins.task.models.Task

/**
 * Created by guyverhopkins on 10/17/16.
 */

class RecyclerViewTaskAdapter : PagedListAdapter<Task, TaskViewHolder>(TaskDiffCallback()), TaskViewHolder.ClickListener {
    private var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_note, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.titleTv.text = task?.title
        holder.dateTv.text = task?.date
        holder.setClickListener(this)
    }

    override fun onClick(position: Int) {
        listener?.onTaskSelected(position)
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onTaskSelected(position: Int)
    }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var titleTv: TextView = itemView.findViewById<View>(R.id.title_et) as TextView
    var dateTv: TextView = itemView.findViewById<View>(R.id.date_tv) as TextView

    private var listener: ClickListener? = null

    interface ClickListener {
        fun onClick(position: Int)
    }

    init {
        itemView.setOnClickListener {
            if (listener != null) {
                listener?.onClick(adapterPosition)
            }
        }
    }

    fun setClickListener(listener: ClickListener) {
        this.listener = listener
    }
}
