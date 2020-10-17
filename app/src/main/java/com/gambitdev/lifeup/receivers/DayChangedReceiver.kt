package com.gambitdev.lifeup.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.content.edit
import com.gambitdev.lifeup.activities.LoadingActivity
import com.gambitdev.lifeup.view_models.TasksViewModel
import java.util.*

class DayChangedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val minutes = Calendar.getInstance().get(Calendar.MINUTE)
        if (hours == 0 && minutes == 0) { // it is midnight, update ui
            resetAchievements(context)
            val goToLoadingPage = Intent(context, LoadingActivity::class.java)
            context?.startActivity(goToLoadingPage)
        }
    }

    private fun resetAchievements(context: Context?) {
        val preferences = context?.getSharedPreferences("achievements", Context.MODE_PRIVATE)
        preferences?.edit {
            putBoolean("bronze_achieved", false)
            putBoolean("silver_achieved", false)
            putBoolean("gold_achieved", false)
        }
    }
}