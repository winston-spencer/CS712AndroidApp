package edu.ndsu.csci

import android.app.Service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "service_channel"
        const val ACTION_SERVICE_STARTED = "edu.ndsu.csci.ACTION_SERVICE_STARTED"
        private const val TAG = "MyForegroundService"
    }

    override fun onCreate() {
        super.onCreate()
        // No longer need to create channels here as they're created in onStartCommand
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand called")

        // Create notification channel if needed
        createNotificationChannelIfNeeded()

        val foregroundNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.service_notification_title))
            .setContentText(getString(R.string.service_notification_text))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(NOTIFICATION_ID, foregroundNotification)

        // Show service started notification
        Log.d(TAG, "Showing service started notification")
        showServiceStartedNotification()

        // Also send broadcast to notify UI that service has started
        val broadcastIntent = Intent(ACTION_SERVICE_STARTED)
        Log.d(TAG, "Sending broadcast with action: $ACTION_SERVICE_STARTED")
        sendBroadcast(broadcastIntent)
        Log.d(TAG, "Broadcast sent")

        return START_STICKY
    }

    private fun showServiceStartedNotification() {
        val notification = NotificationCompat.Builder(this, "service_started_channel")
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.service_started_message))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.notify(2, notification)
        Log.d(TAG, "Service started notification displayed")
    }

    private fun createNotificationChannelIfNeeded() {
        val notificationImportance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service Channel",
                notificationImportance
            ).apply {
                description = "Channel for foreground service notifications"
            }

            val serviceStartedChannel = NotificationChannel(
                "service_started_channel",
                "Service Started",
                notificationImportance
            ).apply {
                description = "Notification when service starts"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
            notificationManager?.createNotificationChannel(serviceStartedChannel)
            Log.d(TAG, "Notification channels created")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

