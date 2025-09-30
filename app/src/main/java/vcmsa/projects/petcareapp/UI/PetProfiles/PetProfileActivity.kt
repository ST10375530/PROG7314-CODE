package vcmsa.projects.petcareapp.UI.PetProfiles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.databinding.ActivityPetProfileBinding

class PetProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.petProfileBackBtn.setOnClickListener {
            finish() // Go back to previous activity (Profile)
        }
    }
}