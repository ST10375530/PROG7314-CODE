package vcmsa.projects.petcareapp.UI.Parks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Explore.ExploreActivity
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.databinding.ActivityParkDetailBinding
import kotlin.jvm.java
import kotlin.run

class ParkDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkDetailBinding
    private lateinit var placeInfo: PlaceInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get place info
        placeInfo = intent.getParcelableExtra("PLACE_INFO") ?: run {
            finish()
            return
        }

        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        // Set park image
        binding.parkImage.setImageResource(placeInfo.imageResId)

        // Set park details
        binding.parkName.text = placeInfo.name
        binding.parkRating.text = "${placeInfo.rating} (${placeInfo.reviewCount} reviews)"
        binding.parkDistance.text = placeInfo.distance
        binding.parkStatus.text = placeInfo.status
        binding.parkTimings.text = placeInfo.timings
        binding.parkDescription.text = placeInfo.description

        // Set status color
        binding.parkStatus.setTextColor(
            if (placeInfo.status == "OPEN") {
                ContextCompat.getColor(this, R.color.light_blue_main)
            } else {
                ContextCompat.getColor(this, R.color.red_food)
            }
        )
    }

    private fun setupClickListeners() {
        // Back button
        binding.backButton.setOnClickListener {
            finish()
        }

        //Locate on Maps button
        binding.locationButton.setOnClickListener {
            openMapLocation()
        }

        // Bottom navigation
        binding.parkHomeNav.setOnClickListener {
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