package com.example.brainrealm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

private const val PREFS_NAME = "MyPreferences"

@Suppress("DEPRECATION")
class Settings : AppCompatActivity() {

    lateinit var binding : ActivitySettingsBinding
    private lateinit var userViewModel: UserViewModel
    private val PICK_IMAGE_REQUEST = 1
    private val userId = FirebaseAuth.getInstance().currentUser?.uid



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)





        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)



        fullNameSave(userId.toString() , "Mehdi Israfilov")
        startImagePicker()




    }

    private fun startImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            val imageUri: Uri? = data.data



            if (imageUri != null) {
                userViewModel.uploadImage(imageUri, userId.toString())
            } else {
                Toast.makeText(this, "Seçilen resim URI'si null.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fullNameSave(userId: String ,fullName: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(userId, fullName)

        editor.apply()
    }

}