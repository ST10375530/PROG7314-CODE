package vcmsa.projects.petcareapp.UI.Register

import android.R
import android.app.Activity
import android.util.Log
import vcmsa.projects.petcareapp.Data.Repositories.AuthRepository
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel()
{

    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val errorForPasswordNotmatching = MutableLiveData<String>()
    val _loading = MutableLiveData<Int>()


    val _authRepo = AuthRepository()

    fun SignUp(fullname: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                // Input validation
                if (!NotBlankOrNull(fullname, email, password, confirmPassword)) {
                    errorMessage.value = "All fields are required"
                    return@launch
                }
                if (!validEmail(email)) {
                    errorMessage.value = "Please enter a valid email"
                    return@launch
                }
                if (!validatePassword(password)) {
                    errorMessage.value =
                        "Password must contain at least one special character, one number, mixed case, and be 8+ chars"
                    return@launch
                }
                if(password != confirmPassword)
                {
                    errorMessage.value = "Passwords no match."
                    return@launch
                }
                // Repository call
                    val result = _authRepo.registerUser(fullname, email, password)
                    if (result.isSuccess) {
                        successMessage.value = "Successfully registered :D"
                    } else {
                        errorMessage.value = result.exceptionOrNull()?.message ?: "Registration failed"
                }


            } catch (e: Exception) {
                errorMessage.value = "Unexpected error occurred!"
            }
        }

    }

    //google SSO:
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                //waiting for response from AuthRepository
                val result = _authRepo.signInWithGoogle(idToken)
                if (result.isSuccess) {
                    successMessage.postValue("Google sign-in successful!")
                } else {
                    errorMessage.postValue(
                        result.exceptionOrNull()?.message ?: "Google sign-in failed"
                    )
                }
            } catch (e: Exception) {
                errorMessage.postValue("Unexpected error during Google sign-in")
            }
        }
    }

    //valid password checker
    fun validatePassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }
        //complex regex to check for  1+ Capital letter/s, 1+ lower letter/s, 1+ digits, 1+ special chars
        val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>])")

        //checking input for at least 1 special char
        if (!passwordPattern.containsMatchIn(password)) {
            return false
        }
        return true
    }
    //valid email checker
    fun validEmail(email: String) : Boolean
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return false
        }
        return true
    }
    //makes sure no inputs are blank
    fun NotBlankOrNull(fullname: String, email: String, password: String, confirmPassword: String) : Boolean {
        if (fullname.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || confirmPassword.isNullOrBlank()) {
            return false
        }
        else{
            return true
        }
    }
}