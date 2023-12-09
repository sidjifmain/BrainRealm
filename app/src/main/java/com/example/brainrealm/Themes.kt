package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Themes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)


        val planetBtn = findViewById<ImageView>(R.id.themes_planet_button)


        planetBtn.setOnClickListener {
            startActivity(
                Intent(
                    this , PlanetMap::class.java
                )
            )
        }
    }
}