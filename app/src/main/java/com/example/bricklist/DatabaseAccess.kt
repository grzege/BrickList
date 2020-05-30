package com.example.bricklist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bricklist.tables.Inventory
import com.example.bricklist.tables.Item

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
    fun addInventoryItems(items: MutableList<Item>,InventoryID:Int){
        for (i in 0 until items.size){
            val values = ContentValues()
            val item=items[i]
            val id = getCount("InventoriesParts")+1
            values.put("id",id)
            values.put("InventoryID",InventoryID)
            values.put("TypeID",item.itemType)
            values.put("ItemID",item.itemID)
            values.put("QuantityInSet",item.quantity)
            values.put("ColorID",item.color)
            db?.insert("InventoriesParts",null,values)
        }
    }
    fun getCount(table:String):Long{
        return DatabaseUtils.queryNumEntries(db,table)
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
                result = Inventory(id,name,active.toInt(),lastAccessed)
                cursor.close()
            }
        }
        cursor?.close()
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
        cursor?.close()
        return names
    }
    fun returnInventories():MutableList<Inventory> {
        val query = "select * from Inventories"
        val cursor = db?.rawQuery(query,null)
        var inventories=mutableListOf<Inventory>()
        if (cursor != null) {
            while(cursor.moveToNext()){
                var id = cursor.getInt(0)
                var name = cursor.getString(1)
                var active = cursor.getInt(2)
                var lastAccessed = cursor.getString(3)
                inventories.add(Inventory(id,name,active,lastAccessed))
            }
        }
        cursor?.close()
        return inventories
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