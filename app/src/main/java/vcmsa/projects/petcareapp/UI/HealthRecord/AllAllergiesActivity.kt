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
import vcmsa.projects.petcareapp.R

class AllAllergiesActivity : AppCompatActivity() {
    private lateinit var allergiesRecyclerView: RecyclerView
    private lateinit var noAllergiesText: TextView
    private val allergyAdapter = AllergyAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_allergies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViews()
        loadAllergies()
        setupClickListeners()
    }

    private fun setupViews() {
        allergiesRecyclerView = findViewById(R.id.allergiesRecyclerView)
        noAllergiesText = findViewById(R.id.noAllergiesText)

        allergiesRecyclerView.layoutManager = LinearLayoutManager(this)
        allergiesRecyclerView.adapter = allergyAdapter
    }

    private fun loadAllergies() {
        //setting up shared preferences (Developers, 2025):
        val sharedPref = getSharedPreferences("medical_info", MODE_PRIVATE)
        val allergiesJson = sharedPref.getString("ALLERGIES", "[]")
        val allergies = jsonToAllergies(allergiesJson ?: "[]")

        allergyAdapter.updateAllergies(allergies.toMutableList())

        // Show/hide empty state
        noAllergiesText.visibility = if (allergies.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun setupClickListeners() {
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        findViewById<MaterialButton>(R.id.addAllergyButton).setOnClickListener {
            val intent = Intent(this, AddMedicalInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun jsonToAllergies(jsonString: String): List<Allergy> {
        val allergies = mutableListOf<Allergy>()
        try {
            //saving the data in json (Developers, 2025):
            val jsonArray = org.json.JSONArray(jsonString)
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
}

//Reference list:

//Developers. 2025. Save simple data with SharedPreferences. [Online]. Available at: https://developer.android.com/training/data-storage/shared-preferences [Accessed 29 September 2025].