package vcmsa.projects.petcareapp.UI.Wellness

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.R

class WellnessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wellness)

        // Back button closes this screen
        val ivBack: ImageView = findViewById(R.id.iv_back)
        ivBack.setOnClickListener { finish() }

        // Switch to Medical Records tab
        val tabMedical: TextView = findViewById(R.id.tab_medical)
        tabMedical.setOnClickListener {
            // startActivity(Intent(this, MedicalRecordsActivity::class.java))
        }

        // See all Vaccinations (placeholder)
        val seeAllVax: TextView = findViewById(R.id.tv_vax_see_all)
        seeAllVax.setOnClickListener {
            // startActivity(Intent(this, VaccinationListActivity::class.java))
        }

        // See all Allergies (placeholder)
        val seeAllAllergies: TextView = findViewById(R.id.tv_allergy_see_all)
        seeAllAllergies.setOnClickListener {
            // startActivity(Intent(this, AllergyListActivity::class.java))
        }

        // Appointment button
        val btnStart: Button = findViewById(R.id.btn_start_appointment)
        btnStart.setOnClickListener {
            //  startActivity(Intent(this, AppointmentBookingActivity::class.java))
        }
    }
}