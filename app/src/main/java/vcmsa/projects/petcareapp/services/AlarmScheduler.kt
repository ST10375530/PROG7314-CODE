package vcmsa.projects.petcareapp.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

object AlarmScheduler {

    private fun buildPendingIntent(
        context: Context,
        id: Int,
        title: String,
        body: String,
        useRemindersChannel: Boolean
    ): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(NotificationReceiver.EXTRA_NOTIFICATION_ID, id)
            putExtra(NotificationReceiver.EXTRA_TITLE, title)
            putExtra(NotificationReceiver.EXTRA_BODY, body)
            putExtra(NotificationReceiver.EXTRA_USE_REMINDERS, useRemindersChannel)
        }

        val flags = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S)
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        else PendingIntent.FLAG_UPDATE_CURRENT

        return PendingIntent.getBroadcast(context, id, intent, flags)
    }

    fun scheduleOneTime(
        context: Context,
        id: Int,
        triggerAtMillis: Long,
        title: String,
        body: String,
        useRemindersChannel: Boolean = true
    ) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pending = buildPendingIntent(context, id, title, body, useRemindersChannel)

        // Use setExactAndAllowWhileIdle for exact delivery (requires battery tradeoffs)
        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending)
    }

    fun scheduleRepeatingDailyAt(
        context: Context,
        id: Int,
        hour: Int,
        minute: Int,
        title: String,
        body: String,
        useRemindersChannel: Boolean = true
    ) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            // If the time is before now, schedule for next day
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pending = buildPendingIntent(context, id, title, body, useRemindersChannel)

        // Use setInexactRepeating to save battery, or setExactAndAllowWhileIdle each day (more battery)
        am.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pending
        )
    }

    fun cancelScheduled(context: Context, id: Int) {
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pending = buildPendingIntent(context, id, "", "", false)
        am.cancel(pending)
    }
}
