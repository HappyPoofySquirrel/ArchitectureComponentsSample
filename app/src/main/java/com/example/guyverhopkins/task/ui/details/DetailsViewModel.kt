package com.example.guyverhopkins.task.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guyverhopkins.task.entities.ITaskRepository
import com.example.guyverhopkins.task.models.Task

/**
 * Created 1/22/19.
 */
class DetailsViewModel(taskId: Int, repo: ITaskRepository) : ViewModel() {
    var task: LiveData<Task> = repo.get(taskId)
}

class DetailsViewModelFactory(private val taskId: Int, private val repo: ITaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(taskId, repo) as T
        } else {
            throw IllegalArgumentException("Details ViewModel Not Found")
        }
    }
}