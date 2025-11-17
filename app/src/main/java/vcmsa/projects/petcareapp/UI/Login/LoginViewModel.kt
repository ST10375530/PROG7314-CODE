package vcmsa.projects.petcareapp.UI.Login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Network.ConnectivityObserver
import vcmsa.projects.petcareapp.Data.Repositories.AuthRepository

class LoginViewModel(private val connectivityObserver: ConnectivityObserver) : ViewModel()
{
    val isConnected: StateFlow<Boolean?> =
        connectivityObserver.isConnected
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                null
            )
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    private val _authRepo = AuthRepository()

    fun signInWIthFirebase(email: String, password: String)
    {
        viewModelScope.launch {
            try{
                val result = _authRepo.signInUserFirebase(email,password)
                if(result.isSuccess)
                {
                    successMessage.postValue("sign-in successful with firebase!")
                }
                else{
                    errorMessage.postValue("Incorrect username or password!")
                }
            }
            catch(e: Exception)
            {
                Log.d("FirebaseLoginErr:", e.message.toString())
                errorMessage.postValue("Unexpected error occured")
            }
        }
    }

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
                Log.d("GoogleLoginErr:", e.message.toString())
                errorMessage.postValue("Unexpected error during Google sign-in")
            }
        }
    }
}

//Creating the viewmodel factory to work with activity pages (Lackner, 2024):
class LoginViewModelFactory(
    private val connectivityObserver: ConnectivityObserver
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(connectivityObserver) as T
    }
}

//reference list:

// Lackner, P. 2024. How to Observe the REAL Internet Connectivity - Android Studio Kotlin Tutorial. [video online] Available at: https://www.youtube.com/watch?v=wvDPG2iQ-OE [Accessed 17 november 2025].