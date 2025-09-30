package vcmsa.projects.petcareapp.UI.Parks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.PetStores.PetStoresActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityParksBinding

class ParksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button to VetsActivity
        binding.parksBack.setOnClickListener {
            finish()
        }

        // Navigate to VetsActivity from "Veterinary" tab
        binding.vetIcon.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

        // Navigate to PetStoresActivity from "Pet Stores" tab
        binding.storePets.setOnClickListener {
            val intent = Intent(this, PetStoresActivity::class.java)
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