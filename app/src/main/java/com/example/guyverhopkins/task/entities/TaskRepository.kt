package com.example.guyverhopkins.task.entities

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.guyverhopkins.task.core.network.FakeNetworkCall
import com.example.guyverhopkins.task.core.network.FakeNetworkError
import com.example.guyverhopkins.task.core.network.FakeNetworkSuccess
import com.example.guyverhopkins.task.core.network.MainNetwork
import com.example.guyverhopkins.task.models.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by guyverhopkins on 10/17/16.
 */

class TaskRepository(private val network: MainNetwork, private val dao: TaskDao) : ITaskRepository {
    override fun getAllPaged(): DataSource.Factory<Int, Task> {
        return dao.getAllPaged()
    }

    var tasksList: LiveData<MutableList<Task>>

    init {
        tasksList = dao.getAll()
    }

    override fun insert(task: Task) {
        dao.insert(task)
    }

    override fun delete(task: Task) {
        dao.delete(task.id)
    }

    override fun update(task: Task) {

        //taskHashMap.put(task.key, task);
        dao.update(task)
//        for ((index, value) in tasksList.withIndex()) {
//            if (value.id == task.id) {
//                tasksList[index] = task
//            }
//        }
//        listener?.taskUpdated()
    }

    override fun get(taskId: Int): LiveData<Task> {
        return dao.get(taskId)
    }

    override fun getAll(): LiveData<MutableList<Task>> {
        return dao.getAll()
    }

    suspend fun refreshTitle() {
//        withContext(Dispatchers.IO) {
//            try {
        val result = network.fetchNewWelcome().await()
        dao.insert(Task(title = result))
//            } catch (error: FakeNetworkException) {
//                throw TitleRefreshError(error)
//            }
//        }
    }
}

/**
 * Thrown when there was a error fetching a new title
 *
 * @property message user ready error message
 * @property cause the original cause of this exception
 */
class TitleRefreshError(cause: Throwable) : Throwable(cause.message, cause)

/**
 * Suspend function to use callback-based [FakeNetworkCall] in coroutines
 *
 * @return network result after completion
 * @throws Throwable original exception from library if network request fails
 */
suspend fun <T> FakeNetworkCall<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                is FakeNetworkError -> continuation.resumeWithException(result.error)
            }
        }
    }
}
