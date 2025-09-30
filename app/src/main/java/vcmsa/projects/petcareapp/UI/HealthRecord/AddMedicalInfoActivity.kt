package vcmsa.projects.petcareapp.UI.HealthRecord

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import vcmsa.projects.petcareapp.R
import java.util.Calendar

class AddMedicalInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_medical_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupDatePickers()
        setupClickListeners()
    }

    private fun setupDatePickers() {
        // Set up date picker for allergy date
        findViewById<TextInputEditText>(R.id.txtAllergyDate).setOnClickListener {
            showDatePicker(R.id.txtAllergyDate)
        }

        // Set up date picker for treatment date
        findViewById<TextInputEditText>(R.id.txtTreatmentDate).setOnClickListener {
            showDatePicker(R.id.txtTreatmentDate)
        }

        // Set up date picker for vaccination date
        findViewById<TextInputEditText>(R.id.txtVaccinationDate).setOnClickListener {
            showDatePicker(R.id.txtVaccinationDate)
        }
    }

    private fun showDatePicker(fieldId: Int) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                findViewById<TextInputEditText>(fieldId).setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun setupClickListeners() {
        // Back button
        findViewById<ImageView>(R.id.add_pet_back_button).setOnClickListener {
            onBackPressed()
        }

        // Submit button
        findViewById<MaterialButton>(R.id.btnSubmitMed).setOnClickListener {
            submitMedicalInfo()
        }
    }

    private fun submitMedicalInfo() {
        val allergies = findViewById<TextInputEditText>(R.id.txtAllergies).text.toString()
        val allergyDate = findViewById<TextInputEditText>(R.id.txtAllergyDate).text.toString()
        val treatment = findViewById<TextInputEditText>(R.id.txtTreatment).text.toString()
        val treatmentDate = findViewById<TextInputEditText>(R.id.txtTreatmentDate).text.toString()
        val vaccination = findViewById<TextInputEditText>(R.id.txtVaccination).text.toString()
        val vaccinationDate = findViewById<TextInputEditText>(R.id.txtVaccinationDate).text.toString()
        val medication = findViewById<TextInputEditText>(R.id.txtMedication).text.toString()
        val emergencyContact = findViewById<TextInputEditText>(R.id.txtEmergencyContact).text.toString()

        // Create intent to pass data back
        val resultIntent = Intent().apply {
            putExtra("ALLERGIES", allergies)
            putExtra("ALLERGY_DATE", allergyDate)
            putExtra("TREATMENT", treatment)
            putExtra("TREATMENT_DATE", treatmentDate)
            putExtra("VACCINATION", vaccination)
            putExtra("VACCINATION_DATE", vaccinationDate)
            putExtra("MEDICATION", medication)
            putExtra("EMERGENCY_CONTACT", emergencyContact)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}