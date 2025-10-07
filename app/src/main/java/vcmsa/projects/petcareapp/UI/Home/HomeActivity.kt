package vcmsa.projects.petcareapp.UI.Home

import PetsAdapter
import vcmsa.projects.petcareapp.UI.Home.HomeViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.Data.Models.PetInfo
import vcmsa.projects.petcareapp.UI.Profile.ProfileActivity
import vcmsa.projects.petcareapp.UI.AddPets.AddPetsActivity
import vcmsa.projects.petcareapp.UI.HealthRecord.HealthRecords
import vcmsa.projects.petcareapp.UI.Main.MainActivity
import vcmsa.projects.petcareapp.UI.Settings.SettingsActivity
import vcmsa.projects.petcareapp.UI.Vets.VetsActivity
import vcmsa.projects.petcareapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel = HomeViewModel()
    private lateinit var petsAdapter: PetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpCurrentUser()
        setupPetsRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupPetsRecyclerView() {
        // Use binding to access RecyclerView
        val petsRecyclerView: RecyclerView = binding.rvPets

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        petsRecyclerView.layoutManager = layoutManager

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(petsRecyclerView)

        // Initialize adapter with click listener
        petsAdapter = PetsAdapter { pet ->
            onPetClicked(pet)
        }
        petsRecyclerView.adapter = petsAdapter
    }

    private fun setUpCurrentUser()
    {
        val user = viewModel.loadUser()
        binding.greetingText.text = "Welcome ${user!!.fullname}"
    }
    private fun setupObservers() {
        // Observe pets data
        viewModel.pets.observe(this) { pets ->
            petsAdapter.submitList(pets)

            // Handle empty state
            if (pets.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
            }
        }

        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        // Observe error messages for loading pets
        viewModel.errorMsg.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.myPetsSection.setOnClickListener {
            val intent = Intent(this, AddPetsActivity::class.java)
            startActivity(intent)
        }

        binding.profileNavItem.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.hDiscoverNavItem.setOnClickListener {
            Toast.makeText(this,"Coming soon", Toast.LENGTH_LONG).show()
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

    private fun onPetClicked(pet: PetInfo) {
        Toast.makeText(this, "Clicked on ${pet.name}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AddPetsActivity::class.java)
        startActivity(intent)
    }

    private fun showEmptyState() {
        // Implement empty state UI
        // Example: binding.emptyStateView.visibility = View.VISIBLE
        // binding.rvPets.visibility = View.GONE
    }

    private fun hideEmptyState() {
        // Hide empty state UI
        // Example: binding.emptyStateView.visibility = View.GONE
        // binding.rvPets.visibility = View.VISIBLE
    }

    private fun showLoading() {
        // Show loading indicator
        // Example: binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        // Hide loading indicator
        // Example: binding.progressBar.visibility = View.GONE
    }
}