package vcmsa.projects.petcareapp.UI.Vets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.UI.Explore.ExploreActivity
import vcmsa.projects.petcareapp.databinding.ActivityVetDrBinding
import kotlin.jvm.java
import kotlin.let
import kotlin.run

class VetDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVetDrBinding
    private lateinit var placeInfo: PlaceInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVetDrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the place info passed from VetsActivity
        placeInfo = intent.getParcelableExtra("PLACE_INFO") ?: run {
            // If no data passed, finish activity
            finish()
            return
        }

        setupViews()
        setupClickListeners()
    }

    // Populate the views with data from placeInfo
    private fun setupViews() {
        // Set doctor name in header
        binding.root.findViewById<TextView>(
            binding.root.context.resources.getIdentifier(
                "dr_header", "id", packageName
            )
        )?.let { header ->
            header.findViewById<TextView>(
                View.generateViewId()
            )?.text = placeInfo.name
        }

        // Set portrait image
        binding.drPortraitSection.findViewById<ImageView>(
            View.generateViewId()
        )?.setImageResource(placeInfo.imageResId)

        // Set info card details
        binding.drInfoCard.findViewById<TextView>(
            View.generateViewId()
        )?.text = placeInfo.name

        binding.txtQualification.text = placeInfo.qualification
        binding.txtRatingValue.text = "${placeInfo.rating} {${placeInfo.reviewCount} reviews}"
        binding.txtExperience.text = placeInfo.experience
        binding.txtDistanceDr.text = placeInfo.distance
        binding.txtTimingsDr.text = placeInfo.timings

        // Set description
        binding.doctorDescription.text = placeInfo.description

        val showFullStars = placeInfo.rating >= 4.5f
    }

    private fun setupClickListeners() {
        // Back button
        binding.drBackButton.setOnClickListener {
            finish()
        }

        //Locate on Maps button
        binding.locationButton.setOnClickListener {
            openMapLocation()
        }

        // Bottom navigation
        binding.home.setOnClickListener {
            finish()
        }

        binding.pDiscoverNavItem.setOnClickListener {
            // Already on discover section
        }
    }

    private fun openMapLocation() {
        val intent = Intent(this, ExploreActivity::class.java)
        intent.putExtra("PLACE_INFO", placeInfo)
        startActivity(intent)
    }
}