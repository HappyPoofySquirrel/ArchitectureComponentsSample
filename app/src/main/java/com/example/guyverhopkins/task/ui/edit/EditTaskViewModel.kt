package com.example.guyverhopkins.task.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guyverhopkins.task.common.default
import com.example.guyverhopkins.task.entities.ITaskRepository
import com.example.guyverhopkins.task.models.Task

class EditTaskViewModel(private val taskId: Int?, private val repo: ITaskRepository) : ViewModel() {

    private var task = MutableLiveData<Task>().default(Task())
    var taskld: LiveData<Task> = task


    //    private var ntask = Task()
//    var taskld: LiveData<Task>()

//    private var _name = MutableLiveData<String>()
//    var name: LiveData<String> = _name

//    private var mediatorTask: MediatorLiveData<Task> = MediatorLiveData()

//    private var taskldd = Transformations.switchMap(taskld) {
//        task.value = taskld.value
//        taskld
//    }


    init {
        taskId?.let {
            taskld = repo.get(it)
        }
    }

    var hasSaved = MutableLiveData<Boolean>().default(false)

    fun onTitleChanged(s: String) {
        task.value?.title = s
    }

    fun onTaskChanged(s: String) {
        task.value?.task = s
    }

    fun onDateChanged(s: String) {
        task.value?.date = s
    }

    fun saveTask() {
        if (taskId != null) {
            task.value?.id = taskId
            repo.update(task.value!!)
        } else {
            repo.insert(task.value!!)
        }

        hasSaved.value = true
    }

    fun deleteTask() {
        task.value?.let {
            repo.delete(it)
        }
    }
}

class EditTaskModelFactory(private val taskId: Int?, private val repo: ITaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            EditTaskViewModel(taskId, repo) as T
        } else {
            throw IllegalArgumentException("Edit ViewModel Not Found")
        }
    }
}