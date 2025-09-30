package vcmsa.projects.petcareapp.UI.HealthRecord

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.json.JSONArray
import org.json.JSONObject
import vcmsa.projects.petcareapp.R

class MedicalRecordsActivity : AppCompatActivity() {
    private val addMedicalRecordLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            updateMedicalInfoUI(data)
        }
    }

    private lateinit var vaccinationRecyclerView: RecyclerView
    private lateinit var allergiesRecyclerView: RecyclerView
    private lateinit var treatmentsRecyclerView: RecyclerView
    private lateinit var medicationsRecyclerView: RecyclerView

    private lateinit var noVaccinationsText: TextView
    private lateinit var noAllergiesText: TextView
    private lateinit var noTreatmentsText: TextView
    private lateinit var noMedicationsText: TextView

    private val vaccinationAdapter = VaccinationAdapter(mutableListOf())
    private val allergyAdapter = AllergyAdapter(mutableListOf())
    private val treatmentAdapter = TreatmentAdapter(mutableListOf())
    private val medicationAdapter = MedicationAdapter(mutableListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_medical_records)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerViews()
        setupClickListeners()
        loadSavedMedicalInfo()
        updateEmptyStates()
    }

    private fun setupRecyclerViews() {
        // Vaccinations RecyclerView
        vaccinationRecyclerView = findViewById(R.id.vaccinationsRecyclerView)
        vaccinationRecyclerView.layoutManager = LinearLayoutManager(this)
        vaccinationRecyclerView.adapter = vaccinationAdapter

        // Allergies RecyclerView
        allergiesRecyclerView = findViewById(R.id.allergiesRecyclerView)
        allergiesRecyclerView.layoutManager = LinearLayoutManager(this)
        allergiesRecyclerView.adapter = allergyAdapter

        // Treatments RecyclerView
        treatmentsRecyclerView = findViewById(R.id.treatmentsRecyclerView)
        treatmentsRecyclerView.layoutManager = LinearLayoutManager(this)
        treatmentsRecyclerView.adapter = treatmentAdapter

        // Medications RecyclerView
        medicationsRecyclerView = findViewById(R.id.medicationsRecyclerView)
        medicationsRecyclerView.layoutManager = LinearLayoutManager(this)
        medicationsRecyclerView.adapter = medicationAdapter

        // Empty state text views
        noVaccinationsText = findViewById(R.id.noVaccinationsText)
        noAllergiesText = findViewById(R.id.noAllergiesText)
        noTreatmentsText = findViewById(R.id.noTreatmentsText)
        noMedicationsText = findViewById(R.id.noMedicationsText)
    }

    private fun setupClickListeners() {
        findViewById<TextView>(R.id.tab_wellness).setOnClickListener {
            val intent = Intent(this, HealthRecords::class.java)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.addMedicalRecordsButton).setOnClickListener {
            val intent = Intent(this, AddMedicalInfoActivity::class.java)
            addMedicalRecordLauncher.launch(intent)
        }

        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // See All click listeners
        findViewById<TextView>(R.id.seeAllVaccinations).setOnClickListener {
            val intent = Intent(this, AllVaccinationsActivity::class.java)
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

        // Also make the entire allergies section clickable
        findViewById<TextView>(R.id.seeAllAllergies).setOnClickListener {
            val intent = Intent(this, AllAllergiesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateMedicalInfoUI(data: Intent?) {
        data?.let {
            val allergies = it.getStringExtra("ALLERGIES")
            val allergyDate = it.getStringExtra("ALLERGY_DATE")
            val treatment = it.getStringExtra("TREATMENT")
            val treatmentDate = it.getStringExtra("TREATMENT_DATE")
            val vaccination = it.getStringExtra("VACCINATION")
            val vaccinationDate = it.getStringExtra("VACCINATION_DATE")
            val medication = it.getStringExtra("MEDICATION")

            // Add new records to adapters
            if (!vaccination.isNullOrEmpty() && !vaccinationDate.isNullOrEmpty()) {
                vaccinationAdapter.addVaccination(Vaccination(vaccination, vaccinationDate))
            }

            if (!allergies.isNullOrEmpty() && !allergyDate.isNullOrEmpty()) {
                allergyAdapter.addAllergy(Allergy(allergies, allergyDate))
            }

            if (!treatment.isNullOrEmpty() && !treatmentDate.isNullOrEmpty()) {
                treatmentAdapter.addTreatment(Treatment(treatment, treatmentDate))
            }

            if (!medication.isNullOrEmpty()) {
                val medDate = treatmentDate ?: vaccinationDate ?: ""
                medicationAdapter.addMedication(Medication(medication, medDate))
            }

            saveMedicalInfoToStorage()
            updateEmptyStates()
        }
    }

    private fun saveMedicalInfoToStorage() {
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)
        with(sharedPref.edit()) {
            // Convert lists to JSON strings manually
            putString("VACCINATIONS", vaccinationsToJson(vaccinationAdapter.getVaccinations()))
            putString("ALLERGIES", allergiesToJson(allergyAdapter.getAllergies()))
            putString("TREATMENTS", treatmentsToJson(treatmentAdapter.getTreatments()))
            putString("MEDICATIONS", medicationsToJson(medicationAdapter.getMedications()))
            apply()
        }
    }

    private fun loadSavedMedicalInfo() {
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)

        // Load vaccinations
        val vaccinationsJson = sharedPref.getString("VACCINATIONS", "[]")
        val vaccinations = jsonToVaccinations(vaccinationsJson ?: "[]")
        vaccinationAdapter.updateVaccinations(vaccinations.toMutableList())

        // Load allergies
        val allergiesJson = sharedPref.getString("ALLERGIES", "[]")
        val allergies = jsonToAllergies(allergiesJson ?: "[]")
        allergyAdapter.updateAllergies(allergies.toMutableList())

        // Load treatments
        val treatmentsJson = sharedPref.getString("TREATMENTS", "[]")
        val treatments = jsonToTreatments(treatmentsJson ?: "[]")
        treatmentAdapter.updateTreatments(treatments.toMutableList())

        // Load medications
        val medicationsJson = sharedPref.getString("MEDICATIONS", "[]")
        val medications = jsonToMedications(medicationsJson ?: "[]")
        medicationAdapter.updateMedications(medications.toMutableList())
    }

    // JSON conversion methods
    private fun vaccinationsToJson(vaccinations: List<Vaccination>): String {
        val jsonArray = JSONArray()
        vaccinations.forEach { vaccination ->
            val jsonObject = JSONObject()
            jsonObject.put("name", vaccination.name)
            jsonObject.put("date", vaccination.date)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    private fun jsonToVaccinations(jsonString: String): List<Vaccination> {
        val vaccinations = mutableListOf<Vaccination>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val date = jsonObject.getString("date")
                vaccinations.add(Vaccination(name, date))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return vaccinations
    }

    private fun allergiesToJson(allergies: List<Allergy>): String {
        val jsonArray = JSONArray()
        allergies.forEach { allergy ->
            val jsonObject = JSONObject()
            jsonObject.put("name", allergy.name)
            jsonObject.put("date", allergy.date)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    private fun jsonToAllergies(jsonString: String): List<Allergy> {
        val allergies = mutableListOf<Allergy>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val date = jsonObject.getString("date")
                allergies.add(Allergy(name, date))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return allergies
    }

    private fun treatmentsToJson(treatments: List<Treatment>): String {
        val jsonArray = JSONArray()
        treatments.forEach { treatment ->
            val jsonObject = JSONObject()
            jsonObject.put("name", treatment.name)
            jsonObject.put("date", treatment.date)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    private fun jsonToTreatments(jsonString: String): List<Treatment> {
        val treatments = mutableListOf<Treatment>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val date = jsonObject.getString("date")
                treatments.add(Treatment(name, date))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return treatments
    }

    private fun medicationsToJson(medications: List<Medication>): String {
        val jsonArray = JSONArray()
        medications.forEach { medication ->
            val jsonObject = JSONObject()
            jsonObject.put("name", medication.name)
            jsonObject.put("date", medication.date)
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

    private fun jsonToMedications(jsonString: String): List<Medication> {
        val medications = mutableListOf<Medication>()
        try {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val date = jsonObject.getString("date")
                medications.add(Medication(name, date))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return medications
    }

    private fun updateEmptyStates() {
        // Show/hide empty state messages based on data
        noVaccinationsText.visibility = if (vaccinationAdapter.itemCount == 0) View.VISIBLE else View.GONE
        noAllergiesText.visibility = if (allergyAdapter.itemCount == 0) View.VISIBLE else View.GONE
        noTreatmentsText.visibility = if (treatmentAdapter.itemCount == 0) View.VISIBLE else View.GONE
        noMedicationsText.visibility = if (medicationAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }
}