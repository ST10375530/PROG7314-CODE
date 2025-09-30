package vcmsa.projects.petcareapp.UI.Welcome2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.petcareapp.UI.Welcome3.Welcome3Activity
import vcmsa.projects.petcareapp.databinding.ActivityWelcome2Binding

class Welcome2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcome2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcome2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.nextButton.setOnClickListener {
            val intent = Intent(this, Welcome3Activity::class.java)
            startActivity(intent)
        }
    }
}