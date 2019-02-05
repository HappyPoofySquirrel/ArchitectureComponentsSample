package com.example.guyverhopkins.task.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.guyverhopkins.task.entities.ITaskRepository
import com.example.guyverhopkins.task.models.Task
import com.example.guyverhopkins.task.ui.BaseState
import com.example.guyverhopkins.task.ui.Event

/**
 * Created 1/2/19.
 */
class TasksViewModel(repo: ITaskRepository) : ViewModel() {

    private val _state = MutableLiveData<BaseState>()

    val state: LiveData<BaseState>
        get() = _state

    private val _snackBar = MutableLiveData<Event<String>>()

    val snackBar: LiveData<Event<String>>
        get() = _snackBar

    private var tasks: LiveData<PagedList<Task>>

    fun getTasks() = tasks

    var goToEdit = MutableLiveData<Int>()

    init {
        val factory: DataSource.Factory<Int, Task> = repo.getAllPaged()
        val pagedListBuilder: LivePagedListBuilder<Int, Task> = LivePagedListBuilder<Int, Task>(factory, 20)
        tasks = pagedListBuilder.build()
    }

    fun onTaskPressed(position: Int) {
        goToEdit.postValue(tasks.value?.get(position)?.id)
    }
}

//needed to pass params into the viewmodel
class TaskViewModelFactory(private val repo: ITaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            TasksViewModel(repo) as T
        } else {
            throw IllegalArgumentException("Tasks ViewModel Not Found")
        }
    }
}