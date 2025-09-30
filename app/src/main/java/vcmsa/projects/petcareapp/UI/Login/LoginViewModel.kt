package vcmsa.projects.petcareapp.UI.Login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Repositories.AuthRepository

class LoginViewModel : ViewModel()
{
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    private val _authRepo = AuthRepository()

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
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

}