package vcmsa.projects.petcareapp.UI.HealthRecord

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import vcmsa.projects.petcareapp.R
import java.util.*

class AddMedicalInfoActivity : AppCompatActivity() {
        //late init var for firestore (Firebase, 2025):
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_medical_info)

        // Initialize Firestore (Firebase, 2025):
        db = FirebaseFirestore.getInstance()

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


        if (allergies.isEmpty() || treatment.isEmpty() || vaccination.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }


        val petId = intent.getStringExtra("PET_ID") ?: generatePetId()


        val medicalInfo = mapOf(
            "petId" to petId,
            "allergies" to allergies,
            "allergyDate" to allergyDate,
            "treatment" to treatment,
            "treatmentDate" to treatmentDate,
            "vaccination" to vaccination,
            "vaccinationDate" to vaccinationDate,
            "medication" to medication,
            "emergencyContact" to emergencyContact,
            "createdAt" to System.currentTimeMillis(),
            "updatedAt" to System.currentTimeMillis()
        )

        // Save to Firestore in healthrecord (Firebase, 2025):
        saveToFirestore(medicalInfo)
    }

    private fun saveToFirestore(medicalInfo: Map<String, Any>) {
        //adding to the collection (Firebase, 2025):
        db.collection("healthrecord")
            .add(medicalInfo)
            .addOnSuccessListener { documentReference ->

                Toast.makeText(this, "Medical info saved successfully!", Toast.LENGTH_SHORT).show()

                // Create intent to pass data back
                val resultIntent = Intent().apply {
                    putExtra("DOCUMENT_ID", documentReference.id)
                    putExtra("PET_ID", medicalInfo["petId"].toString())
                    putExtra("ALLERGIES", medicalInfo["allergies"].toString())
                    putExtra("ALLERGY_DATE", medicalInfo["allergyDate"].toString())
                    putExtra("TREATMENT", medicalInfo["treatment"].toString())
                    putExtra("TREATMENT_DATE", medicalInfo["treatmentDate"].toString())
                    putExtra("VACCINATION", medicalInfo["vaccination"].toString())
                    putExtra("VACCINATION_DATE", medicalInfo["vaccinationDate"].toString())
                    putExtra("MEDICATION", medicalInfo["medication"].toString())
                    putExtra("EMERGENCY_CONTACT", medicalInfo["emergencyContact"].toString())
                }

                setResult(RESULT_OK, resultIntent)
                finish()
            }
            .addOnFailureListener { e ->

                Toast.makeText(this, "Error saving medical info: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun generatePetId(): String {

        return "pet_${System.currentTimeMillis()}"
    }
}

//reference list:

////Firebase. 2025. Get started with Cloud Firestore. [Online]. Available at: https://firebase.google.com/docs/firestore/quickstart [Accessed 27 September 2025].