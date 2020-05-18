package com.example.bricklist

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.main_listview)
        val listItems = arrayListOf<String>("alfa","beta","gamma")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,listItems)
        listView.adapter=adapter
    }
    fun addProject(v:View){
        val i= Intent(this,NewProjectActivity::class.java)
        startActivity(i)
    }
}
