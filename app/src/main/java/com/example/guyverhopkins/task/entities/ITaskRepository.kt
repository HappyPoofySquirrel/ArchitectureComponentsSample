package com.example.guyverhopkins.task.entities

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.guyverhopkins.task.models.Task

/**
 * Created by guyverhopkins on 10/17/16.
 */

interface ITaskRepository {

    fun insert(task: Task)

    fun delete(task: Task)

    fun update(task: Task)

    fun getAll(): LiveData<MutableList<Task>>
    fun get(taskId: Int): LiveData<Task>
    fun getAllPaged(): DataSource.Factory<Int, Task>
}
