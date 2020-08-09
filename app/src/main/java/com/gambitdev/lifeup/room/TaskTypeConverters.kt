package com.gambitdev.lifeup.room

import androidx.room.TypeConverter
import com.gambitdev.lifeup.enums.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskTypeConverters {

    @TypeConverter
    fun categoryToString(category: Category) : String {
        return category.categoryName
    }

    @TypeConverter
    fun stringToCategory(string: String) : Category {
        return when(string) {
            "Social" -> Category.SOCIAL
            "Family" -> Category.FAMILY
            "Relationship" -> Category.RELATIONSHIP
            "Career" -> Category.CAREER
            "Hobbies" -> Category.HOBBIES
            "Fatherhood" -> Category.FATHERHOOD
            "Fitness" -> Category.FITNESS
            "Spirit & Mind" -> Category.SPIRIT_MIND
            else -> Category.INTELLECT
        }
    }

    @TypeConverter
    fun intListToJSON(list: MutableList<Int>) : String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun JSONtoIntList(json: String) : MutableList<Int> {
        val type = object : TypeToken<MutableList<Int>>(){}.type
        return Gson().fromJson(json, type)
    }
}