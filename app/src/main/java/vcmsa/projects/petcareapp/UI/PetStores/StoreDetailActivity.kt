package vcmsa.projects.petcareapp.UI.PetStores

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Explore.ExploreActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.databinding.ActivityStoreDetailBinding
import kotlin.jvm.java
import kotlin.run

class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreDetailBinding
    private lateinit var placeInfo: PlaceInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        placeInfo = intent.getParcelableExtra("PLACE_INFO") ?: run {
            finish()
            return
        }

        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        binding.storeImage.setImageResource(placeInfo.imageResId)
        binding.storeName.text = placeInfo.name
        binding.storeRating.text = "${placeInfo.rating} (${placeInfo.reviewCount} reviews)"
        binding.storeDistance.text = placeInfo.distance
        binding.storeStatus.text = placeInfo.status
        binding.storeTimings.text = placeInfo.timings
        binding.storeDescription.text = placeInfo.description

        binding.storeStatus.setTextColor(
            if (placeInfo.status == "OPEN") {
                ContextCompat.getColor(this, R.color.light_blue_main)
            } else {
                ContextCompat.getColor(this, R.color.red_food)
            }
        )
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        //Locate on Maps button
        binding.locationButton.setOnClickListener {
            openMapLocation()
        }

        binding.storeHomeNav.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun openMapLocation() {
        val intent = Intent(this, ExploreActivity::class.java)
        intent.putExtra("PLACE_INFO", placeInfo)
        startActivity(intent)
    }
}