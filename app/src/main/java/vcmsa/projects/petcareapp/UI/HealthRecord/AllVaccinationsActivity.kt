package vcmsa.projects.petcareapp.UI.HealthRecord

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class AllVaccinationsActivity : AppCompatActivity() {

    private lateinit var vaccinationRecyclerView: RecyclerView
    private lateinit var noVaccinationsText: TextView
    private val vaccinationAdapter = VaccinationAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_vaccinations)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViews()
        loadVaccinations()
        setupClickListeners()
    }

    private fun setupViews() {
        vaccinationRecyclerView = findViewById<RecyclerView>(R.id.vaccinationsRecyclerView)
        noVaccinationsText = findViewById<TextView>(R.id.noVaccinationsText)

        vaccinationRecyclerView.layoutManager = LinearLayoutManager(this)
        vaccinationRecyclerView.adapter = vaccinationAdapter
    }

    private fun loadVaccinations() {
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)
        val vaccinationsJson = sharedPref.getString("VACCINATIONS", "[]")
        val vaccinations = jsonToVaccinations(vaccinationsJson ?: "[]")

        vaccinationAdapter.updateVaccinations(vaccinations.toMutableList())

        // Show/hide empty state
        noVaccinationsText.visibility = if (vaccinations.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setupClickListeners() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        findViewById<MaterialButton>(R.id.addVaccinationButton).setOnClickListener {
            val intent = Intent(this, AddMedicalInfoActivity::class.java)
            startActivity(intent)
        }
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
}