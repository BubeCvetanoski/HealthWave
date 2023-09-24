package healthWave.core.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class MotivationalNotificationsScheduler(private val context: Context) {

    fun scheduleMotivationalNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MotivationalNotificationsReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Set the alarm to trigger every three hours
        val intervalMillis = 3 * 60 * 60 * 1000
        val triggerAtMillis = System.currentTimeMillis()

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis.toLong(),
            pendingIntent
        )
    }

    fun cancelMotivationalNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MotivationalNotificationsReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }
}