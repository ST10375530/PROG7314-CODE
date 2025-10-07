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

class AllTreatmentsActivity : AppCompatActivity() {
    private lateinit var treatmentsRecyclerView: RecyclerView
    private lateinit var noTreatmentsText: TextView
    private val treatmentAdapter = TreatmentAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_treatments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupClickListeners()
        loadTreatments()
    }

    private fun setupRecyclerView() {
        treatmentsRecyclerView = findViewById(R.id.treatmentsRecyclerView)
        noTreatmentsText = findViewById(R.id.noTreatmentsText)

        treatmentsRecyclerView.layoutManager = LinearLayoutManager(this)
        treatmentsRecyclerView.adapter = treatmentAdapter
    }

    private fun setupClickListeners() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        findViewById<MaterialButton>(R.id.addTreatmentButton).setOnClickListener {
            val intent = Intent(this, AddMedicalInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTreatments() {
        //setting up sharedpreferences (Developers, 2025):
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)
        val treatmentsJson = sharedPref.getString("TREATMENTS", "[]")
        val treatments = jsonToTreatments(treatmentsJson ?: "[]")

        treatmentAdapter.updateTreatments(treatments.toMutableList())

        // Update empty state
        noTreatmentsText.visibility = if (treatments.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun jsonToTreatments(jsonString: String): List<Treatment> {
        val treatments = mutableListOf<Treatment>()
        try {
            //passing from json (Developers, 2025):
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
}
//Reference list:

//Developers. 2025. Save simple data with SharedPreferences. [Online]. Available at: https://developer.android.com/training/data-storage/shared-preferences [Accessed 29 September 2025].
