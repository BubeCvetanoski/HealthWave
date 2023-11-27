package healthWave

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import healthWave.core.notifications.MotivationalNotificationsService.Companion.createMotivationalNotificationChannel

@HiltAndroidApp
class HealthWaveApp : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationContext.createMotivationalNotificationChannel()
    }
}