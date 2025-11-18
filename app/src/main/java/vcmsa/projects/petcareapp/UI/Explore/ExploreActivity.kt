package vcmsa.projects.petcareapp.UI.Explore

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.Data.Models.PlaceInfo
import vcmsa.projects.petcareapp.Data.Models.PlaceType
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.HealthRecord.HealthRecords
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityExploreBinding

class ExploreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExploreBinding
    private var placeInfo: PlaceInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        placeInfo = intent.getParcelableExtra("PLACE_INFO")

        placeInfo?.let { place ->
            showPlaceDetails(place)
        } ?: run {
            binding.placeDetailsCard.visibility = View.GONE
        }

        setupClickListeners()
    }

    private fun showPlaceDetails(place: PlaceInfo) {
        binding.placeDetailsCard.visibility = View.VISIBLE
        binding.detailImage.setImageResource(place.imageResId)
        binding.detailName.text = place.name
        binding.detailRating.text = place.rating.toString()
        binding.detailDistance.text = place.distance
        binding.detailStatus.text = place.status

        binding.detailStatus.setTextColor(
            if (place.status == "OPEN") {
                ContextCompat.getColor(this, R.color.light_blue_main)
            } else {
                ContextCompat.getColor(this, R.color.red_food)
            }
        )
    }

    private fun setupClickListeners() {
        binding.backExplore.setOnClickListener {
            finish()
        }

        binding.searchButton.setOnClickListener {
            showSearchDialog()
        }

        binding.directionsButton.setOnClickListener {
            placeInfo?.let { place ->
                openGoogleMapsDirections(place)
            } ?: run {
                Toast.makeText(this, "No location selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.homeExplore.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.discoverExplore.setOnClickListener {
            val intent = Intent(this, VetsActivity::class.java)
            startActivity(intent)
        }

        binding.healthExplore.setOnClickListener {
            val intent = Intent(this, HealthRecords::class.java)
            startActivity(intent)
        }

        binding.profileExplore.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openGoogleMapsDirections(place: PlaceInfo) {
        val uri = Uri.parse("google.navigation:q=${place.latitude},${place.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            val browserUri = Uri.parse(
                "https://www.google.com/maps/dir/?api=1&destination=${place.latitude},${place.longitude}"
            )
            val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
            startActivity(browserIntent)
        }
    }

    private fun openInGoogleMaps(place: PlaceInfo) {
        val uri = Uri.parse("geo:${place.latitude},${place.longitude}?q=${place.latitude},${place.longitude}(${place.name})")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            val browserUri = Uri.parse(
                "https://www.google.com/maps/search/?api=1&query=${place.latitude},${place.longitude}"
            )
            startActivity(Intent(Intent.ACTION_VIEW, browserUri))
        }
    }

    private fun showSearchDialog() {
        val options = arrayOf(
            "Nearby Vets",
            "Nearby Parks",
            "Nearby Pet Stores",
            "Open in Google Maps"
        )

        AlertDialog.Builder(this)
            .setTitle("Search for...")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> searchNearbyPlaces(PlaceType.VET)
                    1 -> searchNearbyPlaces(PlaceType.PARK)
                    2 -> searchNearbyPlaces(PlaceType.PET_STORE)
                    3 -> openCurrentLocationInMaps()
                }
            }
            .show()
    }

    private fun searchNearbyPlaces(type: PlaceType) {
        val typeName = when (type) {
            PlaceType.VET -> "veterinarians"
            PlaceType.PARK -> "dog parks"
            PlaceType.PET_STORE -> "pet stores"
        }

        val query = "nearby $typeName"
        val uri = Uri.parse("geo:0,0?q=$query")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "Please install Google Maps to search for nearby places",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openCurrentLocationInMaps() {
        placeInfo?.let { place ->
            openInGoogleMaps(place)
        } ?: run {
            val intent = packageManager.getLaunchIntentForPackage("com.google.android.apps.maps")
            if (intent != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}