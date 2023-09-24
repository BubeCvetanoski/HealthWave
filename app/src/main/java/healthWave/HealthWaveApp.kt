package healthWave

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import healthWave.core.util.Constants
import healthWave.core.util.Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_DESCRIPTION
import healthWave.core.util.Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_NAME

@HiltAndroidApp
class HealthWaveApp : Application() {

    override fun onCreate() {
        super.onCreate()

        createMotivationNotificationChannel()
    }

    private fun createMotivationNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_ID,
                MOTIVATIONAL_NOTIFICATIONS_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = MOTIVATIONAL_NOTIFICATIONS_CHANNEL_DESCRIPTION

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}