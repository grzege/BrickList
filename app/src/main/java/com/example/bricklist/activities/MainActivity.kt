package com.example.bricklist.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.preference.PreferenceManager
import com.example.bricklist.R
import com.example.bricklist.adapters.InventoryAdapter
import com.example.bricklist.database.DatabaseAccess
import com.example.bricklist.objects.Settings
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        Settings.prefix=prefs.getString("prefixOfURL","http://fcds.cs.put.poznan.pl/MyWeb/BL/")
        Settings.archive=prefs.getBoolean("archive",true)
        val listView = findViewById<ListView>(R.id.main_listview)
        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess?.open()
        if (databaseAccess != null) {
            val listItems = ArrayList(databaseAccess.returnInventories())
            listItems.sortByDescending { it.lastAccessed }
            val adapter = InventoryAdapter(this,listItems)
            listView.adapter = adapter
            main_listview.setOnItemClickListener{_,_,position,_ ->
                val selectedInventory = listItems[position]
                val detailIntent =
                    ProjectActivity.newIntent(
                        this,
                        selectedInventory
                    )
                startActivity(detailIntent)
            }
        }
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun toDate(string: String): LocalDate? {
//        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
//        return LocalDate.parse(string,formatter)
//    }
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

