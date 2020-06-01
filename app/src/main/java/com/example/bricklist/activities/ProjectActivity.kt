package com.example.bricklist.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Xml
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import com.example.bricklist.R
import com.example.bricklist.adapters.BricksAdapter
import com.example.bricklist.database.DatabaseAccess
import com.example.bricklist.tables.Brick
import com.example.bricklist.tables.Inventory
import com.example.bricklist.tables.InventoryPart
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        val actionBar = supportActionBar
        actionBar!!.title = "Project"
        val inventoryID = intent.extras?.getString(EXTRA_ID)

        val databaseAccess = DatabaseAccess.getInstance(applicationContext)
        databaseAccess?.open()
        databaseAccess?.updateAccessDate(inventoryID!!.toInt())
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
                val colorID = inventoryParts[i].colorID
                val code = databaseAccess.findUniqueCode(itemID,colorID)
                if (name != null && colorName != null && code != null)
                    bricks.add(Brick(id, name, colorName, itemID, quantityInSet, quantityInStore,colorID,code))
                else if (name != null && colorName != null)
                    bricks.add(Brick(id, name, colorName, itemID, quantityInSet, quantityInStore,colorID))
            }
        }
        databaseAccess?.close()
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
    fun writeXML(list:ArrayList<InventoryPart>,inventoryID:Int){
        val docBuilder: DocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val doc: Document = docBuilder.newDocument()

        val root: Element = doc.createElement("INVENTORY")

        for(i in 0 until list.size) {
            if(list[i].quantityInSet-list[i].quantityInStore==0)
                continue
            val item : Element = doc.createElement("ITEM")
            val itemtype : Element = doc.createElement("ITEMTYPE")
            val itemid : Element = doc.createElement("ITEMID")
            val color : Element = doc.createElement("COLOR")
            val qtyfilled : Element = doc.createElement("QTYFILLED")
            root.appendChild(item)
            itemtype.appendChild(doc.createTextNode(list[i].itemType))
            item.appendChild(itemtype)
            itemid.appendChild(doc.createTextNode(list[i].itemID))
            item.appendChild(itemid)
            color.appendChild(doc.createTextNode(list[i].colorID.toString()))
            item.appendChild(color)
            qtyfilled.appendChild(doc.createTextNode((list[i].quantityInSet-list[i].quantityInStore).toString()))
            item.appendChild(qtyfilled)
        }
        doc.appendChild(root)
        val transformer : Transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")

        val path=this.filesDir
        val outDir = File(path,"Output")
        outDir.mkdir()
        val file =  File(outDir,"$inventoryID.xml")

        transformer.transform(DOMSource(doc),StreamResult(file))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.project_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exparch -> {
                val inventoryID = intent.extras?.getString(EXTRA_ID)!!.toInt()
                val databaseAccess = DatabaseAccess.getInstance(applicationContext)
                databaseAccess?.open()
                val inventoryParts = databaseAccess?.returnInventoryParts(inventoryID)
                databaseAccess?.archive(inventoryID)
                databaseAccess?.close()
                if(inventoryParts!=null)writeXML(ArrayList(inventoryParts),inventoryID)
                val toast = Toast.makeText(this, "Exportowano do XML i zarchiwizowano", Toast.LENGTH_SHORT)
                toast.show()
                val i = Intent(this, MainActivity::class.java)
                finish()
                startActivity(i)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
                true
            }
        }
    }
}
