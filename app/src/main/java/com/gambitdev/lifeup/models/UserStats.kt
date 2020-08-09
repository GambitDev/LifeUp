package com.gambitdev.lifeup.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.gambitdev.lifeup.util.Constants.Companion.EXP_FOR_FIRST_LEVEL
import com.gambitdev.lifeup.util.Constants.Companion.LEVEL_UP_GROWTH_PERCENTAGE_CONST

@Entity(tableName = "user_stats")
class UserStats {
    @PrimaryKey
    var userStatsId = 0

    var exp = 0
    val userLevel: Int
        get() {
            if (exp < 500) return 1
            return calcUserLevel()
        }
    val expToNextLevel: Int
        get() {
            if (exp == 0) return EXP_FOR_FIRST_LEVEL
            return exp % userLevel
        }
    val expRequiredToLevelUp: Int
        get() {
            var exp = EXP_FOR_FIRST_LEVEL
            for (i in 0 until userLevel + 1) {
                exp += exp.times(LEVEL_UP_GROWTH_PERCENTAGE_CONST).div(100)
            }
            return exp
        }

    @Ignore
    var tasksCompletedCountByCategory = mutableMapOf(
        Pair("Social", 0),
        Pair("Family", 0),
        Pair("Relationship", 0),
        Pair("Career", 0),
        Pair("Hobbies", 0),
        Pair("Fatherhood", 0),
        Pair("Fitness", 0),
        Pair("Spirit & Mind", 0),
        Pair("Intellect", 0)
    )

    val completedTasksCount: Int
        get() {
            var completedCount = 0
            tasksCompletedCountByCategory.forEach {
                completedCount += it.value
            }
            return completedCount
        }

    private fun calcUserLevel(): Int {
        var level = 0
        var currentExp = exp
        var expToNextLevel = EXP_FOR_FIRST_LEVEL
        while (true) {
            level++
            currentExp -= expToNextLevel
            expToNextLevel += expToNextLevel.div(100).times(LEVEL_UP_GROWTH_PERCENTAGE_CONST)
            if (currentExp - expToNextLevel <= 0) break
        }
        return level
    }
}