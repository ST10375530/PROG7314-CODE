package vcmsa.projects.petcareapp.UI.Welcome3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Login.LoginActivity
import vcmsa.projects.petcareapp.databinding.ActivityWelcome3Binding

class Welcome3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcome3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcome3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}