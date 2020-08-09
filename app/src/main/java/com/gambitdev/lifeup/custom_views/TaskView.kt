package com.gambitdev.lifeup.custom_views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.LinearLayout
import com.gambitdev.lifeup.R
import kotlinx.android.synthetic.main.task_view_layout.view.*

class TaskView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var onTaskCompletedListener: OnTaskCompleted? = null
    var taskTitle: String = ""
        set(value) {
            field = value
            task_title.text = value
        }

    init {
        View.inflate(context, R.layout.task_view_layout, this)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TaskView)
        attributes.getString(R.styleable.TaskView_taskTitle).let {
            it?.let {
                task_title.text = it
                taskTitle = it
            }
        }
        attributes.recycle()

        task_checkbox.setOnCheckedChangeListener { checkbox, _ ->
            taskCheckboxStateChanged(checkbox)
        }
    }

    //can only be called once, since there is no way to init this view with a checked checkbox
    //therefore if this method fires we know that isChecked is true and immediately disable it
    private fun taskCheckboxStateChanged(checkbox: CompoundButton) {
        checkbox.isEnabled = false
        task_title.apply {
            setTextColor(Color.LTGRAY)
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        onTaskCompletedListener?.taskCompleted(this)
    }

    fun checkTask() {
        task_checkbox.isChecked = true
        taskCheckboxStateChanged(task_checkbox)
    }

    interface OnTaskCompleted {
        fun taskCompleted(taskView: TaskView)
    }
}