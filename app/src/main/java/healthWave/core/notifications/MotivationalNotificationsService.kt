package healthWave.core.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.healthwave.R
import healthWave.MainActivity
import healthWave.core.util.Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_ID

class MotivationalNotificationsService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val notificationScheduler = MotivationalNotificationsScheduler(context)

    fun showMotivationalNotification(notificationId: Int, motivationalQuote: String) {
        val activityIntent = Intent(context, MainActivity::class.java)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(motivationalQuote)

        val notification = NotificationCompat.Builder(context, MOTIVATIONAL_NOTIFICATIONS_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentText(motivationalQuote)
            .setContentIntent(activityPendingIntent)
            .setStyle(bigTextStyle)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    fun scheduleMotivationalNotifications() {
        notificationScheduler.scheduleMotivationalNotifications()
    }

    fun cancelMotivationalNotifications() {
        notificationScheduler.cancelMotivationalNotifications()
    }
}