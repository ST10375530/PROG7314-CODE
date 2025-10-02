package vcmsa.projects.petcareapp.UI.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks.await
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Models.PetInfo
import vcmsa.projects.petcareapp.Data.Models.User
import vcmsa.projects.petcareapp.Data.Repositories.AuthRepository
import vcmsa.projects.petcareapp.Data.Repositories.PetRepository

class HomeViewModel : ViewModel()
{
    private val petRepository = PetRepository()
    private val authRepo = AuthRepository()

    private val _pets = MutableLiveData<List<PetInfo>>()
    val pets: LiveData<List<PetInfo>> get() = _pets
    val errorMsg = MutableLiveData<String>()

    val userErrorMsg = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadPets()
    }

    fun loadPets() {
        _isLoading.value = true
        viewModelScope.launch {
            petRepository.getUserPets()
                .onSuccess { petsList ->
                    _pets.value = petsList
                    _isLoading.value = false
                }
                .onFailure { exception ->
                    errorMsg.value =
                        "An Unexpected error occurred - loading pets: ${exception.message}"
                    _isLoading.value = false
                }
        }
    }

    fun loadUser() : User?
    {
        val user = authRepo.getCurrentUser()
        if(user == null)
        {
            userErrorMsg.value = "failed to load current user!!"
            //returning an emptty user to not crash program
            return User("","","")
        }
        else {
            return user
        }
    }

    fun refreshPets() {
        loadPets()
    }
}
