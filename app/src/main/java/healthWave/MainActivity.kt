package healthWave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import healthWave.core.notifications.MotivationalNotificationsService

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationService = MotivationalNotificationsService(applicationContext)

        setContent {
            AppDestinations(notificationService = notificationService)
        }
    }
}