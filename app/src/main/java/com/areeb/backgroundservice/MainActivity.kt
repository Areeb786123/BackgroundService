package com.areeb.backgroundservice

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.areeb.backgroundservice.ui.theme.BackgroundServiceTheme
import com.areeb.backgroundservice.ui.utils.extension.NotificationHelper

import kotlinx.coroutines.delay

class MainActivity : BaseActivity() {
    private val notificationHelper by lazy { NotificationHelper(this) }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundServiceTheme {
                var counter by remember { mutableIntStateOf(0) }
                LaunchedEffect(Unit) {
                    for (i in 1..100) {
                        delay(100) // update once a second
                        counter = i
                        notificationHelper.updateNotification(
                            progress = i
                        )
                    }
                    notificationHelper.completeNotification()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    Greeting(
                        name = "$counter number",
                        modifier = Modifier
                            .padding(innerPadding)
                            .clickable { notificationHelper.createNotification() }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BackgroundServiceTheme {
        Greeting("Android")
    }
}