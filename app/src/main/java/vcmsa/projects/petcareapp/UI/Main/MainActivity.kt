package vcmsa.projects.petcareapp.UI.Main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Settings.ThemeManager
import vcmsa.projects.petcareapp.UI.Welcome.WelcomeActivity
import vcmsa.projects.petcareapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getStartedButton.setOnClickListener {
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            ThemeManager.initializeTheme(this)
        }
    }
}