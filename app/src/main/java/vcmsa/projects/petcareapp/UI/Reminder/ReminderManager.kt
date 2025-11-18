package vcmsa.projects.petcareapp.UI.Reminder

import android.content.Context
import vcmsa.projects.petcareapp.Data.Services.AlarmScheduler
import vcmsa.projects.petcareapp.Data.Models.Reminder

object ReminderManager {
      //this class saved the data locally to the device with shared preferences (Phillipp Lackner, 2022):
//    fun getActiveReminders(context: Context): List<Reminder> {
//        val sharedPrefs = context.getSharedPreferences("pet_reminders", Context.MODE_PRIVATE)
//        val remindersSet = sharedPrefs.getStringSet("active_reminders", setOf()) ?: setOf()
//
//        return remindersSet.mapNotNull { data ->
//            try {
//                val parts = data.split("|")
//                if (parts.size == 7) {
//                    Reminder(
//                        id = parts[0].toInt(),
//                        type = parts[1],
//                        frequency = parts[2],
//                        petName = parts[3],
//                        notes = parts[4],
//                        hour = parts[5].toInt(),
//                        minute = parts[6].toInt()
//                    )
//                } else null
//            } catch (e: Exception) {
//                null
//            }
//        }
//    }
//
//    fun cancelReminder(context: Context, reminderId: Int) {
//        // Cancel the alarm
//        AlarmScheduler.cancelScheduled(context, reminderId)
//
//        // Remove from storage
//        val sharedPrefs = context.getSharedPreferences("pet_reminders", Context.MODE_PRIVATE)
//        val reminders = sharedPrefs.getStringSet("active_reminders", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
//
//        reminders.removeIf { it.startsWith("$reminderId|") }
//        sharedPrefs.edit().putStringSet("active_reminders", reminders).apply()
//    }

}


// Reference list:

// Phillipp Lackner. 2022. Local Notifications in Android - The Full Guide (Android Studio Tutorial).[video online]. Available at: https://www.youtube.com/watch?v=LP623htmWcI [Accessed 18 November 2025].


