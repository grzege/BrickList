package com.example.bricklist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bricklist.R
import com.example.bricklist.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = supportActionBar
        actionBar!!.title = "Settings"
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content,
                SettingsFragment()
            )
            .commit()
    }
}
