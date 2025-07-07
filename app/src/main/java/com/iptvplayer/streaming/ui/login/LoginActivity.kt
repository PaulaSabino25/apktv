package com.iptvplayer.streaming.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iptvplayer.streaming.databinding.ActivityLoginBinding
import com.iptvplayer.streaming.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val server = binding.serverEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (server.isBlank() || username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.loadingProgressBar.visibility = View.VISIBLE
            // Simulação de autenticação
            binding.root.postDelayed({
                binding.loadingProgressBar.visibility = View.GONE
                if (username == "demo" && password == "demo") {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("SERVER_URL", server)
                    intent.putExtra("USERNAME", username)
                    intent.putExtra("PASSWORD", password)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }, 1200)
        }
    }
}
