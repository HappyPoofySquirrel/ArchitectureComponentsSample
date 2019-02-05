package com.example.guyverhopkins.task.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by guyverhopkins on 10/17/16.
 */

@Entity
data class Task(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var title: String = "",
        var date: String = "",
        var task: String = ""
)