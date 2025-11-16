package vcmsa.projects.petcareapp.UI.Main


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.services.NotificationHelper
import vcmsa.projects.petcareapp.services.AlarmScheduler
import vcmsa.projects.petcareapp.UI.AddPets.AddPetsActivity

class MainActivity : AppCompatActivity() {

    // STEP 5: Permission Launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // User chose allow/deny
        if (isGranted) {
            // Notifications allowed
        } else {
            // Notifications denied
        }
    }

    // STEP 5: Check notification permission
    private fun ensureNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Already allowed
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    // STEP 6: Setup notifications and buttons
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // <-- Your XML file

        // Create notification channels (MUST BE FIRST)
        NotificationHelper.createNotificationChannels(this)

        // Ask permission BEFORE sending notifications
        ensureNotificationPermission()

        // IMMEDIATE notification
        findViewById<Button>(R.id.save_pet_button)?.setOnClickListener {
            NotificationHelper.notifyNow(
                this,
                100,
                "Hello from PetCare",
                "Welcome back to PetCare!",
                false
            )
        }

        // ONE-TIME notification (2 minutes later)
        findViewById<Button>(R.id.btnScheduleOneTime)?.setOnClickListener {
            val triggerAt = System.currentTimeMillis() + (2 * 60 * 1000)
            AlarmScheduler.scheduleOneTime(
                this,
                id = 200,
                triggerAtMillis = triggerAt,
                title = "Vet appointment",
                body = "Vet appointment in 2 minutes!",
                useRemindersChannel = true
            )
        }

        // DAILY repeating notification @ 08:00
        findViewById<Button>(R.id.btnScheduleDaily)?.setOnClickListener {
            AlarmScheduler.scheduleRepeatingDailyAt(
                this,
                id = 300,
                hour = 12,
                minute = 0,
                title = "Medicine time",
                body = "Give your pet its morning medicine.",
                useRemindersChannel = true
            )
        }
    }
}
