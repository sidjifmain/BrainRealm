package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.brainrealm.databinding.ActivityDuel1Binding

class Duel1 : AppCompatActivity() {
    lateinit var binding : ActivityDuel1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDuel1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.oynaBtn.setOnClickListener {
            startActivity(
                Intent(
                    this , Duel2::class.java
                )
            )
            finish()
        }

    }
}