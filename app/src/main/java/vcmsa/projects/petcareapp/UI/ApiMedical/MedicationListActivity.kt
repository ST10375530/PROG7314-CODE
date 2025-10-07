package vcmsa.projects.petcareapp.UI.ApiMedical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.petcareapp.UI.ApiMedical.MedicationAdapter
import vcmsa.projects.petcareapp.UI.ApiMedical.MedicationViewModel
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.UI.Home.HomeActivity
import vcmsa.projects.petcareapp.UI.specificMedicine.MedicationDetailActivity

class MedicationListActivity : AppCompatActivity() {

    private lateinit var viewModel: MedicationViewModel
    private lateinit var adapter: MedicationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private lateinit var errorTextView: TextView

    private lateinit var backBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_list)

        try {
            initViews()
            setupViewModel()
            setupRecyclerView()
            observeViewModel()
            setupBackButton()
        } catch (e: Exception) {
            Log.d("Error API List: ", e.message.toString())
            Toast.makeText(this, "Error initializing activity: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun setupBackButton()
    {
        backBtn = findViewById(R.id.btnBack)
        backBtn.setOnClickListener {
            val intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun initViews() {
        recyclerView = findViewById(R.id.rvMedications)
        progressBar = findViewById(R.id.progressBar)
        searchView = findViewById(R.id.searchView)
        errorTextView = findViewById(R.id.tvError)

        // Setup search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchMedications(newText ?: "")
                return true
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[MedicationViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = MedicationAdapter(emptyList()) { medication ->
            // When item is clicked, open detail activity
            val intent = android.content.Intent(this, MedicationDetailActivity::class.java).apply {
                putExtra("MEDICATION", medication)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.medications.observe(this) { medications ->
            adapter.updateData(medications)
            recyclerView.visibility = android.view.View.VISIBLE
            errorTextView.visibility = android.view.View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
            if (isLoading) {
                recyclerView.visibility = android.view.View.GONE
                errorTextView.visibility = android.view.View.GONE
            }
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage != null) {
                errorTextView.text = errorMessage
                errorTextView.visibility = android.view.View.VISIBLE
                recyclerView.visibility = android.view.View.GONE
            } else {
                errorTextView.visibility = android.view.View.GONE
            }
        }
    }
}