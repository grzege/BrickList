package com.example.bricklist

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bricklist.tables.Inventory
import com.example.bricklist.tables.Item
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        val id = intent.extras?.getString(EXTRA_ID)
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
