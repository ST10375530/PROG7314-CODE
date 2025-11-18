package vcmsa.projects.petcareapp.Data.Services

import android.content.Context
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

object BiometricHelper {

    fun isBiometricAvailable(context: Context): Boolean {
        //creating the biometric manager with the provided context (Developers, 2025):
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            //checking that the Device supports biometrics and has at least one enrolled method (Developers, 2025):
            BiometricManager.BIOMETRIC_SUCCESS -> true
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // biometric is available but not enrolled
                true
            }
            else -> false
        }
    }
    //this function actually creates the display of the biometric
    fun showBiometricPrompt(
        //setting the requirements to provide context and success/failure checker (Developers, 2025):
        activity: FragmentActivity,
        title: String = "Authentication Required",
        subtitle: String = "Confirm your identity to access settings",
        onSuccess: () -> Unit,
        onError: (errorCode: Int, errString: CharSequence) -> Unit,
        onFailed: () -> Unit = {}
    ) {
        val executor: Executor = ContextCompat.getMainExecutor(activity)
        //creating the biometric prompt in an pop-up format (Developers, 2025):
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }
                //This handles any errors that occur during authentication (Developers, 2025):
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errorCode, errString)
                    //if authentication is cancelled or failed, finish the activity
                    if (errorCode != BiometricPrompt.ERROR_USER_CANCELED &&
                        errorCode != BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        activity.finish()
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onFailed()
                }
            })

        //providing the biometric layout (Developers, 2025):
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .setConfirmationRequired(false)
            .build()
        //assigning the speicic layout to the actual prompt
        biometricPrompt.authenticate(promptInfo)
    }
}

//Reference list:

//Developers. 2025. BiometricManager. [Online]. Available at: https://developer.android.com/reference/androidx/biometric/BiometricManager [Accessed 18 November 2025].

//Developers. 2025. BiometricPrompt. [Online]. Available at: https://developer.android.com/reference/androidx/biometric/BiometricPrompt [Accessed 18 November 2025].

//Developers. 2025. Show a biometric authentication dialog. [Online]. Available at: https://developer.android.com/identity/sign-in/biometric-auth [Accessed 18 November 2025].