package vcmsa.projects.petcareapp.UI.AddPets

import android.nfc.FormatException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vcmsa.projects.petcareapp.Data.Models.PetInfo

class AddPetsViewModel : ViewModel()
{


    //string msg to update weather it was successful or not
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()

    //var that will be parsed back to PetRepository
    private lateinit var petInfo: PetInfo
    fun addPet(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String)
    {
        //making sure fields were entered without being null/blank
        ValidInputs(petName,petType,petAge,petColour,petHeight,petWeight)

        //setting the petInfo var to object of PetInfo data class
        setPetInfo(petName,petType,petAge,petColour,petHeight,petWeight)

    }

    fun ValidInputs(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String)
    {
        //checking all inputs to make sure they arent null/blank
        if(petName.isNullOrBlank() || petType.isNullOrBlank() || petAge.isNullOrBlank() || petColour.isNullOrBlank() || petHeight.isNullOrBlank()|| petWeight.isNullOrBlank())
        {
            errorMsg.value = "Please fill in all fields"
        }
    }

    fun setPetInfo(petName: String, petType:String, petAge:String, petColour:String, petHeight:String, petWeight:String)
    {
        try {
            //parsing values
            val age = petAge.toInt()
            val height = petHeight.toDouble()
            val weight = petWeight.toDouble()

            //updating the petInfo var to object of PetInfo
            petInfo = PetInfo(petName,petType,age,petColour,height,weight)
        }
        catch(e: Exception){
            //returning error msg if there is one
            errorMsg.value = "Unexpected error occured"
        }
    }

}