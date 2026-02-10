package edu.ndsu.csci

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ndsu.csci.ui.theme.CS712AndroidAppTheme
import androidx.compose.ui.res.stringArrayResource


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CS712AndroidAppTheme {
                SecondScreen()
            }
        }
    }
}

@Composable
fun SecondScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "Mobile Software Engineering Challenges",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            val challenges = stringArrayResource(R.array.mobile_se_challenges)
            challenges.forEach {
                Text(
                    text = "• $it",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    activity?.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Main Activity")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    CS712AndroidAppTheme {
        SecondScreen()
    }
}
