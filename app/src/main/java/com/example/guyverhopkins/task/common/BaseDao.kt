package com.example.guyverhopkins.task.common

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: T)
}