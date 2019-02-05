package com.example.guyverhopkins.task.entities

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.example.guyverhopkins.task.common.BaseDao
import com.example.guyverhopkins.task.models.Task

@Dao
abstract class TaskDao : BaseDao<Task> {
    @Query("DELETE FROM Task")
    abstract fun clear()

    @Query("SELECT * FROM Task")
    abstract fun getAll(): LiveData<MutableList<Task>>

    @Query("SELECT * FROM Task")
    abstract fun getAllPaged(): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM Task WHERE id = :id")
    abstract fun get(id: Int): LiveData<Task>

    @Query("DELETE FROM Task WHERE id = :id")
    abstract fun delete(id: Int)
}
