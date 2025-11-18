package vcmsa.projects.petcareapp.UI.PetStores

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.Data.Models.PlaceType
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Parks.ParksActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.UI.Explore.ExploreActivity
import vcmsa.projects.petcareapp.databinding.ActivityPetStoresBinding

class PetStoresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPetStoresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetStoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupCardClickListeners()
    }

    private fun setupClickListeners() {
        // Back button to ParksActivity
        binding.petStoresBack.setOnClickListener {
            finish()
        }

        //Navigate to VetsActivity from "Veterinary" tab
        binding.vetIcon.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

        //Navigate to ParksActivity from "Parks" tab
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

    // Click listeners for pet store cards
    private fun setupCardClickListeners() {
        val scrollView = binding.petStoresScroll
        val linearLayout = scrollView.getChildAt(0) as? android.widget.LinearLayout
        val firstCard = linearLayout?.getChildAt(3) as? androidx.cardview.widget.CardView
        val secondCard = linearLayout?.getChildAt(5) as? androidx.cardview.widget.CardView

        // First store card click
        firstCard?.setOnClickListener {
            openStoreDetail(createFirstStoreInfo())
        }

        // Second store card click
        secondCard?.setOnClickListener {
            openStoreDetail(createSecondStoreInfo())
        }
    }

    private fun openStoreDetail(placeInfo: PlaceInfo) {
        val intent = Intent(this, StoreDetailActivity::class.java)
        intent.putExtra("PLACE_INFO", placeInfo)
        startActivity(intent)
    }

    private fun createFirstStoreInfo(): PlaceInfo {
        return PlaceInfo(
            id = "store_1",
            name = "Tails of the City",
            type = PlaceType.PET_STORE,
            rating = 5.0f,
            reviewCount = 100,
            distance = "2.5 km",
            price = "R100",
            status = "OPEN",
            timings = "Monday - Friday at 8.00 am - 5.00 pm",
            latitude = -26.2141,
            longitude = 28.0673,
            imageResId = R.drawable.park1, // Replace with actual store image
            description = "Your one-stop shop for all pet supplies, food, and accessories."
        )
    }

    private fun createSecondStoreInfo(): PlaceInfo {
        return PlaceInfo(
            id = "store_2",
            name = "Silver Paw Lounge",
            type = PlaceType.PET_STORE,
            rating = 5.0f,
            reviewCount = 100,
            distance = "2.5 km",
            price = "R100",
            status = "OPEN",
            timings = "Monday - Friday at 8.00 am - 5.00 pm",
            latitude = -26.2241,
            longitude = 28.0773,
            imageResId = R.drawable.park1, // Replace with actual store image
            description = "Premium pet store offering grooming services and luxury pet products."
        )
    }
}