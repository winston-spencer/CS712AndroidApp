package edu.ndsu.csci

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import edu.ndsu.csci.ui.theme.CS712AndroidAppTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val broadcastReceiver = MyBroadcastReceiver()
    private lateinit var serviceStartedReceiver: ServiceStartedBroadcastReceiver

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startTheService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Register broadcast receiver dynamically
        registerBroadcastReceiver()

        setContent {
            CS712AndroidAppTheme {
                MainScreen(
                    onStartService = { handleStartService() },
                    onSendBroadcast = { sendCustomBroadcast() }
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // Unregister broadcast receiver to prevent memory leaks
        unregisterBroadcastReceiver()
    }

    private fun handleStartService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startTheService()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            startTheService()
        }
    }

    private fun startTheService() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
    }

    private fun sendCustomBroadcast() {
        val intent = Intent(MyBroadcastReceiver.ACTION_CUSTOM_BROADCAST)
        intent.setPackage(packageName)
        sendBroadcast(intent)
    }


    private fun registerBroadcastReceiver() {
        Log.d(TAG, "Registering broadcast receivers")
        val filter = IntentFilter(MyBroadcastReceiver.ACTION_CUSTOM_BROADCAST)
        ContextCompat.registerReceiver(
            this,
            broadcastReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        Log.d(TAG, "Custom broadcast receiver registered")

        // Register service started receiver
        serviceStartedReceiver = ServiceStartedBroadcastReceiver()
        val serviceStartedFilter = IntentFilter(MyForegroundService.ACTION_SERVICE_STARTED)
        Log.d(TAG, "About to register service started receiver for action: ${MyForegroundService.ACTION_SERVICE_STARTED}")
        try {
            ContextCompat.registerReceiver(
                this,
                serviceStartedReceiver,
                serviceStartedFilter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            Log.d(TAG, "Service started receiver registered successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to register service started receiver", e)
        }
    }

    private fun unregisterBroadcastReceiver() {
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (_: IllegalArgumentException) {
            // Receiver was already unregistered
        }
        try {
            unregisterReceiver(serviceStartedReceiver)
        } catch (_: IllegalArgumentException) {
            // Receiver was already unregistered
        }
    }
}

@Composable
fun MainScreen(
    onStartService: () -> Unit = {},
    onSendBroadcast: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.student_name),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.student_id),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.button_start_activity_explicitly),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent("edu.ndsu.csci.ACTION_SHOW_CHALLENGES")
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.button_start_activity_implicitly))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartService,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.button_start_service))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSendBroadcast,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.button_send_broadcast))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CS712AndroidAppTheme(
        dynamicColor = false
    ) {
        MainScreen()
    }
}
