package edu.ndsu.csci

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class ServiceStartedBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 2
        private const val CHANNEL_ID = "service_started_channel"
        private const val TAG = "ServiceStartedReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive called with action: ${intent?.action}")
        if (intent?.action == MyForegroundService.ACTION_SERVICE_STARTED && context != null) {
            Log.d(TAG, "Service started action received, showing notification")
            createNotificationChannelIfNeeded(context)
            showServiceStartedNotification(context)
        }
    }

    private fun createNotificationChannelIfNeeded(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Service Started",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notification when service starts"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created")
        }
    }

    private fun showServiceStartedNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.service_started_message))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
        Log.d(TAG, "Notification displayed with ID: $NOTIFICATION_ID")
    }
}

