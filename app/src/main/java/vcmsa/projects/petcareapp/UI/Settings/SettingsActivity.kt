package vcmsa.projects.petcareapp.UI.Settings
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Services.BiometricHelper
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Login.LoginActivity
import vcmsa.projects.petcareapp.databinding.ActivitySettingsBinding
import androidx.biometric.BiometricPrompt
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Reminder.ReminderSetup

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val auth = FirebaseAuth.getInstance()
    private var isAuthenticated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check if user is already authenticated for this session
        if (!isAuthenticated) {
            authenticateUser()
            return
        }
        initializeUI()
    }

    private fun authenticateUser() {
        // Call the backend methods to make sure the biometrics are available
        if (BiometricHelper.isBiometricAvailable(this)) {
            BiometricHelper.showBiometricPrompt(
                activity = this,
                title = "Settings Access",
                subtitle = "Authenticate to access your settings",
                onSuccess = {
                    isAuthenticated = true
                    initializeUI()
                },
                onError = { errorCode, errString ->
                    Toast.makeText(this, "Authentication failed: $errString", Toast.LENGTH_SHORT).show()
                    if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                        finish()
                    }
                }
            )
        } else {
            // If biometric is not available, show a message and close
            MaterialAlertDialogBuilder(this)
                .setTitle("Authentication Required")
                .setMessage("Device security (biometric/password) is required to access settings but is not available on this device.")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .setOnDismissListener {
                    finish()
                }
                .show()
        }
    }

    private fun initializeUI() {
        // Apply theme before setting content view
        ThemeManager.initializeTheme(this)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupThemeSwitch()
        setupClickListeners()
        setupNotificationButton() // Add this method call
    }

    private fun setupNotificationButton() {
        binding.addNotificationBtn.setOnClickListener {
            val intent = Intent(this@SettingsActivity, ReminderSetup::class.java)
            startActivity(intent)
        }
    }

    private fun setupThemeSwitch() {
        // Set initial state based on current theme
        val currentTheme = ThemeManager.getCurrentTheme(this)
        binding.themeSwitch.isChecked = when (currentTheme) {
            ThemeManager.THEME_DARK -> true
            ThemeManager.THEME_LIGHT -> false
            else -> AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        }
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.accountDetailsButton.setOnClickListener {
            showAccountDetails()
        }
        binding.passwordChangeButton.setOnClickListener {
            showPasswordChangeDialog()
        }
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            handleThemeChange(isChecked)
        }
        binding.notificationsButton.setOnClickListener {
            showNotificationsMessage()
        }
        binding.logoutButton.setOnClickListener {
            showLogoutMessage()
        }
    }

    // ... rest of your methods remain the same (showAccountDetails, changePassword, etc.)
    private fun handleThemeChange(isDarkMode: Boolean) {
        val theme = if (isDarkMode) ThemeManager.THEME_DARK else ThemeManager.THEME_LIGHT
        ThemeManager.setTheme(this, theme)
        val mode = if (isDarkMode) "Dark" else "Light"
        Toast.makeText(this, "App theme set to $mode mode", Toast.LENGTH_SHORT).show()
        recreate()
    }

    private fun showAccountDetails() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val accountDetailsMessage = """
                Account Information:

                Email: ${currentUser.email ?: "Not set"}
                User ID: ${currentUser.uid}
                Email Verified: ${if (currentUser.isEmailVerified) "Yes" else "No"}
                Account Created: ${getAccountCreationInfo()}

                Provider: ${currentUser.providerId}
            """.trimIndent()
            showMessageDialog("Your Account Details", accountDetailsMessage)
        } else {
            showMessageDialog("Account Details", "You are not currently signed in. Please log in to view your account details.")
        }
    }

    private fun getAccountCreationInfo(): String {
        val user = auth.currentUser
        return user?.metadata?.creationTimestamp?.let {
            val date = java.util.Date(it)
            java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(date)
        } ?: "Unknown"
    }

    private fun showPasswordChangeDialog() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            showMessageDialog("Change Password", "Please sign in to change your password.")
            return
        }
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_password, null)
        val etCurrentPassword = dialogView.findViewById<TextInputEditText>(R.id.etCurrentPassword)
        val etNewPassword = dialogView.findViewById<TextInputEditText>(R.id.etNewPassword)
        val etConfirmPassword = dialogView.findViewById<TextInputEditText>(R.id.etConfirmPassword)
        MaterialAlertDialogBuilder(this)
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Change Password") { dialog, _ ->
                val currentPassword = etCurrentPassword.text?.toString()?.trim()
                val newPassword = etNewPassword.text?.toString()?.trim()
                val confirmPassword = etConfirmPassword.text?.toString()?.trim()
                if (currentPassword.isNullOrEmpty() || newPassword.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (newPassword != confirmPassword) {
                    Toast.makeText(this, "New passwords don't match", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (newPassword.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                changePassword(currentPassword, newPassword)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        lifecycleScope.launch {
            try {
                val user = auth.currentUser
                val credential = EmailAuthProvider.getCredential(user?.email ?: "", currentPassword)
                user?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this@SettingsActivity, "Password updated successfully", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@SettingsActivity, "Failed to update password: ${updateTask.exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        val exception = reauthTask.exception
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this@SettingsActivity, "Current password is incorrect", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this@SettingsActivity, "Re-authentication failed: ${exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showNotificationsMessage() {
        showMessageDialog("Notification Settings",
            "Notification customization features are coming soon!\n\n" +
                    "You'll be able to manage:\n" +
                    "• Appointment reminders\n" +
                    "• Health notifications\n" +
                    "• Promotional updates\n" +
                    "• System alerts")
    }

    private fun showLogoutMessage() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout Confirmation")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes, Logout") { dialog, _ ->
                performLogout()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun performLogout() {
        lifecycleScope.launch {
            try {
                auth.signOut()
                Toast.makeText(this@SettingsActivity, "Logged out successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Logout failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showMessageDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}