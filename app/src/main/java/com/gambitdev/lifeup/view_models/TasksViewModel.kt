package com.gambitdev.lifeup.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.room.Repository
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_TASKS_TO_PRESENT

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { Repository(application) }
    private val userStats = repository.getUserStats()

    val numberOfCompletedTasks = MutableLiveData(0)

    val midnightIndicator = MutableLiveData<Boolean>(false)

    val updateTasksOnMidnight = Transformations.switchMap(midnightIndicator) {
        getTasksForToday(NUMBER_OF_TASKS_TO_PRESENT)
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
        userStats.value!!.exp += task.expWorth
        incrementNumberOfCompletedTasks()
    }

    private fun incrementNumberOfCompletedTasks() {
        numberOfCompletedTasks.value = numberOfCompletedTasks.value!!.plus(1)
    }

    private fun rewardUser(expReward: Int) {
        userStats.value!!.exp += expReward
        repository.updateUserStats(userStats.value!!)
    }
}