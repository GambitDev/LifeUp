package com.gambitdev.lifeup.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.gambitdev.lifeup.R
import kotlinx.android.synthetic.main.task_progress_bar.view.*
import kotlin.math.roundToInt

class TaskProgressBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    var onMedalAchievedListener: OnMedalAchievedListener? = null
    private var bronzeMedalPos: Int
    private var silverMedalPos: Int

    init {
        View.inflate(context,
            R.layout.task_progress_bar, this)

        val attributes = context.obtainStyledAttributes(attrs,
            R.styleable.TaskProgressBar
        )

        attributes.getFloat(R.styleable.TaskProgressBar_bronzeMedalPosition, 0.5f).let {
            bronzeMedalPos = it.roundToInt()
            bronze_gl.setGuidelinePercent(it)
        }
        attributes.getFloat(R.styleable.TaskProgressBar_silverMedalPosition, 0.75f).let {
            silverMedalPos = it.roundToInt()
            silver_gl.setGuidelinePercent(it)
        }
        base_progress_bar.progress = attributes.getInt(R.styleable.TaskProgressBar_progress, 0)
        base_progress_bar.max = attributes.getInt(R.styleable.TaskProgressBar_maxProgress, 100)
        attributes.recycle()
    }

    fun incrementProgress() {
        base_progress_bar.progress++
        when (base_progress_bar.progress) {
            bronzeMedalPos -> onMedalAchievedListener?.bronzeAchieved()
            silverMedalPos -> onMedalAchievedListener?.silverAchieved()
            base_progress_bar.max -> onMedalAchievedListener?.goldAchieved()
        }
    }

    fun incrementProgressToPosition(position: Int) {
        base_progress_bar.progress = position
    }

    fun decrementProgress() {
        base_progress_bar.progress--
    }

    fun setMaxProgress(maxProgress: Int) {
        base_progress_bar.max = maxProgress
        bronzeMedalPos = maxProgress.toFloat().div(2).roundToInt()
        silverMedalPos = maxProgress.toFloat().div(4).times(3).roundToInt()
        bronze_gl.setGuidelinePercent(bronzeMedalPos.toFloat().div(maxProgress))
        silver_gl.setGuidelinePercent(silverMedalPos.toFloat().div(maxProgress))
    }

    interface OnMedalAchievedListener {
        fun bronzeAchieved()
        fun silverAchieved()
        fun goldAchieved()
    }
}