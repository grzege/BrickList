package com.example.bricklist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.bricklist.adapters.InventoryAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        val listView = findViewById<ListView>(R.id.main_listview)
        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess?.open()
        if (databaseAccess != null) {
            val listItems = ArrayList(databaseAccess.returnInventories())
            val adapter = InventoryAdapter(this,listItems)
            listView.adapter = adapter
            main_listview.setOnItemClickListener{_,_,position,_ ->
                val selectedInventory = listItems[position]
                val detailIntent = ProjectActivity.newIntent(this,selectedInventory)
                startActivity(detailIntent)
            }
        }
    }
    fun addProject(v: View) {
        val i = Intent(this, NewProjectActivity::class.java)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
    }
}

