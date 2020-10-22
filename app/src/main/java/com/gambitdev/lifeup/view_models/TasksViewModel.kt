package com.gambitdev.lifeup.view_models

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.*
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.UserStats
import com.gambitdev.lifeup.room.Repository
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_TASKS_TO_PRESENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val userStatsLiveData = repository.getUserStatsLiveData()

    private suspend fun getUserStats() : UserStats? {
        var userStats: UserStats? = null
        val job = GlobalScope.launch {
            userStats = repository.getUserStats()
        }
        job.join()

        return userStats
    }

    fun getNumberOfCompletedTasks() : LiveData<Int> {
        return repository.getNumberOfCompletedTasks()
    }

    val userLevel = Transformations.switchMap(userStatsLiveData) {
        return@switchMap MutableLiveData(it.userLevel)
    }

    fun getAllTasks() : LiveData<List<Task>> {
        return repository.getAllTasks()
    }

    suspend fun getTasksForToday(numberOfCategoriesToSelect: Int) : MutableLiveData<List<Task>> {
        var tasksForToday = MutableLiveData<List<Task>>()
        val job = GlobalScope.launch {
            tasksForToday = repository.getTasksForToday(numberOfCategoriesToSelect)
        }
        job.join()

        return tasksForToday
    }

    fun taskCompleted(task: Task) {
        task.completed = true
        repository.updateTask(task)
        rewardUser(task.expWorth)
    }

    fun rewardUser(expReward: Int) {
        GlobalScope.launch {
            getUserStats()?.let {
                it.addExp(expReward)
                repository.updateUserStats(it)
            }
        }
    }
}