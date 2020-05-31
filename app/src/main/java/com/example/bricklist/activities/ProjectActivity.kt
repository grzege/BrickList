package com.example.bricklist.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.bricklist.R
import com.example.bricklist.adapters.BricksAdapter
import com.example.bricklist.database.DatabaseAccess
import com.example.bricklist.tables.Brick
import com.example.bricklist.tables.Inventory

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        val inventoryID = intent.extras?.getString(EXTRA_ID)

        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess?.open()
        val inventoryParts = databaseAccess?.returnInventoryParts(inventoryID!!.toInt())
        val bricks = mutableListOf<Brick>()
        if (inventoryParts != null) {
            for (i in 0 until inventoryParts.size) {
                val id = inventoryParts[i].id
                val itemID = inventoryParts[i].itemID
                val name = databaseAccess.findBrickName(itemID)
                val colorName = databaseAccess.findColorName(inventoryParts[i].colorID)
                val quantityInSet = inventoryParts[i].quantityInSet
                val quantityInStore = inventoryParts[i].quantityInStore
                if (name != null && colorName != null)
                    bricks.add(Brick(id, name, colorName, itemID, quantityInSet, quantityInStore))
            }
        }
        val listView = findViewById<ListView>(R.id.project_listView)
        val listItems = ArrayList(bricks)
        val adapter = BricksAdapter(this, listItems)
        listView.adapter = adapter
    }
    companion object{
        val EXTRA_ID = "id"
        val EXTRA_NAME = "name"
        val EXTRA_ACTIVE = "active"
        val EXTRA_LAST = "last"

        fun newIntent(context: Context,inventory: Inventory): Intent {
            val detailIntent = Intent(context,
                ProjectActivity::class.java)

            detailIntent.putExtra(EXTRA_ID,inventory.id.toString())
            detailIntent.putExtra(EXTRA_NAME,inventory.name)
            detailIntent.putExtra(EXTRA_ACTIVE,inventory.active.toString())
            detailIntent.putExtra(EXTRA_LAST,inventory.lastAccessed)

            return detailIntent
        }
    }
}
