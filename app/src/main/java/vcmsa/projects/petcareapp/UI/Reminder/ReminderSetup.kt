package vcmsa.projects.petcareapp.UI.Reminder

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Services.AlarmScheduler
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.databinding.ActivityReminderSetupBinding
import java.util.Calendar

class ReminderSetup : AppCompatActivity() {
    private lateinit var binding: ActivityReminderSetupBinding
//    private var selectedHour = 8
//    private var selectedMinute = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReminderSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        setupUI()
//        setupClickListeners()
    }

//    private fun setupUI() {
//        // Setup reminder type spinner
//        val reminderTypes = arrayOf("Food", "Medicine", "Walk", "Grooming", "Vet Appointment")
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, reminderTypes)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerReminderType.adapter = adapter
//
//        // Setup frequency spinner
//        val frequencies = arrayOf("Once", "Daily", "Weekly", "Monthly")
//        val freqAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frequencies)
//        freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spinnerFrequency.adapter = freqAdapter
//
//        // Set initial time
//        updateTimeButtonText()
//    }
//
//    private fun setupClickListeners() {
//        binding.buttonSelectTime.setOnClickListener {
//            showTimePicker()
//        }
//
//        binding.buttonSaveReminder.setOnClickListener {
//            saveReminder()
//        }
//
//        binding.buttonCancel.setOnClickListener {
//            finish()
//        }
//    }
//
//    private fun showTimePicker() {
//        val timePicker = TimePickerDialog(
//            this,
//            { _, hourOfDay, minute ->
//                selectedHour = hourOfDay
//                selectedMinute = minute
//                updateTimeButtonText()
//            },
//            selectedHour,
//            selectedMinute,
//            false // 24-hour format
//        )
//        timePicker.show()
//    }
//
//    private fun updateTimeButtonText() {
//        val timeString = String.format("%02d:%02d", selectedHour, selectedMinute)
//        binding.buttonSelectTime.text = "Time: $timeString"
//    }
//
//    private fun saveReminder() {
//        lifecycleScope.launch {
//            try {
//                val reminderType = binding.spinnerReminderType.selectedItem as String
//                val frequency = binding.spinnerFrequency.selectedItem as String
//                val petName = binding.editTextPetName.text.toString().trim()
//                val notes = binding.editTextNotes.text.toString().trim()
//
//                if (petName.isEmpty()) {
//                    binding.editTextPetName.error = "Please enter pet name"
//                    return@launch
//                }
//
//                // Generate unique ID for this reminder
//                val reminderId = System.currentTimeMillis().toInt()
//
//                // Create reminder title and body
//                val title = "$reminderType Reminder - $petName"
//                val body = if (notes.isNotEmpty()) notes else "Time for $reminderType"
//
//                // Calculate trigger time
//                val triggerTime = calculateTriggerTime(selectedHour, selectedMinute)
//
//                when (frequency) {
//                    "Once" -> {
//                        AlarmScheduler.scheduleOneTime(
//                            context = this@ReminderSetup,
//                            id = reminderId,
//                            triggerAtMillis = triggerTime,
//                            title = title,
//                            body = body,
//                            useRemindersChannel = true
//                        )
//                    }
//                    "Daily" -> {
//                        AlarmScheduler.scheduleRepeatingDailyAt(
//                            context = this@ReminderSetup,
//                            id = reminderId,
//                            hour = selectedHour,
//                            minute = selectedMinute,
//                            title = title,
//                            body = body,
//                            useRemindersChannel = true
//                        )
//                    }
//                    // Add weekly/monthly scheduling logic here if needed
//                }
//
//                // Save reminder details to local database or SharedPreferences
//                saveReminderToStorage(reminderId, reminderType, frequency, petName, notes, selectedHour, selectedMinute)
//
//                // Show success message and finish
//                showSuccessMessage()
//                finish()
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Show error message
//                Toast.makeText(
//                    this@ReminderSetup,
//                    "Failed to set reminder: ${e.message}",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//    }
//
//    private fun calculateTriggerTime(hour: Int, minute: Int): Long {
//        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, hour)
//            set(Calendar.MINUTE, minute)
//            set(Calendar.SECOND, 0)
//            set(Calendar.MILLISECOND, 0)
//
//            // If the time is before now, schedule for next day
//            if (timeInMillis <= System.currentTimeMillis()) {
//                add(Calendar.DAY_OF_YEAR, 1)
//            }
//        }
//        return calendar.timeInMillis
//    }
//
//    private fun saveReminderToStorage(
//        id: Int,
//        type: String,
//        frequency: String,
//        petName: String,
//        notes: String,
//        hour: Int,
//        minute: Int
//    ) {
//        // Save to SharedPreferences or Room database
//        val sharedPrefs = getSharedPreferences("pet_reminders", Context.MODE_PRIVATE)
//        val reminders = sharedPrefs.getStringSet("active_reminders", mutableSetOf()) ?: mutableSetOf()
//
//        val reminderData = "$id|$type|$frequency|$petName|$notes|$hour|$minute"
//        reminders.add(reminderData)
//
//        sharedPrefs.edit().putStringSet("active_reminders", reminders).apply()
//    }
//
//    private fun showSuccessMessage() {
//        Toast.makeText(
//            this,
//            "Reminder set successfully!",
//            Toast.LENGTH_SHORT
//        ).show()
//    }
}
// Reference list:

// Phillipp Lackner. 2022. Local Notifications in Android - The Full Guide (Android Studio Tutorial).[video online]. Available at: https://www.youtube.com/watch?v=LP623htmWcI [Accessed 18 November 2025].
