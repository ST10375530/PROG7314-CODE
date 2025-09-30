package vcmsa.projects.petcareapp.UI.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.AddPets.AddPetsActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.PetProfiles.PetProfileActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish() // Go back to previous activity (Home)
        }

        binding.btnDogImage.setOnClickListener {
            val intent = Intent(this, PetProfileActivity::class.java)
            startActivity(intent)
        }

        binding.addPetOption.setOnClickListener {
            val intent = Intent(this, AddPetsActivity::class.java)
            startActivity(intent)
        }

        // Assuming the home nav item is clickable
        binding.homeNavItemProfile.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        // Navigate to VetsActivity on discover icon click
        binding.pDiscoverNavItem.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

    }
}