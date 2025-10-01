package vcmsa.projects.petcareapp.UI.AddPets

import android.nfc.FormatException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vcmsa.projects.petcareapp.Data.Models.PetInfo
import vcmsa.projects.petcareapp.Data.Repositories.PetRepository

class AddPetsViewModel : ViewModel()
{


    //string msg to update weather it was successful or not
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()

    //var that will be parsed back to PetRepository
    private lateinit var petInfo: PetInfo

    private var _PetRepo = PetRepository()
    fun addPet(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String)
    {
        //making sure fields were entered without being null/blank
        if(!ValidInputs(petName,petType,petAge,petColour,petHeight,petWeight))
        {
            //exiting method - errMsg is updated inside the ValidInputs method
            return
        }

        //setting the petInfo var to object of PetInfo data class
        if(!setPetInfo(petName,petType,petAge,petColour,petHeight,petWeight))
        {
            //exiting method - errMsg is updated inside the setPetInfo method
            return
        }

        //adding the pet to the repository

        viewModelScope.launch {
            _PetRepo.addPet(petInfo).onSuccess {
                successMsg.value = "Added pet successfully"
            }.onFailure {
                errorMsg.value = "An unexpected error occurred"
                return@launch
            }
        }

    }

    fun ValidInputs(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String) : Boolean
    {
        //checking all inputs to make sure they arent null/blank
        if(petName.isNullOrBlank() || petType.isNullOrBlank() || petAge.isNullOrBlank() || petColour.isNullOrBlank() || petHeight.isNullOrBlank()|| petWeight.isNullOrBlank())
        {
            errorMsg.value = "Please fill in all fields"
            return false
        }
        return true
    }

    fun setPetInfo(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String) : Boolean
    {
        try {
            //parsing values
            val age = petAge.toInt()
            val height = petHeight.toDouble()
            val weight = petWeight.toDouble()


            //updating the petInfo var to object of PetInfo
            petInfo = PetInfo(petName,petType,age,petColour,height,weight)
            return true
        }
        catch(e: Exception){
            //returning error msg if there is one
            errorMsg.value = "Unexpected error occured"
            return false
        }
    }

}