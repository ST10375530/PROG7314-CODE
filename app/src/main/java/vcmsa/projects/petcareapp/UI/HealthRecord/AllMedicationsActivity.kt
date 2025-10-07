package vcmsa.projects.petcareapp.UI.HealthRecord

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.json.JSONArray
import vcmsa.projects.petcareapp.R

class AllMedicationsActivity : AppCompatActivity() {

    private lateinit var medicationsRecyclerView: RecyclerView
    private lateinit var noMedicationsText: TextView
    private val medicationAdapter = MedicationAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_medications)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupClickListeners()
        loadMedications()
    }

    private fun setupRecyclerView() {
        medicationsRecyclerView = findViewById(R.id.medicationsRecyclerView)
        noMedicationsText = findViewById(R.id.noMedicationsText)

        medicationsRecyclerView.layoutManager = LinearLayoutManager(this)
        medicationsRecyclerView.adapter = medicationAdapter
    }

    private fun setupClickListeners() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        findViewById<MaterialButton>(R.id.addMedicationButton).setOnClickListener {
            val intent = Intent(this, AddMedicalInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadMedications() {
        //setting up shared preferences (Developers, 2025):
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)
        val medicationsJson = sharedPref.getString("MEDICATIONS", "[]")
        val medications = jsonToMedications(medicationsJson ?: "[]")

        medicationAdapter.updateMedications(medications.toMutableList())

        // Update empty state
        noMedicationsText.visibility = if (medications.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun jsonToMedications(jsonString: String): List<Medication> {
        val medications = mutableListOf<Medication>()
        try {
            //passing the data to json (Developers, 2025):
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
}
//Reference list:

//Developers. 2025. Save simple data with SharedPreferences. [Online]. Available at: https://developer.android.com/training/data-storage/shared-preferences [Accessed 29 September 2025].