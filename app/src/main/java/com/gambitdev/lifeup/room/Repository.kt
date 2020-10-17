package com.gambitdev.lifeup.room

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.TaskList
import com.gambitdev.lifeup.models.UserStats
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class Repository(context: Context) {
    private val dao = AppDB.getInstance(context).taskDao()

    fun updateTask(task: Task) {
        AppDB.executor.execute {
            dao.updateTask(task)
        }
    }

    fun getAllTasks(): LiveData<List<Task>> {
        return dao.getAllTasks()
    }

    private fun getRandomTasks(numberOfCategoriesToSelect: Int): List<Task> {
        return dao.getRandomTasks(numberOfCategoriesToSelect)
    }

    private fun getTaskById(taskId: Int): Task {
        return dao.getTaskById(taskId)
    }

    private fun insertTaskList(taskList: TaskList) {
        AppDB.executor.execute {
            dao.insertTaskList(taskList)
        }
    }

    private fun updateTaskList(taskList: TaskList) {
        AppDB.executor.execute {
            dao.updateTaskList(taskList)
        }
    }

    private fun getTaskList(): TaskList {

        return dao.getTaskList()
    }

    fun getTasksForToday(numberOfCategoriesToSelect: Int): MutableLiveData<List<Task>> {
        val tasksForToday = mutableListOf<Task>()
        val taskList = getTaskList()
        val randomTasks = getRandomTasks(numberOfCategoriesToSelect)
        when {
            taskList.taskIdList.size == 0 -> {
                randomTasks.forEach { taskList.taskIdList.add(it.taskId) }
                updateTaskList(taskList)
            }
            taskList.dayOfMonthGenerated != Calendar.getInstance()
                .get(Calendar.DAY_OF_MONTH) -> {
                taskList.taskIdList.forEach {
                    getTaskById(it).apply {
                        completed = false
                        updateTask(this)
                    }
                }
                val idList = mutableListOf<Int>()
                randomTasks.map { idList.add(it.taskId) }
                taskList.taskIdList = idList
                taskList.dayOfMonthGenerated = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                updateTaskList(taskList)
            }
        }
        tasksForToday.apply {
            taskList.taskIdList.forEach {
                add(getTaskById(it))
            }
        }
        return MutableLiveData(tasksForToday)
    }

    fun getUserStatsLiveData() : LiveData<UserStats> {
        return dao.getUserStatsLiveData()
    }

    fun getUserStats() : UserStats {
        return dao.getUserStats()
    }

    fun updateUserStats(userStats: UserStats) {
        AppDB.executor.execute {
            dao.updateUserStats(userStats)
        }
    }
}