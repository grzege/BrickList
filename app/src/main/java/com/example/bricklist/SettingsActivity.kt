package com.example.bricklist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

//        toolbar = findViewById(R.id.mToolbar)
//        setSupportActionBar(toolbar)
//
//        supportActionBar().setTitle("Settings")

    }
}
