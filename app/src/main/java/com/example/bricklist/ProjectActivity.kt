package com.example.bricklist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bricklist.tables.Inventory

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        val name = intent.extras?.getString(EXTRA_ID)
        val toast = Toast.makeText(this, name, Toast.LENGTH_LONG)
        toast.show()
    }
    companion object{
        val EXTRA_ID = "id"
        val EXTRA_NAME = "name"
        val EXTRA_ACTIVE = "active"
        val EXTRA_LAST = "last"

        fun newIntent(context: Context,inventory: Inventory): Intent {
            val detailIntent = Intent(context,ProjectActivity::class.java)

            detailIntent.putExtra(EXTRA_ID,inventory.id.toString())
            detailIntent.putExtra(EXTRA_NAME,inventory.name)
            detailIntent.putExtra(EXTRA_ACTIVE,inventory.active.toString())
            detailIntent.putExtra(EXTRA_LAST,inventory.lastAccessed)

            return detailIntent
        }
    }
}
