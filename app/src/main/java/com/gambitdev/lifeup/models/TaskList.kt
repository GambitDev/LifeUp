package com.gambitdev.lifeup.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gambitdev.lifeup.room.TaskTypeConverters
import java.util.*

@Entity (tableName = "task_list")
@TypeConverters (TaskTypeConverters::class)
class TaskList {

    @PrimaryKey
    var id = 0

    var taskIdList = mutableListOf<Int>()
    var dayOfMonthGenerated = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
}