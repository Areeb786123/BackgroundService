package com.areeb.backgroundservice.ui.utils.extension

import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.areeb.backgroundservice.R

import android.app.NotificationChannel


class NotificationHelper(private val context: Context) {
    private val notificationId = 1001
    private val channelId = "backgroundServiceId"
    private val channelName = "Background Service Notifications"
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Reuse a single builder instance
    private val notificationBuilder: NotificationCompat.Builder by lazy {
        NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setPriority(NotificationCompat.PRIORITY_LOW) // Set a default priority
        }
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for background service notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification() {
        notificationBuilder.apply {
            setContentTitle("This is a background test")
            setContentText("Notification created")
            setProgress(0, 0, false) // No progress initially
            setSilent(false)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun updateNotification(progress: Int) {
        notificationBuilder.apply {
            setContentTitle("Updating background test")
            setContentText("Progress: $progress%")
            setProgress(100, progress, false)
            setSilent(true)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    fun completeNotification() {
        notificationBuilder.apply {
            setContentTitle("Notification complete")
            setContentText(null) // Clear any previous text
            clearActions()
            setSilent(false)
        }
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
