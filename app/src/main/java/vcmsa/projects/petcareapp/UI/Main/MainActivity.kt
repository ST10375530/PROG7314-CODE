package vcmsa.projects.petcareapp.UI.Main


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import vcmsa.projects.petcareapp.R
import vcmsa.projects.petcareapp.Data.Services.NotificationHelper
//import vcmsa.projects.petcareapp.Data.Services.AlarmScheduler
//import vcmsa.projects.petcareapp.UI.AddPets.AddPetsActivity
import vcmsa.projects.petcareapp.UI.Welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnGetStarted = findViewById<Button>(R.id.get_started_button)
        btnGetStarted.setOnClickListener {
            val intent: Intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }

    }
}
