package com.example.bricklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bricklist.Tables.Inventory
import kotlinx.android.synthetic.main.activity_new_project.*

class NewProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
    }
    fun onAdd(view:View)
    {
        when {
            projectID.text.toString()=="" -> {
                val toast = Toast.makeText(this, "Wprowadź ID", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectName.text.toString()=="" -> {
                val toast = Toast.makeText(this, "Wprowadź nazwę", Toast.LENGTH_SHORT)
                toast.show()
            }
            !onCheck(view)-> {
                val id=projectID.text.toString().toInt()
                val name = projectName.text.toString()
                val databaseAccess = DatabaseAccess.getInstance(applicationContext)
                databaseAccess?.open()
                databaseAccess?.addInventory(Inventory(id,name))
                databaseAccess?.close()
                val toast = Toast.makeText(this, "Dodano nowy projekt!", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
    fun onCheck(view:View):Boolean
    {
        when {
            projectID.text.toString()=="" -> {
                val toast = Toast.makeText(this, "Wprowadź ID", Toast.LENGTH_SHORT)
                toast.show()
            }
            projectName.text.toString()=="" -> {
                val toast = Toast.makeText(this, "Wprowadź nazwę", Toast.LENGTH_SHORT)
                toast.show()
            }
            else -> {
                val id=projectID.text.toString().toInt()
                val name = projectName.text.toString()
                val databaseAccess = DatabaseAccess.getInstance(applicationContext)
                databaseAccess?.open()
                return if(databaseAccess?.findInventory(Inventory(id,name))!=null){
                    val toast = Toast.makeText(this, "Projekt o takim ID już istnieje", Toast.LENGTH_SHORT)
                    toast.show()
                    databaseAccess.close()
                    true
                }else{
                    val toast = Toast.makeText(this, "Nie istnieje projekt o takim ID", Toast.LENGTH_SHORT)
                    toast.show()
                    databaseAccess?.close()
                    false
                }
            }
        }
        return false
    }
}
