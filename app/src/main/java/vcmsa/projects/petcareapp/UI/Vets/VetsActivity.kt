package vcmsa.projects.petcareapp.UI.Vets

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Parks.ParksActivity
import vcmsa.projects.petcareapp.UI.PetStores.PetStoresActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.databinding.ActivityVetsBinding

class VetsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button to HomeActivity
        binding.vetBack.setOnClickListener {
            finish()
        }

        // Navigate to ParksActivity from "Parks" tab
        binding.storeParks.setOnClickListener {
            val intent = Intent(this, ParksActivity::class.java)
            startActivity(intent)
        }

        // Navigate to PetStoresActivity from "Pet Stores" tab
        binding.storePets.setOnClickListener {
            val intent = Intent(this, PetStoresActivity::class.java)
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