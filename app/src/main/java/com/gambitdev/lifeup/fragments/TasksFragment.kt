package com.gambitdev.lifeup.fragments

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.adapters.TaskAdapter
import com.gambitdev.lifeup.view_models.TasksViewModel
import kotlinx.android.synthetic.main.fragment_tasks_layout.*
import androidx.lifecycle.Observer
import com.gambitdev.lifeup.custom_views.TaskView
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.receivers.DayChangedReceiver
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_TASKS_TO_PRESENT

class TasksFragment : Fragment(R.layout.fragment_tasks_layout) {

    private val vm: TasksViewModel by viewModels()
    private val taskAdapter by lazy { TaskAdapter(::taskChecked) }
    private val dayChangedReceiver by lazy { DayChangedReceiver(vm) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTaskList()
        setupTaskAdapter()
        setupProgressBar()
        registerDayChangedReceiver()
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
        vm.getTasksForToday(NUMBER_OF_TASKS_TO_PRESENT).observe(viewLifecycleOwner, Observer {
            taskAdapter.taskList = it
        })
    }

    private fun setupProgressBar() {
        vm.getTasksForToday(NUMBER_OF_TASKS_TO_PRESENT).observe(viewLifecycleOwner, Observer {
            progress.setMaxProgress(it.size)
        })
        vm.numberOfCompletedTasks.observe(viewLifecycleOwner, Observer {
            progress.incrementProgressToPosition(it)
        })
    }

    private fun taskChecked(task: Task) {
        vm.taskCompleted(task)
        Toast.makeText(context, "You have earned ${task.expWorth} exp", LENGTH_SHORT).show()
    }

    private fun registerDayChangedReceiver() {
        val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
        context?.registerReceiver(dayChangedReceiver, intentFilter)
    }

    private fun unregisterDayChangedReceiver() {
        context?.unregisterReceiver(dayChangedReceiver)
    }
}