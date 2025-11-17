package vcmsa.projects.petcareapp.UI.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Network.AndroidConnectivityObserver
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Register.RegisterActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 9001
    }
    //creating the new instance of the viewmodel (Lackner, 2024):
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            AndroidConnectivityObserver(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.isConnected.collect { connected ->
                    if(connected != null) {
                        handleConnectivity(connected)
                    }
                }
            }
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            var email = binding.loginEditEmail.text.toString()
            var password =  binding.loginEditPassword.text.toString()
            loginViewModel.signInWIthFirebase(email,password)
        }

        binding.registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginGoogle.setOnClickListener {
            // Step 1: Configure Google Sign-In for Firebase
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)

            // Step 2: Launch Google Sign-In intent
            //added a force chooser by making sure it signs the client out
            googleSignInClient.signOut().addOnCompleteListener {
                startActivityForResult(googleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
            }
        }

        // Observe LiveData for successful registration
        loginViewModel.successMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        // Observe LiveData for fail registration
        loginViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }
        private fun handleConnectivity(isConnected: Boolean) {
            if (!isConnected) {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Connectivity", "Online")
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken

                if (idToken != null) {
                    loginViewModel.signInWithGoogle(idToken)
                } else {
                    loginViewModel.errorMessage.postValue("Google Sign-In failed")
                }
            } catch (e: ApiException) {
                loginViewModel.errorMessage.postValue("Google Sign-In failed: ${e.statusCode}")
            }
        }
    }
}

//reference list:

// Lackner, P. 2024. How to Observe the REAL Internet Connectivity - Android Studio Kotlin Tutorial. [video online] Available at: https://www.youtube.com/watch?v=wvDPG2iQ-OE [Accessed 17 november 2025].