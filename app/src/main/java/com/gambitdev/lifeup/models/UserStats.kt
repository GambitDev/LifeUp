package com.gambitdev.lifeup.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gambitdev.lifeup.util.Constants.Companion.EXP_FOR_FIRST_LEVEL
import com.gambitdev.lifeup.util.Constants.Companion.LEVEL_UP_GROWTH_PERCENTAGE_CONST

@Entity(tableName = "user_stats")
class UserStats {
    @PrimaryKey
    var userStatsId = 0
    var userLevel = 1
    var deltaExp = 0
    var expToNextLevel = EXP_FOR_FIRST_LEVEL

    fun addExp(exp: Int) {
        if (deltaExp + exp > expToNextLevel) {
            userLevel++
            deltaExp += exp
            deltaExp -= expToNextLevel
            expToNextLevel += expToNextLevel.div(100).times(LEVEL_UP_GROWTH_PERCENTAGE_CONST)
        } else {
            deltaExp += exp
        }
    }
}