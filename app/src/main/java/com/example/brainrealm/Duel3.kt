package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brainrealm.R
import com.example.brainrealm.databinding.ActivityDuel1Binding
import com.example.brainrealm.databinding.ActivityDuel3Binding

class Duel3 : AppCompatActivity() {

    lateinit var binding: ActivityDuel3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuel3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.oynaBtn2.setOnClickListener {
            val intent = Intent(this , Question::class.java)
            intent.putExtra("planet" , "planetx")
            startActivity(intent)
            finish()
        }

    }
}