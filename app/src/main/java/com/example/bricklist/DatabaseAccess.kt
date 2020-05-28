package com.example.bricklist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bricklist.Tables.Inventory

class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var db: SQLiteDatabase? = null

    var c: Cursor? = null

    fun open() {
        db = openHelper.writableDatabase
    }
    fun close() {
        if (db != null) {
            db!!.close()
        }
    }

    fun addInventory(inventory: Inventory){
        val values = ContentValues()
        values.put("id",inventory.id)
        values.put("Name",inventory.name)
        values.put("Active",inventory.active)
        values.put("LastAccessed", inventory.lastAccessed)
        db?.insert("Inventories",null,values)
    }
    fun findInventory(inventory: Inventory): Inventory? {
        var id=inventory.id
        val query = "select * from Inventories where id=$id"
        val cursor = db?.rawQuery(query,null)
        var result:Inventory?=null
        if (cursor != null) {
            if(cursor.moveToFirst()){
                id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val active = cursor.getString(2)
                val lastAccessed = cursor.getString(3)
                result = Inventory(id,name,active.toBoolean(),lastAccessed)
                cursor.close()
            }
        }
        return result
    }
    fun returnInvNames():MutableList<String> {
        val query = "select * from Inventories"
        val cursor = db?.rawQuery(query,null)
        var names=mutableListOf<String>()
        if (cursor != null) {
            while(cursor.moveToNext()){
                var name = cursor.getString(1)
                names.add(name)
            }
        }
        return names
    }
    companion object {
        private var instance: DatabaseAccess? = null
        fun getInstance(context: Context): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }

    init {
        openHelper = DatabaseOpenHelper(context)
    }
}