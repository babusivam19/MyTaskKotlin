package com.babu.mytaskkotlin.view.activity

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.babu.mytaskkotlin.data.model.FactData
import com.babu.mytaskkotlin.data.model.InformationData
import com.babu.mytaskkotlin.data.model.Response
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import java.util.ArrayList

/**
 * Created by Babu on 7/8/2018.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)
    private var activity: MainActivity? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        activity = activityTestRule.activity
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        activity = null
    }

    @Test
    fun allDataLoadedSuccessfully() {
        val informationData = ArrayList<InformationData>()
        informationData.add(InformationData("Title 1", "Desc 1", ""))
        informationData.add(InformationData("Title 2", "Desc 2", ""))
        val data = FactData("My Title", informationData)

        activity!!.updateResponse(Response.success(data))
        assertEquals(activity!!.supportActionBar!!.title, data.title)
        assertEquals(activity!!.informationListAdapter.itemCount, 2)
    }

    @Test
    fun dataLoadingFailed() {
        activity!!.updateResponse(Response.error(null, Throwable("Something went wrong")))
        assertEquals(activity!!.informationListAdapter.itemCount, 0)
    }
}