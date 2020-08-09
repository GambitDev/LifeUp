package com.gambitdev.lifeup.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.TaskList
import com.gambitdev.lifeup.models.UserStats
import com.gambitdev.lifeup.util.InitTaskDataUtil
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Task::class, TaskList::class, UserStats::class], version = 5)
abstract class AppDB : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: AppDB? = null
        private const val NUMBER_OF_THREADS = 4
        val executor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        var context: Context? = null
        fun getInstance(appContext: Context): AppDB {
            context = appContext
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(appContext).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext.applicationContext,
                AppDB::class.java, "app_db"
            )
                //prepopulate the database after onCreate was called
                .addCallback(initDbOnFirstOpen)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        private val initDbOnFirstOpen = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                //On db creation, populate db with pre-defined tasks
                executor.execute {
                    getInstance(context!!).taskDao().run {
                        InitTaskDataUtil.prepopulateDatabase(this)
                    }
                }
            }
        }
    }
}