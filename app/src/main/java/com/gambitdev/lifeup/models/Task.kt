package com.gambitdev.lifeup.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gambitdev.lifeup.enums.Category
import com.gambitdev.lifeup.room.TaskTypeConverters

@Entity(tableName = "tasks")
@TypeConverters(TaskTypeConverters::class)
data class Task(var taskTitle: String) {

    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0

    var category: Category? = null
    var completed: Boolean = false
    var expWorth: Int = 100
}