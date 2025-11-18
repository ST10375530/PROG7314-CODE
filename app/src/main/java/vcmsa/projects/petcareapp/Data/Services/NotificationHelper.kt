package vcmsa.projects.petcareapp.Data.Services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Main.MainActivity
import vcmsa.projects.petcareapp.UI.PetProfiles.PetProfileActivity

object NotificationHelper {
//    const val CHANNEL_ID_GENERAL = "petcare_general"
//    const val CHANNEL_ID_REMINDERS = "petcare_reminders"
//
     //Building the notification on some devices as it wont work on devices before O (Phillipp Lackner, 2022):
//    fun createNotificationChannels(context: Context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            val general = NotificationChannel(
//                CHANNEL_ID_GENERAL,
//                "General notifications",
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply { description = "General app notifications" }
//
//            val reminders = NotificationChannel(
//                CHANNEL_ID_REMINDERS,
//                "Reminders",
//                NotificationManager.IMPORTANCE_HIGH
//            ).apply { description = "Medicine and appointment reminders" }
//
//            nm.createNotificationChannel(general)
//            nm.createNotificationChannel(reminders)
//        }
//    }
//
      //making sure that permision was granted before the notification is posted (Phillipp Lackner, 2022):
//    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
//    fun notifyNow(
//        context: Context,
//        id: Int,
//        title: String,
//        body: String,
//        useRemindersChannel: Boolean = false,
//        petName: String? = null
//    ) {
//
//        createNotificationChannels(context)
            //checking the sdk version as some devices might not have this feature (Phillipp Lackner, 2022):
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            val has = ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) == PackageManager.PERMISSION_GRANTED
//
//            if (!has) {
//
//                return
//            }
//        }
          //setting up the notification channel (Phillipp Lackner, 2022):
//        val channel = if (useRemindersChannel) CHANNEL_ID_REMINDERS else CHANNEL_ID_GENERAL
//
//        val contentIntent = Intent(context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//          //designing how much space the notification must be (Phillipp Lackner, 2022):
//        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        else PendingIntent.FLAG_UPDATE_CURRENT
//        val contentPendingIntent = PendingIntent.getActivity(
//            context,
//            id,
//            contentIntent,
//            pendingIntentFlags
//        )
//
//        //building the notification context - telling it what to have and display (Phillipp Lackner, 2022):
//        val builder = NotificationCompat.Builder(context, channel)
//            .setContentTitle(title)
//            .setContentText(body)
//            .setSmallIcon(R.drawable.ic_dog)
//            .setAutoCancel(true)
//            .setContentIntent(contentPendingIntent)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//
//        petName?.let { name ->
                //getting the pets details and making the notificaiton specific to the pet (Phillipp Lackner, 2022):
//            val profileIntent = Intent(context, PetProfileActivity::class.java).apply {
//                putExtra("pet_name", name)
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            val actionPendingIntent = PendingIntent.getActivity(
//                context,
//                id + 1,
//                profileIntent,
//                pendingIntentFlags
//            )
//            builder.addAction(0, "View Profile", actionPendingIntent)
//        }
//        try {
//            NotificationManagerCompat.from(context).notify(id, builder.build())
//        } catch (se: SecurityException) {
//            se.printStackTrace()
//        }
//    }
}

// Reference list:

// Phillipp Lackner. 2022. Local Notifications in Android - The Full Guide (Android Studio Tutorial).[video online]. Available at: https://www.youtube.com/watch?v=LP623htmWcI [Accessed 18 November 2025].
