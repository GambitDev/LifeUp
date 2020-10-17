package com.gambitdev.lifeup.view_models

import android.app.Application
import androidx.lifecycle.*
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.UserStats
import com.gambitdev.lifeup.room.Repository
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_TASKS_TO_PRESENT

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val userStats = repository.getUserStats()
    private val userStatsLiveData = repository.getUserStatsLiveData()

    val numberOfCompletedTasks = MutableLiveData(0)

    val userLevel = Transformations.switchMap(userStatsLiveData) {
        return@switchMap MutableLiveData(it.userLevel)
    }

    fun getAllTasks() : LiveData<List<Task>> {
        return repository.getAllTasks()
    }

    fun getTasksForToday(numberOfCategoriesToSelect: Int) : LiveData<List<Task>> {
        return repository.getTasksForToday(numberOfCategoriesToSelect).also {
            it.value!!.filter { task -> task.completed }.size.let { completedSize ->
                numberOfCompletedTasks.value = completedSize
            }
        }
    }

    fun taskCompleted(task: Task) {
        task.completed = true
        repository.updateTask(task)
        rewardUser(task.expWorth)
        incrementNumberOfCompletedTasks()
    }

    private fun incrementNumberOfCompletedTasks() {
        numberOfCompletedTasks.value = numberOfCompletedTasks.value!!.plus(1)
    }

    fun rewardUser(expReward: Int) {
        userStats.let {
            it.addExp(expReward)
            repository.updateUserStats(it)
        }
    }
}