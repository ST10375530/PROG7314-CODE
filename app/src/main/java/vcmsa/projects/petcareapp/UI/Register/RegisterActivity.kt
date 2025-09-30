package vcmsa.projects.petcareapp.UI.Register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Login.LoginActivity
import vcmsa.projects.petcareapp.databinding.ActivtyRegisterBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.AddPets.AddPetsViewModel

class RegisterActivity : AppCompatActivity() {


    companion object {
        private const val RC_GOOGLE_SIGN_IN = 9001
    }

    private lateinit var binding: ActivtyRegisterBinding
    private val registerViewModel = RegisterViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivtyRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerButton.setOnClickListener {
            val firstName = binding.registerFname.text.toString().trim()
            val lastName = binding.registerLname.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString()
            val confirmPassword = binding.registerCpassword.text.toString()

            val fullName = "$firstName $lastName"

            // Trigger signup
            registerViewModel.SignUp(fullName, email, password, confirmPassword)
        }

        //google SSO btn
        binding.registerGoogle.setOnClickListener {
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
        registerViewModel.successMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        // Observe LiveData for fail registration
        registerViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }



        binding.loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() //finish register activity
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RegisterActivity.Companion.RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val idToken = account.idToken

                if (idToken != null) {
                    registerViewModel.signInWithGoogle(idToken)
                } else {
                    registerViewModel.errorMessage.postValue("Google Sign-In failed")
                }
            } catch (e: ApiException) {
                registerViewModel.errorMessage.postValue("Google Sign-In failed: ${e.statusCode}")
            }
        }
    }
}