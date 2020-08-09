package com.gambitdev.lifeup.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.fragments.StatsFragment
import com.gambitdev.lifeup.fragments.TasksFragment
import com.gambitdev.lifeup.room.AppDB
import kotlinx.android.synthetic.main.activity_main_content.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
        setupHomeFragment()
    }

    private fun setupBottomNavigation() {
        bottom_nav.setOnNavigationItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.tasks -> TasksFragment()
                R.id.stats -> StatsFragment()
                else -> null
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment!!)
                .commit()
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setupHomeFragment() {
        bottom_nav.selectedItemId = R.id.tasks
    }
}
