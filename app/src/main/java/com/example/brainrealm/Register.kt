package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("salam" , "Test")
        // Initialize UserViewModel using ViewModelProvider
        userViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(UserViewModel::class.java)

        Log.d("salam" , "Test")

        // Initialize UI elements and set up event listeners
        initUi()

        binding.registerLoginBtn.setOnClickListener {
            startActivity(
                Intent(
                    this, Login::class.java
                )
            )
            finish()
        }
    }



    // Function to initialize UI elements and set up event listeners
    private fun initUi() {
        binding.registerButton.setOnClickListener {
            val email = binding.registerEmailInput.text.toString()
            val pass = binding.registerPassInput.text.toString()
            val confirmPass = binding.registerCPassInput.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    // Register button clicked, make the button invisible and show the progress bar
                    binding.registerButton.isVisible = false
                    binding.registerProgressBar.isVisible = true

                    // Call createUser method in UserViewModel to perform registration
                    userViewModel.createUser(email, pass, binding.registerPassInput)
                } else {
                    // Passwords do not match, show a toast message
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Empty fields are not allowed, show a toast message
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.qayda.setOnClickListener {
            startActivity(
                Intent(
                    this , Terms::class.java
                )
            )
        }

        binding.registerPassVisibleBtn.setOnClickListener {
            togglePasswordVisibility(binding.registerPassInput)
        }

        binding.registerCPassVisibleBtn.setOnClickListener {
            togglePasswordVisibility(binding.registerCPassInput)
        }
    }

    // Function to toggle password visibility
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
