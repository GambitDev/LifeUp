package com.gambitdev.lifeup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.view_models.TasksViewModel
import com.gambitdev.lifeup.util.Constants.Companion.NUMBER_OF_INIT_WRITES_TO_DB
import com.gambitdev.lifeup.util.InitTaskDataUtil

class LoadingActivity : AppCompatActivity() {

    private val vm: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        handleDbCreation()
    }

    private fun handleDbCreation() {
        vm.getAllTasks().observe(this, Observer {
            //continue to main activity only after init of db was completed
            if (it.size == InitTaskDataUtil.initTasks.size) {
                continueToMainActivity()
                finish()
            }
        })
    }

    private fun continueToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
