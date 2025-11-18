package vcmsa.projects.petcareapp.UI.AddPets

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.Data.Models.PetInfo
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.databinding.ActivityAddPetsBinding
import vcmsa.projects.petcareapp.Data.Services.NotificationHelper

class AddPetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPetsBinding

    private val addPetViewModel = AddPetsViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityAddPetsBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.addPetBackButton.setOnClickListener {
                val intent: Intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

        binding.savePetButton.setOnClickListener {
            val petName = binding.addPetName.text.toString().trim()
            val petType = binding.addPetType.text.toString().trim()
            val petAge = binding.addPetAge.text.toString().trim()
            val petColour = binding.addPetColour.text.toString().trim()
            val petHeight = binding.addPetHeight.text.toString().trim()
            val petWeight = binding.addPetWeight.text.toString().trim()


            addPetViewModel.addPet(petName, petType, petAge, petColour, petHeight, petWeight)
        }

// message for successfully adding the pet
        addPetViewModel.successMsg.observe(this) { message ->
            if (!message.isNullOrEmpty()) {

                Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                // Send notification now — use pet name from the form
                val petName = binding.addPetName.text.toString().trim()
                val nid = petName.hashCode()
//                NotificationHelper.notifyNow(
//                    this,
//                    id = nid,
//                    title = "New Pet Added!",
//                    body = "$petName has been added to your PetCare profile.",
//                    useRemindersChannel = false,
//                    petName = petName
//                )

                // Navigate to Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        //message for successfully adding the pet
            addPetViewModel.successMsg.observe(this) { message ->
                if (!message.isNullOrEmpty()) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            // Observe LiveData for failing to add the pet
            addPetViewModel.errorMsg.observe(this) { message ->
                if (!message.isNullOrEmpty()) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }

    }
}