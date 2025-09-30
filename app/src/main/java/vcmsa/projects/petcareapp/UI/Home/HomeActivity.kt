package vcmsa.projects.petcareapp.UI.Home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.AddPets.AddPetsActivity
import vcmsa.projects.petcareapp.UI.HealthRecord.HealthRecords
import vcmsa.projects.petcareapp.UI.Settings.SettingsActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Example navigation from Home
        binding.myPetsSection.setOnClickListener {
            val intent = Intent(this, AddPetsActivity::class.java)
            startActivity(intent)
        }
        // Assuming the profile nav item is clickable
        binding.profileNavItem.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // Navigate to VetsActivity on "discover" icon click
        binding.hDiscoverNavItem.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }
        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.healthRecordsNavItem.setOnClickListener {
            val intent = Intent(this, HealthRecords::class.java)
            startActivity(intent)
        }
    }
}