package healthWave.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import healthWave.core.util.MotivationalQuotes.listOfMotivationalQuotes

class MotivationalNotificationsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationsService = MotivationalNotificationsService(context)

        val notificationId = System.currentTimeMillis().toInt()
        val randomIndex = (1..130).random()
        val motivationalQuote = listOfMotivationalQuotes[randomIndex] //randomly sending the motivational quotes

        notificationsService.showMotivationalNotification(
            notificationId = notificationId,
            motivationalQuote = motivationalQuote
        )
    }
}