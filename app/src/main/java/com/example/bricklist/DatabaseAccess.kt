package com.example.bricklist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bricklist.tables.Inventory
import com.example.bricklist.tables.InventoryPart

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
    fun addInventoryItems(inventoryParts: MutableList<InventoryPart>, InventoryID:Int){
        for (i in 0 until inventoryParts.size){
            val values = ContentValues()
            val item=inventoryParts[i]
            val id = getCount("InventoriesParts")+1
            values.put("id",id)
            values.put("InventoryID",InventoryID)
            values.put("TypeID",item.itemType)
            values.put("ItemID",item.itemID)
            values.put("QuantityInSet",item.quantityInSet)
            values.put("ColorID",item.colorID)
            db?.insert("InventoriesParts",null,values)
        }
    }
    private fun getCount(table:String):Long{
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
    fun findColorName(colorID:Int): String? {
        val query = "select Name from Colors where Code=\"$colorID\""
        val cursor = db?.rawQuery(query,null)
        var name:String?=null
        if (cursor != null) {
            if(cursor.moveToFirst()){
                name = cursor.getString(0)
                cursor.close()
            }
        }
        cursor?.close()
        return name
    }
    fun findBrickName(itemID:String): String? {
        val query = "select Name from Parts where Code=\"$itemID\""
        val cursor = db?.rawQuery(query,null)
        var name:String?=null
        if (cursor != null) {
            if(cursor.moveToFirst()){
                name = cursor.getString(0)
                cursor.close()
            }
        }
        cursor?.close()
        return name
    }
    fun returnInventories():MutableList<Inventory> {
        val query = "select * from Inventories"
        val cursor = db?.rawQuery(query,null)
        val inventories=mutableListOf<Inventory>()
        if (cursor != null) {
            while(cursor.moveToNext()){
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val active = cursor.getInt(2)
                val lastAccessed = cursor.getString(3)
                inventories.add(Inventory(id,name,active,lastAccessed))
            }
        }
        cursor?.close()
        return inventories
    }
    fun returnInventoryParts(InventoryID: Int):MutableList<InventoryPart> {
        val query = "select * from InventoriesParts where InventoryID=$InventoryID"
        val cursor = db?.rawQuery(query,null)
        val items=mutableListOf<InventoryPart>()
        if (cursor != null) {
            while(cursor.moveToNext()){
                val itemType = cursor.getString(2)
                val itemID = cursor.getString(3)
                val quantityInSet = cursor.getInt(4)
                val quantityInStore = cursor.getInt(5)
                val colorID = cursor.getInt(6)
                items.add(InventoryPart(itemID,itemType,colorID,quantityInSet,quantityInStore))
            }
        }
        cursor?.close()
        return items
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