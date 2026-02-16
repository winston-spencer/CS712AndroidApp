package edu.ndsu.csci

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_CUSTOM_BROADCAST = "edu.ndsu.csci.ACTION_CUSTOM_BROADCAST"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_CUSTOM_BROADCAST) {
            Toast.makeText(context, "Broadcast received!", Toast.LENGTH_SHORT).show()
        }
    }
}
