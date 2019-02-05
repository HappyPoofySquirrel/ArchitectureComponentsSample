package com.example.guyverhopkins.task.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.example.guyverhopkins.task.models.Task

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }
}