package vcmsa.projects.petcareapp.UI.Vets

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.Data.Models.PlaceType
import vcmsa.projects.petcareapp.R
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

        setupClickListeners()
        setupCardClickListeners()
    }

    private fun setupClickListeners() {
        // Back button to HomeActivity
        binding.vetBack.setOnClickListener {
            finish()
        }

        //Navigate to ParksActivity from "Parks" tab
        binding.storeParks.setOnClickListener {
            val intent = Intent(this, ParksActivity::class.java)
            startActivity(intent)
        }

        //Navigate to PetStoresActivity from "Pet Stores" tab
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

    private fun setupCardClickListeners() {
        // First vet card - Dr. Nambuvan
        binding.root.findViewById<androidx.cardview.widget.CardView>(
            R.id.imgDoctor
        )?.parent?.let { cardView ->
            (cardView.parent as? androidx.cardview.widget.CardView)?.setOnClickListener {
                openVetDetail(createDrNambuvanInfo())
            }
        }

        val scrollView = binding.vetsScroll
        val linearLayout = scrollView.getChildAt(0) as? android.widget.LinearLayout
        val firstCard = linearLayout?.getChildAt(3) as? androidx.cardview.widget.CardView
        val secondCard = linearLayout?.getChildAt(5) as? androidx.cardview.widget.CardView

        // First vet card click
        firstCard?.setOnClickListener {
            openVetDetail(createDrNambuvanInfo())
        }

    }

    //Opens the vet detail activity with place information
    private fun openVetDetail(placeInfo: PlaceInfo) {
        val intent = Intent(this, VetDetailActivity::class.java)
        intent.putExtra("PLACE_INFO", placeInfo)
        startActivity(intent)
    }

    // PlaceInfo for Dr (card view
    private fun createDrNambuvanInfo(): PlaceInfo {
        return PlaceInfo(
            id = "vet_1",
            name = "Dr. Nambuvan",
            type = PlaceType.VET,
            qualification = "Bachelor of veterinary science",
            rating = 5.0f,
            reviewCount = 100,
            experience = "10 years of experience",
            distance = "2.5 km",
            price = "R500",
            status = "OPEN",
            timings = "Monday - Friday at 8.00 am - 5.00 pm",
            latitude = -26.1951, // Example coordinates near Johannesburg
            longitude = 28.0473,
            imageResId = R.drawable.doctor1,
            description = "Dr. Nambuvan is one of the most skilled and experienced veterinarians " +
                    "in the area. With over 10 years of experience treating pets of all kinds, " +
                    "Dr. Nambuvan provides compassionate and expert care for your beloved companions."
        )
    }
}