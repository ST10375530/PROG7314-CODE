package vcmsa.projects.petcareapp.UI.Parks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.Data.Models.PlaceType
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.PetStores.PetStoresActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.UI.Explore.ExploreActivity
import vcmsa.projects.petcareapp.databinding.ActivityParksBinding

class ParksActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupCardClickListeners()
    }

    private fun setupClickListeners() {
        // Back button to VetsActivity
        binding.parksBack.setOnClickListener {
            finish()
        }

        //Navigate to VetsActivity from "Veterinary" tab
        binding.vetIcon.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

        //Navigate to PetStoresActivity from "Pet Stores" tab
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

    //Click listeners for park cards
    private fun setupCardClickListeners() {
        val scrollView = binding.parksScroll
        val linearLayout = scrollView.getChildAt(0) as? android.widget.LinearLayout
        val firstCard = linearLayout?.getChildAt(3) as? androidx.cardview.widget.CardView
        val secondCard = linearLayout?.getChildAt(5) as? androidx.cardview.widget.CardView

        // First park card click
        firstCard?.setOnClickListener {
            openParkDetail(createFirstParkInfo())
        }

        // Second park card click
        secondCard?.setOnClickListener {
            openParkDetail(createSecondParkInfo())
        }
    }

    private fun openParkDetail(placeInfo: PlaceInfo) {
        val intent = Intent(this, ParkDetailActivity::class.java)
        intent.putExtra("PLACE_INFO", placeInfo)
        startActivity(intent)
    }

    private fun createFirstParkInfo(): PlaceInfo {
        return PlaceInfo(
            id = "park_1",
            name = "Comb and Collar Park",
            type = PlaceType.PARK,
            rating = 5.0f,
            reviewCount = 100,
            distance = "2.5 km",
            price = "R100",
            status = "OPEN",
            timings = "Monday - Friday at 8.00 am - 5.00 pm",
            latitude = -26.1841,
            longitude = 28.0373,
            imageResId = R.drawable.park1,
            description = "A beautiful park perfect for walking your pets and enjoying the outdoors."
        )
    }

    private fun createSecondParkInfo(): PlaceInfo {
        return PlaceInfo(
            id = "park_2",
            name = "Pet-arronies Salon Park",
            type = PlaceType.PARK,
            rating = 5.0f,
            reviewCount = 100,
            distance = "2.5 km",
            price = "R100",
            status = "OPEN",
            timings = "Monday - Friday at 8.00 am - 5.00 pm",
            latitude = -26.1741,
            longitude = 28.0273,
            imageResId = R.drawable.park1,
            description = "Family-friendly park with dedicated pet areas and walking trails."
        )
    }
}