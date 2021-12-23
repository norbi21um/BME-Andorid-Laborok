package hu.bme.aut.publictransport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.publictransport.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(1000)
        setTheme(R.style.Theme_PublicTransport)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            when {
                binding.etEmailAddress.text.toString().isEmpty() -> {
                    binding.etEmailAddress.requestFocus()
                    binding.etEmailAddress.error = "Please enter your email address"
                }
                binding.etPassword.text.toString().isEmpty() -> {
                    binding.etPassword.requestFocus()
                    binding.etPassword.error = "Please enter your password"
                }
                else -> {
                    startActivity(Intent(this, ListActivity::class.java))
                }
            }
        }
    }
}