package com.example.bricklist.tables

import java.util.*

class Inventory {
    val id:Int
    val name:String
    var active:Int
    var lastAccessed: String
    constructor(id:Int,name:String){
        this.id=id
        this.name=name
        this.active=1
        this.lastAccessed=getDate()
    }
    constructor(id:Int,name:String,active:Int,lastAccessed:String){
        this.id=id
        this.name=name
        this.active=active
        this.lastAccessed= lastAccessed
    }
    private fun getDate():String{
        val currentYear: String = Calendar.getInstance().get(Calendar.YEAR).toString();
        val currentMonth: String = (Calendar.getInstance().get(Calendar.MONTH)+1).toString();//miesiące indeksowane są od 0
        val currentDay: String = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString();
        return "$currentDay.$currentMonth.$currentYear"
    }
}