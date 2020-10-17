package com.gambitdev.lifeup.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.TaskList
import com.gambitdev.lifeup.models.UserStats
import java.util.*

@Dao
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDao {
    //Methods for Task entity
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTask(task: Task)

    @Update
    abstract fun updateTask(task: Task)

    @Query ("SELECT * FROM tasks")
    abstract fun getAllTasks() : LiveData<List<Task>>

    @Query ("SELECT * FROM tasks WHERE taskId = :taskId")
    abstract fun getTaskById(taskId: Int) : Task

    @Query ("SELECT * FROM tasks WHERE category = :categoryName")
    abstract fun getTasksByCategory(categoryName: String) : List<Task>

    @Query ("SELECT * FROM (SELECT * FROM tasks ORDER BY RANDOM()) a GROUP BY a.category ORDER BY RANDOM() LIMIT :numberOfCategoriesToSelect")
    abstract fun getRandomTasks(numberOfCategoriesToSelect: Int) : List<Task>

    //Methods for TaskList entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTaskList(taskList: TaskList)

    @Update
    abstract fun updateTaskList(taskList: TaskList)

    @Query ("SELECT * FROM task_list WHERE id = 0")
    abstract fun getTaskList() : TaskList

    //Methods for UserStats entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUserStats(userStats: UserStats)

    @Update
    abstract fun updateUserStats(userStats: UserStats)

    @Query("SELECT * FROM user_stats WHERE userStatsId = 0")
    abstract fun getUserStatsLiveData() : LiveData<UserStats>

    @Query("SELECT * FROM user_stats WHERE userStatsId = 0")
    abstract fun getUserStats() : UserStats
}