package com.gambitdev.lifeup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.custom_views.TaskView
import com.gambitdev.lifeup.models.Task
import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskAdapter(private val taskChecked: (task: Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList: List<Task> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.task_list_item, parent, false)
        return TaskViewHolder(view, taskChecked)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    class TaskViewHolder(itemView: View, val taskChecked: (task: Task) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val categoryTitle : TextView = itemView.category_title
        private val taskView : TaskView = itemView.task_view
        private val expTv : TextView = itemView.exp_tv

        fun bind(task: Task) {
            if (task.completed) taskView.checkTask()
            categoryTitle.text = task.category?.categoryName
            taskView.taskTitle = task.taskTitle
            taskView.onTaskCompletedListener = object: TaskView.OnTaskCompleted {
                override fun taskCompleted(taskView: TaskView) {
                    taskChecked(task)
                }
            }
            expTv.text = expTv.context.getString(R.string.task_exp, task.expWorth)
        }
    }
}