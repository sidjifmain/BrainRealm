package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        initUi()

        binding.loginQeydiyyatBtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            val email = binding.loginEmailInput.text.toString()
            val pass = binding.loginPassInput.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Login button clicked, make the button invisible and show the progress bar
                binding.button.isVisible = false
                binding.loginProgressBar.isVisible = true

                // Sign in with UserViewModel
                userViewModel.signIn(email, pass)
            } else {
                // Empty fields are not allowed, show a toast message
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe LiveData changes to update UI when user data changes
        userViewModel.user.observe(this, { user ->
            val intent = Intent(this, SelectGameMode::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun initUi() {
        binding.passVisibleBtn.setOnClickListener {
            togglePasswordVisibility(binding.loginPassInput)
        }
    }

    private fun togglePasswordVisibility(passwordEditText: EditText) {
        val isPasswordVisible = passwordEditText.transformationMethod == null

        if (isPasswordVisible) {
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            passwordEditText.transformationMethod = null
        }

        passwordEditText.setSelection(passwordEditText.text.length)
    }
}
