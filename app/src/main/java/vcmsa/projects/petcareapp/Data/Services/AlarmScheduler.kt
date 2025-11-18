package vcmsa.projects.petcareapp.Data.Services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
//import vcmsa.projects.petcareapp.Data.Services.NotificationReceiver
import java.util.Calendar

object AlarmScheduler {

//    private fun buildPendingIntent(
//        context: Context,
//        id: Int,
//        title: String,
//        body: String,
//        useRemindersChannel: Boolean
//    ): PendingIntent {
//        val intent = Intent(context, NotificationReceiver::class.java).apply {
//            putExtra(NotificationReceiver.Companion.EXTRA_NOTIFICATION_ID, id)
//            putExtra(NotificationReceiver.Companion.EXTRA_TITLE, title)
//            putExtra(NotificationReceiver.Companion.EXTRA_BODY, body)
//            putExtra(NotificationReceiver.Companion.EXTRA_USE_REMINDERS, useRemindersChannel)
//        }
          //getting the builds sdk and making sure its above a certain version (Phillipp Lackner, 2022):
//        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        else PendingIntent.FLAG_UPDATE_CURRENT
//
//        return PendingIntent.getBroadcast(context, id, intent, flags)
//    }
      //This is for one time notifications and will be used for appointment reminders (Phillipp Lackner, 2022):
//    fun scheduleOneTime(
//        context: Context,
//        id: Int,
//        triggerAtMillis: Long,
//        title: String,
//        body: String,
//        useRemindersChannel: Boolean = true
//    ) {
//        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pending = buildPendingIntent(context, id, title, body, useRemindersChannel)
//
//        //use setExactAndAllowWhileIdle as it will wake up your phone to trigger the notification when needed (Phillipp Lackner, 2022):
//        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pending)
//    }
      //this method will be used for medicine/food reminders (Phillipp Lackner, 2022):
//    fun scheduleRepeatingDailyAt(
//        context: Context,
//        id: Int,
//        hour: Int,
//        minute: Int,
//        title: String,
//        body: String,
//        useRemindersChannel: Boolean = true
//    ) {
          //creating the built in calander to set the day and time it must trigger (Phillipp Lackner, 2022):
//        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, hour)
//            set(Calendar.MINUTE, minute)
//            set(Calendar.SECOND, 0)
//            set(Calendar.MILLISECOND, 0)
//            // If the time is before now, schedule for next day
//            if (timeInMillis <= System.currentTimeMillis()) {
//                add(Calendar.DAY_OF_YEAR, 1)
//            }
//        }
          // initializes the alarm manager to be used at a certain time (Phillipp Lackner, 2022):
//        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pending = buildPendingIntent(context, id, title, body, useRemindersChannel)
          //this actually setups up the alarm settings (Phillipp Lackner, 2022):
//        am.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pending
//        )
//    }
     // a method to cancel a scheduled alarm (Phillipp Lackner, 2022):
//    fun cancelScheduled(context: Context, id: Int) {
//        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val pending = buildPendingIntent(context, id, "", "", false)
//        am.cancel(pending)
//      }
}

// Reference list:

// Phillipp Lackner. 2022. Local Notifications in Android - The Full Guide (Android Studio Tutorial).[video online]. Available at: https://www.youtube.com/watch?v=LP623htmWcI [Accessed 18 November 2025].
