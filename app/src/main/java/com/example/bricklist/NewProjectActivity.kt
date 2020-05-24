package com.example.bricklist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_project.*

class NewProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)
    }
    fun onAdd()
    {
        if(projectID.text==null){
            val toast = Toast.makeText(applicationContext, "Wprowadź ID", Toast.LENGTH_LONG)
            toast.show()
        }
        else if (projectName.text==null){
            val toast = Toast.makeText(applicationContext, "Wprowadź nazwę", Toast.LENGTH_LONG)
            toast.show()
        }
        else{

        }
    }
    fun onCheck()
    {

    }
}
