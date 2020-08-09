package com.gambitdev.lifeup.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.gambitdev.lifeup.R
import com.gambitdev.lifeup.view_models.StatsViewModel
import kotlinx.android.synthetic.main.fragment_stats_layout.*

class StatsFragment : Fragment(R.layout.fragment_stats_layout) {
    private val vm: StatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLevelObserver()
        setupProgressObserver()
        setupExpToNextLevelTextView()
    }

    private fun setupLevelObserver() {
        vm.userLevel.observe(viewLifecycleOwner, Observer {
            lvl_progress.counterValue = it
        })
    }

    private fun setupProgressObserver() {
        vm.currentExpInDegrees.observe(viewLifecycleOwner, Observer {
            lvl_progress.arcSweepAngle = it.toFloat()
        })
    }

    private fun setupExpToNextLevelTextView() {
        vm.expToNextLevel.observe(viewLifecycleOwner, Observer {
            exp_to_lvl_up_tv.text = getString(R.string.exp_to_lvl_up_string, it)
        })
    }
}