package healthWave.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.healthwave.R
import healthWave.MainActivity
import healthWave.core.util.Constants
import healthWave.core.util.Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_ID

class MotivationalNotificationsService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
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

    companion object {
        fun Context.createMotivationalNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    MOTIVATIONAL_NOTIFICATIONS_CHANNEL_ID,
                    Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.description = Constants.MOTIVATIONAL_NOTIFICATIONS_CHANNEL_DESCRIPTION

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}