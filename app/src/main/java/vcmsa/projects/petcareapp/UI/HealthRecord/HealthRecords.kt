package vcmsa.projects.petcareapp.UI.HealthRecord

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.ApiMedical.MedicationListActivity


class HealthRecords : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_health_records)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val medicalRecordsTab = findViewById<TextView>(R.id.tab_medical)
        medicalRecordsTab.setOnClickListener {
            // Navigate to MedicalRecordsActivity
            val intent = Intent(this, MedicalRecordsActivity::class.java)
            startActivity(intent)
        }


        setupClickListeners()
    }

    private fun setupClickListeners() {
        // Medical Records Tab
        findViewById<TextView>(R.id.tab_medical).setOnClickListener {
            val intent = Intent(this, MedicalRecordsActivity::class.java)
            startActivity(intent)
        }

        // Back Button
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // See All click listeners for cards
        findViewById<TextView>(R.id.seeAllVaccinations).setOnClickListener {
            val intent = Intent(this, AllVaccinationsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.seeAllAllergies).setOnClickListener {
            val intent = Intent(this, AllAllergiesActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.seeAllTreatments).setOnClickListener {
            val intent = Intent(this, AllTreatmentsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.seeAllMedications).setOnClickListener {
            val intent = Intent(this, AllMedicationsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.seeAllMedicationIdentifier).setOnClickListener {
            val intent = Intent(this, MedicationListActivity::class.java)
            startActivity(intent)
        }

        // Make entire cards clickable as well
        findViewById<MaterialCardView>(R.id.past_vaccinations_tab).setOnClickListener {
            val intent = Intent(this, AllVaccinationsActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.allergies_tab).setOnClickListener {
            val intent = Intent(this, AllAllergiesActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.treatments_tab).setOnClickListener {
            val intent = Intent(this, AllTreatmentsActivity::class.java)
            startActivity(intent)
        }

        findViewById<MaterialCardView>(R.id.medications_tab).setOnClickListener {
            val intent = Intent(this, AllMedicationsActivity::class.java)
            startActivity(intent)
        }
    }
}
