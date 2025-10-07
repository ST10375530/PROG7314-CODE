package vcmsa.projects.petcareapp.UI.specificMedicine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import vcmsa.projects.petcareapp.Data.Models.API.Medication
import vcmsa.projects.petcareapp.Data.Repositories.MedicationRepository
import vcmsa.projects.petcareapp.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MedicationDetailActivity : AppCompatActivity() {

    private lateinit var medication: Medication
    private val repository = MedicationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_detail)

        // Get medication from intent
        medication = intent.getParcelableExtra("MEDICATION") ?: run {
            Toast.makeText(this, "Error loading medication details", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        initViews()
        displayMedicationDetails()
        setupDosageCalculator()
    }

    private fun initViews() {
        // Set up back button - make sure ID matches your layout
        findViewById<ImageButton>(R.id.btnBackMedicalList).setOnClickListener {
            finish()
        }
    }

    private fun displayMedicationDetails() {
        findViewById<TextView>(R.id.tvMedicationName).text = medication.name

        // Handle List<String> description
        // Handle List<String> description
        val descriptionText = when {
            medication.description.isNullOrEmpty() -> "No description available"
            medication.description?.size == 1 -> medication.description?.get(0) ?: "No description available"
            else -> medication.description?.joinToString("\n\n") ?: "No description available"
        }
        findViewById<TextView>(R.id.tvDescription).text = descriptionText
        findViewById<TextView>(R.id.tvDescription).text = descriptionText

        // Handle dosage
        medication.dosage?.let { dosage ->
            findViewById<TextView>(R.id.tvDosage).text = "Standard Dosage: $dosage"
        } ?: run {
            findViewById<TextView>(R.id.tvDosage).text = "Standard Dosage: Not specified"
        }

        // Handle dosage range
        if (medication.dosageMin != null && medication.dosageMax != null) {
            val unit = medication.dosageUnit ?: "mg"
            findViewById<TextView>(R.id.tvDosageRange).text =
                "Dosage Range: ${medication.dosageMin} - ${medication.dosageMax} $unit/kg"
        } else {
            findViewById<TextView>(R.id.tvDosageRange).text = "Dosage Range: Not specified"
        }

        // Handle frequency
        medication.frequency?.let { frequency ->
            findViewById<TextView>(R.id.tvFrequency).text = "Frequency: $frequency"
        } ?: run {
            findViewById<TextView>(R.id.tvFrequency).text = "Frequency: Not specified"
        }

        // Handle List<String> warnings
        medication.warnings?.let { warnings ->
            if (warnings.isNotEmpty()) {
                val warningsText = warnings.joinToString("\n• ", "• ")
                findViewById<TextView>(R.id.tvWarnings).text = "Warnings:\n$warningsText"
            } else {
                findViewById<TextView>(R.id.tvWarnings).text = "Warnings: None"
            }
        } ?: run {
            findViewById<TextView>(R.id.tvWarnings).text = "Warnings: Not specified"
        }

        // Handle conditions treated (List<String>)
        medication.conditionsTreated?.let { conditions ->
            if (conditions.isNotEmpty()) {
                val conditionsText = conditions.joinToString(", ")
                // You might want to add a TextView for this in your layout
                // findViewById<TextView>(R.id.tvConditions).text = "Conditions: $conditionsText"
            }
        }

        // Handle prescription required
        medication.prescriptionRequired?.let { prescriptionRequired ->
            findViewById<TextView>(R.id.tvPrescription).text =
                if (prescriptionRequired) "Prescription Required" else "No Prescription Required"
            findViewById<TextView>(R.id.tvPrescription).setTextColor(
                if (prescriptionRequired) getColor(android.R.color.holo_red_dark)
                else getColor(android.R.color.holo_green_dark)
            )
        } ?: run {
            findViewById<TextView>(R.id.tvPrescription).text = "Prescription: Not specified"
        }
    }

    private fun setupDosageCalculator() {
        val calculateButton = findViewById<Button>(R.id.btnCalculateDosage)
        val weightEditText = findViewById<EditText>(R.id.etWeight)
        val weightUnitSpinner = findViewById<Spinner>(R.id.spinnerWeightUnit)
        val dosageResultTextView = findViewById<TextView>(R.id.tvDosageResult)

        // Setup spinner
        val weightUnits = arrayOf("kg", "lb")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, weightUnits)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        weightUnitSpinner.adapter = adapter

        calculateButton.setOnClickListener {
            val weightText = weightEditText.text.toString()
            if (weightText.isEmpty()) {
                Toast.makeText(this, "Please enter weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = weightText.toDoubleOrNull()
            if (weight == null || weight <= 0) {
                Toast.makeText(this, "Please enter a valid weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weightUnit = weightUnitSpinner.selectedItem as String
            calculateDosage(weight, weightUnit)
        }
    }

    private fun calculateDosage(weight: Double, weightUnit: String) {
        val dosageResultTextView = findViewById<TextView>(R.id.tvDosageResult)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarDosage)

        progressBar.visibility = android.view.View.VISIBLE
        dosageResultTextView.visibility = android.view.View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.calculateDosage(medication.name, weight, weightUnit)

                withContext(Main) {
                    progressBar.visibility = android.view.View.GONE

                    if (result.isSuccess) {
                        val dosageData = result.getOrNull()
                        if (dosageData != null) {
                            displayDosageResult(dosageData)
                        } else {
                            showDosageError("No dosage data received")
                        }
                    } else {
                        showDosageError(result.exceptionOrNull()?.message ?: "Unknown error")
                    }
                }
            } catch (e: Exception) {
                withContext(Main) {
                    progressBar.visibility = android.view.View.GONE
                    showDosageError(e.message ?: "Calculation failed")
                }
            }
        }
    }

    private fun showDosageError(message: String) {
        val dosageResultTextView = findViewById<TextView>(R.id.tvDosageResult)
        dosageResultTextView.text = "Error: $message"
        dosageResultTextView.setTextColor(getColor(android.R.color.holo_red_dark))
        dosageResultTextView.visibility = android.view.View.VISIBLE
    }

    private fun displayDosageResult(dosageData: vcmsa.projects.petcareapp.Data.Models.API.DosageCalculationResponse) {
        val dosageResultTextView = findViewById<TextView>(R.id.tvDosageResult)

        val resultText = """
            Dosage Calculation Result:
            
            Medication: ${dosageData.medication}
            Animal Weight: ${dosageData.animalWeight.value} ${dosageData.animalWeight.unit}
            
            Recommended Dosage:
            ${dosageData.calculatedDosage.min} mg - ${dosageData.calculatedDosage.max} mg}
            
            Frequency: ${dosageData.calculatedDosage.frequency ?: "Not specified"}
            
            Dosage per kg: ${dosageData.calculatedDosage.dosagePerKg.min} - ${dosageData.calculatedDosage.dosagePerKg.max} ${dosageData.calculatedDosage.dosagePerKg.unit ?: "mg"}/kg
        """.trimIndent()

        dosageResultTextView.text = resultText
        dosageResultTextView.setTextColor(getColor(android.R.color.black))
        dosageResultTextView.visibility = android.view.View.VISIBLE
    }
}