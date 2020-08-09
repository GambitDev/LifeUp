package com.gambitdev.lifeup.util

import com.gambitdev.lifeup.enums.Category
import com.gambitdev.lifeup.models.Task
import com.gambitdev.lifeup.models.TaskList
import com.gambitdev.lifeup.models.UserStats
import com.gambitdev.lifeup.room.TaskDao

class InitTaskDataUtil {
    companion object {
        val initTasks = listOf(
            //Category.SOCIAL
            Task("Call a friend for a chat").apply {
                category = Category.SOCIAL
                expWorth = 50
            },
            Task("Schedule for a beer with a friend").apply {
                category = Category.SOCIAL
                expWorth = 100
            },
            Task("Schedule to hang out with a friend").apply {
                category = Category.SOCIAL
                expWorth = 100
            },
            Task("Call a family member").apply {
                category = Category.FAMILY
                expWorth = 50
            },
            //Category.FAMILY
            Task("Send some photos of Isaac").apply {
                category = Category.FAMILY
                expWorth = 70
            },
            Task("Send a video of Isaac").apply {
                category = Category.FAMILY
                expWorth = 80
            },
            Task("Schedule to go visit").apply {
                category = Category.FAMILY
                expWorth = 200
            },
            Task("Invite Michael over").apply {
                category = Category.FAMILY
                expWorth = 150
            },
            Task("Invite Tamar over").apply {
                category = Category.FAMILY
                expWorth = 150
            },
            Task("Invite family over").apply {
                category = Category.FAMILY
                expWorth = 200
            },
            //Category.RELATIONSHIP
            Task("Talk with Elian about her day for 20 min").apply {
                category = Category.RELATIONSHIP
                expWorth = 150
            },
            Task("Take Elian out for a walk").apply {
                category = Category.RELATIONSHIP
                expWorth = 200
            },
            Task("Make plans for a date with Elian").apply {
                category = Category.RELATIONSHIP
                expWorth = 400
            },
            Task("Buy Elian flowers").apply {
                category = Category.RELATIONSHIP
                expWorth = 150
            },
            Task("Compliment Elian").apply {
                category = Category.RELATIONSHIP
                expWorth = 100
            },
            Task("Contemplate about your feelings for Elian for 15 min").apply {
                category = Category.RELATIONSHIP
                expWorth = 200
            },
            Task("Learn something with Elian").apply {
                category = Category.RELATIONSHIP
                expWorth = 200
            },
            Task("Surprise Elian").apply {
                category = Category.RELATIONSHIP
                expWorth = 300
            },
            //Category.CAREER
            Task("Work on your CV").apply {
                category = Category.CAREER
                expWorth = 50
            },
            Task("Apply for 1 job").apply {
                category = Category.CAREER
                expWorth = 100
            },
            Task("Work on your LinkedIn profile").apply {
                category = Category.CAREER
                expWorth = 50
            },
            //Category.HOBBIES
            Task("Play the guitar for 30 min").apply {
                category = Category.HOBBIES
                expWorth = 50
            },
            Task("Try drawing something").apply {
                category = Category.HOBBIES
                expWorth = 50
            },
            Task("Work on some lyrics for a song").apply {
                category = Category.HOBBIES
                expWorth = 80
            },
            Task("Work on a chord progression for a song").apply {
                category = Category.HOBBIES
                expWorth = 60
            },
            Task("Work on song recordings").apply {
                category = Category.HOBBIES
                expWorth = 90
            },
            Task("Code for 30 min").apply {
                category = Category.HOBBIES
                expWorth = 150
            },
            //Category.FATHERHOOD
            Task("Spend 20 minutes with Isaac (NO PHONE)").apply {
                category = Category.FATHERHOOD
                expWorth = 150
            },
            Task("Take Isaac to the park").apply {
                category = Category.FATHERHOOD
                expWorth = 200
            },
            Task("Help Isaac practice his speaking for 15 min").apply {
                category = Category.FATHERHOOD
                expWorth = 150
            },
            Task("Take Isaac to the playing grounds").apply {
                category = Category.FATHERHOOD
                expWorth = 200
            },
            Task("Think about Isaac education & up-bringing for 10 min").apply {
                category = Category.FATHERHOOD
                expWorth = 200
            },
            //Category.FITNESS
            Task("Stretch for 15 min").apply {
                category = Category.FITNESS
                expWorth = 70
            },
            Task("Do Yoga for 15 min").apply {
                category = Category.FITNESS
                expWorth = 70
            },
            Task("Go for a jog").apply {
                category = Category.FITNESS
                expWorth = 150
            },
            Task("Do 3 sets of 10 push-ups").apply {
                category = Category.FITNESS
                expWorth = 100
            },
            Task("Do 3 sets of 10 squats").apply {
                category = Category.FITNESS
                expWorth = 100
            },
            Task("Do 3 sets of 20 crunches").apply {
                category = Category.FITNESS
                expWorth = 100
            },
            //Category.SPIRIT_MIND
            Task("Meditate for 10 min").apply {
                category = Category.SPIRIT_MIND
                expWorth = 100
            },
            Task("Contemplate about the meaning of God in your life for 5 min").apply {
                category = Category.SPIRIT_MIND
                expWorth = 150
            },
            Task("Perform a mitzva").apply {
                category = Category.SPIRIT_MIND
                expWorth = 200
            },
            Task("Learn some Hassidut").apply {
                category = Category.SPIRIT_MIND
                expWorth = 200
            },
            //Category.INTELLECT
            Task("Learn something new about programming").apply {
                category = Category.INTELLECT
                expWorth = 60
            },
            Task("Learn something new about math").apply {
                category = Category.INTELLECT
                expWorth = 50
            },
            Task("Learn something new about physics").apply {
                category = Category.INTELLECT
                expWorth = 50
            },
            Task("Learn something new about chemistry").apply {
                category = Category.INTELLECT
                expWorth = 50
            },
            Task("Learn something new about biology").apply {
                category = Category.INTELLECT
                expWorth = 50
            },
            Task("Read a chapter from a book").apply {
                category = Category.INTELLECT
                expWorth = 70
            },
            Task("Learn something new about music").apply {
                category = Category.INTELLECT
                expWorth = 60
            },
            Task("Learn something new about music production").apply {
                category = Category.INTELLECT
                expWorth = 60
            },
            Task("Read a random Wikipedia entry").apply {
                category = Category.INTELLECT
                expWorth = 50
            }
        )
        fun prepopulateDatabase(dao: TaskDao) {
            //init tasks
            initTasks.forEach{
                dao.insertTask(it)
            }
            //init task list
            dao.insertTaskList(TaskList())
            //init user stats
            dao.insertUserStats(UserStats())
        }
    }
}