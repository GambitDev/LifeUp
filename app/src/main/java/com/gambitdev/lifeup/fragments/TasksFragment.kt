package com.gambitdev.lifeup.fragments

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.adapters.TaskAdapter
import com.gambitdev.lifeup.view_models.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks_layout.*
import androidx.lifecycle.Observer
import com.gambitdev.lifeup.custom_views.TaskProgressBar
import com.gambitdev.lifeup.custom_views.TaskView
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.receivers.DayChangedReceiver
import com.gambitdev.lifeup.util.Constants
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_TASKS_TO_PRESENT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TasksFragment : Fragment(R.layout.fragment_tasks_layout), TaskProgressBar.OnMedalAchievedListener {

    private val vm: TasksViewModel by viewModels()
    private val taskAdapter by lazy { TaskAdapter(::taskChecked) }
    private val dayChangedReceiver by lazy { DayChangedReceiver() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTaskList()
        setupTaskAdapter()
        setupProgressBar()
        registerDayChangedReceiver()
        setupLevelCounter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unregisterDayChangedReceiver()
    }

    private fun setupTaskList() {
        with(task_list) {
            adapter = taskAdapter
        }
    }

    private fun setupTaskAdapter() {
        var tasksForToday: MutableLiveData<List<Task>>
        val handler = Handler()
        GlobalScope.launch {
            tasksForToday = vm.getTasksForToday(NUMBER_OF_TASKS_TO_PRESENT)
            handler.post {
                tasksForToday.observe(viewLifecycleOwner, Observer {
                    taskAdapter.taskList = it
                })
            }
        }
    }

    private fun setupProgressBar() {
        var tasksForToday : MutableLiveData<List<Task>>
        val handler = Handler()
        GlobalScope.launch {
            tasksForToday = vm.getTasksForToday(NUMBER_OF_TASKS_TO_PRESENT)
            handler.post {
                tasksForToday.observe(viewLifecycleOwner, Observer {
                    progress.setMaxProgress(it.size)
                })
            }
        }

        vm.getNumberOfCompletedTasks().observe(viewLifecycleOwner, Observer {
            progress.incrementProgressToPosition(it)
        })
        progress.onMedalAchievedListener = this
    }

    private fun taskChecked(task: Task) {
        vm.taskCompleted(task)
    }

    private fun registerDayChangedReceiver() {
        val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
        context?.registerReceiver(dayChangedReceiver, intentFilter)
    }

    private fun unregisterDayChangedReceiver() {
        context?.unregisterReceiver(dayChangedReceiver)
    }

    private fun setupLevelCounter() {
        vm.userLevel.observe(viewLifecycleOwner, Observer {
            lvl_counter.text = getString(R.string.user_lvl_tasks_fragment, it)
        })
    }

    override fun bronzeAchieved() {
        val preferences = context?.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        val medalAchieved = preferences?.getBoolean("bronze_achieved", false)
        if (!medalAchieved!!) {
            preferences.edit {
                putBoolean("bronze_achieved", true)
            }
            Toast.makeText(
                context,
                "Bronze medal achieved! Bonus ${Constants.BRONZE_MEDAL_EXP_REWARD} exp",
                LENGTH_LONG
            ).show()
            vm.rewardUser(Constants.BRONZE_MEDAL_EXP_REWARD)
        }
    }

    override fun silverAchieved() {
        val preferences = context?.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        val medalAchieved = preferences?.getBoolean("silver_achieved", false)
        if (!medalAchieved!!) {
            preferences.edit {
                putBoolean("silver_achieved", true)
            }
            Toast.makeText(
                context,
                "Silver medal achieved! Bonus ${Constants.SILVER_MEDAL_EXP_REWARD} exp",
                LENGTH_LONG
            ).show()
            vm.rewardUser(Constants.SILVER_MEDAL_EXP_REWARD)
        }
    }

    override fun goldAchieved() {
        val preferences = context?.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        val medalAchieved = preferences?.getBoolean("gold_achieved", false)
        if (!medalAchieved!!) {
            preferences.edit {
                putBoolean("gold_achieved", true)
            }
            Toast.makeText(
                context,
                "Gold medal achieved! Bonus ${Constants.GOLD_MEDAL_EXP_REWARD} exp",
                LENGTH_LONG
            ).show()
            vm.rewardUser(Constants.GOLD_MEDAL_EXP_REWARD)
        }
    }
}