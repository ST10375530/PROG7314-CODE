package vcmsa.projects.petcareapp.UI.ApiMedical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Models.API.Medication
import vcmsa.projects.petcareapp.Data.Repositories.MedicationRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class MedicationViewModel : ViewModel() {

    private val repository = MedicationRepository()

    private val _medications = MutableLiveData<List<Medication>>()
    val medications: LiveData<List<Medication>> = _medications

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadAllMedications()
    }

    fun loadAllMedications() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = repository.getAllMedications()
            _isLoading.value = false

            if (result.isSuccess) {
                _medications.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to load medications"
            }
        }
    }

    fun searchMedications(query: String) {
        if (query.isEmpty()) {
            loadAllMedications()
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            val result = repository.searchMedications(query)
            _isLoading.value = false

            if (result.isSuccess) {
                _medications.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Search failed"
            }
        }
    }
}