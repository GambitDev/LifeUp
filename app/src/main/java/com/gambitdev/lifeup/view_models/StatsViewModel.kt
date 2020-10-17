package com.gambitdev.lifeup.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gambitdev.lifeup.room.Repository

class StatsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(application)
    private val userStats = repository.getUserStatsLiveData()

    val userLevel = Transformations.switchMap(userStats) {
        return@switchMap MutableLiveData(it.userLevel)
    }

    val expToNextLevel = Transformations.switchMap(userStats) {
        return@switchMap MutableLiveData(it.expToNextLevel - it.deltaExp)
    }

    val currentExpInDegrees = Transformations.switchMap(userStats) {
        val degrees = (it.deltaExp).times(360).div(it.expToNextLevel)
        return@switchMap MutableLiveData(degrees)
    }
}