package com.gambitdev.lifeup.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.gambitdev.lifeup.view_models.TasksViewModel
import java.util.*

class DayChangedReceiver(private val vm: TasksViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minutes = Calendar.getInstance().get(Calendar.MINUTE)
        if (hours == 0 && minutes == 0) { // it is midnight, update ui
            vm.midnightIndicator.value = true
        }
    }
}