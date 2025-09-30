package vcmsa.projects.petcareapp.UI.PetStores

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Parks.ParksActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityPetStoresBinding

class PetStoresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetStoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button to ParksActivity
        binding.petStoresBack.setOnClickListener {
            finish()
        }

        // Navigate to VetsActivity from "Veterinary" tab
        binding.vetIcon.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

        // Navigate to ParksActivity from "Parks" tab
        binding.storeParks.setOnClickListener {
            val intent = Intent(this, ParksActivity::class.java)
            startActivity(intent)
        }
        // Navigate to VetsActivity
        binding.discover.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }
        // Navigate to HomeActivity
        binding.homeNavItem.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        // Navigate to ProfileActivity
        binding.profileNavItem.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}