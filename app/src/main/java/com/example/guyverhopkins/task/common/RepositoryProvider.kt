package com.example.guyverhopkins.task.common

import android.app.Application
import com.example.guyverhopkins.task.core.AppDatabase
import com.example.guyverhopkins.task.core.network.MainNetwork
import com.example.guyverhopkins.task.entities.ITaskRepository
import com.example.guyverhopkins.task.entities.TaskRepository

/**
 * Created by guyverhopkins on 10/18/16.
 */
class RepositoryProvider private constructor() {

    internal var taskRepository: ITaskRepository? = null

    fun getTaskRepository(mainNetwork: MainNetwork, application: Application): ITaskRepository {
        if (taskRepository == null) {
            val dao = AppDatabase.getAppDataBase(application)!!.taskDao()
            taskRepository = TaskRepository(mainNetwork, dao)
        }
        return taskRepository as ITaskRepository
    }

    companion object {
        val instance = RepositoryProvider()
    }
}
