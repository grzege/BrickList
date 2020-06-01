package com.example.bricklist.activities

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bricklist.R
import com.example.bricklist.objects.Settings
import com.example.bricklist.database.DatabaseAccess
import com.example.bricklist.tables.Inventory
import com.example.bricklist.tables.InventoryPart
import kotlinx.android.synthetic.main.activity_new_project.*
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

class NewProjectActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
        val actionBar = supportActionBar
        actionBar!!.title = "New Project"
    }
    fun onAdd(view: View) {
        when {
            projectID.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź ID", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectID.text.toString().toIntOrNull()==null-> {
                val toast = Toast.makeText(this, "ID musi być liczbą całkowitą!", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectName.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź nazwę", Toast.LENGTH_SHORT)
                toast.show()
            }
            !checkID() -> {
                val id = projectID.text.toString().toInt()
                val invURL = Settings.prefix + id + ".xml"
                downloadData(invURL)
            }
        }
    }

    fun onCheck(view: View) {
        when {
            projectID.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź ID", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectID.text.toString().toIntOrNull()==null-> {
                val toast = Toast.makeText(this, "ID musi być liczbą całkowitą!", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectName.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź nazwę", Toast.LENGTH_SHORT)
                toast.show()
            }
            checkID() -> {
                val toast =
                    Toast.makeText(this, "Projekt o takim ID już istnieje", Toast.LENGTH_SHORT)
                toast.show()
            }
            else -> {
                val toast =
                    Toast.makeText(this, "Nie istnieje projekt o takim ID", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun checkID(): Boolean {
        when {
            projectID.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź ID", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectName.text.toString() == "" -> {
                val toast = Toast.makeText(this, "Wprowadź nazwę", Toast.LENGTH_SHORT)
                toast.show()
            }
            else -> {
                val databaseAccess = DatabaseAccess.getInstance(applicationContext)
                databaseAccess?.open()
                val id = projectID.text.toString().toInt()
                val name = projectName.text.toString()
                return if (databaseAccess?.findInventory(Inventory(id, name)) != null) {
                    databaseAccess.close()
                    val toast =
                        Toast.makeText(this, "Projekt o takim ID już istnieje", Toast.LENGTH_SHORT)
                    toast.show()
                    true
                } else {
                    databaseAccess?.close()
                    false
                }
            }
        }
        return false
    }

    private inner class InvTask : AsyncTask<String, Int, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val databaseAccess = DatabaseAccess.getInstance(applicationContext)
            databaseAccess?.open()
            val items = loadData()
            if(items.size!=0)
            {
                val id = projectID.text.toString().toInt()
                val name = projectName.text.toString()
                databaseAccess?.addInventory(Inventory(id, name))
                databaseAccess?.addInventoryItems(items,id)
            }
            databaseAccess?.close()
            val i = Intent(applicationContext, MainActivity::class.java)
            finish()
            startActivity(i)
        }

        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL(params[0])
                val connection = url.openConnection()
                connection.connect()
                val lengthOfFile = connection.contentLength
                val isStream = url.openStream()
                val testDirectory = File("$filesDir/XML")
                if (!testDirectory.exists()) testDirectory.mkdir()
                val fos = FileOutputStream("$testDirectory/inv.xml")
                val data = ByteArray(1024)
                var count = 0
                var total: Long = 0
                var progress = 0
                count = isStream.read(data)
                while (count != -1) {
                    total += count.toLong()
                    val progress_temp = total.toInt() * 100 / lengthOfFile
                    if (progress_temp % 10 == 0 && progress != progress_temp) {
                        progress = progress_temp
                    }
                    fos.write(data, 0, count)
                    count = isStream.read(data)
                }
                isStream.close()
                fos.close()
            } catch (e: MalformedURLException) {
                return "Malformed URL"
            } catch (e: FileNotFoundException) {
                return "File not found"
            } catch (e: IOException) {
                return "IO Exception"
            }
            return "success"
        }
    }

    private fun downloadData(url: String) {
        val it = InvTask()
        it.execute(url)
    }

    fun loadData(): MutableList<InventoryPart> {
        var itemList = mutableListOf<InventoryPart>()

        val filename = "inv.xml"
        val path = filesDir
        val inDir = File(path, "XML")

        if (inDir.exists()) {
            val file = File(inDir, filename)
            if (file.exists()) {
                val xmlDoc: Document =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
                xmlDoc.documentElement.normalize()
                val items: NodeList = xmlDoc.getElementsByTagName("ITEM")
                for (i in 0 until items.length) {
                    val itemNode: Node = items.item(i)
                    if (itemNode.nodeType == Node.ELEMENT_NODE) {
                        val elem = itemNode as Element
                        val children = elem.childNodes

                        var itemType: String? = null
                        var itemID: String? = null
                        var qty: Int? = null
                        var color: Int? = null
                        var alternate: String? = null

                        for (j in 0 until children.length) {
                            val node = children.item(j)
                            if (node is Element) {
                                when (node.nodeName) {
                                    "ITEMTYPE" -> {
                                        itemType = node.textContent
                                    }
                                    "ITEMID" -> {
                                        itemID = node.textContent
                                    }
                                    "QTY" -> {
                                        qty = node.textContent.toInt()
                                    }
                                    "COLOR" -> {
                                        color = node.textContent.toInt()
                                    }
                                    "ALTERNATE" -> {
                                        alternate = node.textContent
                                    }
                                }
                            }
                        }
                        if (alternate == "N" && itemID != null && itemType != null && color != null && qty != null)
                            itemList.add(InventoryPart(itemID, itemType, color, qty))
                    }
                }
                //file.delete()
            }
        }

        return itemList
    }
}

