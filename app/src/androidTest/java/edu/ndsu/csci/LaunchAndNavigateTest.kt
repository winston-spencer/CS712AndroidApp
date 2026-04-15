package edu.ndsu.csci

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * A simple UI Automator test that verifies end-to-end navigation
 */
private const val LAUNCH_TIMEOUT = 5_000L

@RunWith(AndroidJUnit4::class)
class LaunchAndNavigateTest {

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.pressHome()

        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val intent = context.packageManager.getLaunchIntentForPackage("edu.ndsu.csci")
            ?: Intent().apply {
                setPackage("edu.ndsu.csci")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg("edu.ndsu.csci").depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun testNavigateToSecondActivityAndVerifyContent() {
        val startButton = device.wait(Until.findObject(By.text("Start Activity Explicitly")), LAUNCH_TIMEOUT)
        assertNotNull("Start Activity Explicitly button not found", startButton)
        startButton.click()

        device.wait(Until.hasObject(By.pkg("edu.ndsu.csci").depth(0)), LAUNCH_TIMEOUT)

        val deviceFragmentation = device.wait(Until.findObject(By.textContains("Device Fragmentation")), LAUNCH_TIMEOUT)
        assertNotNull("Device Fragmentation text not found on SecondActivity", deviceFragmentation)
    }
}