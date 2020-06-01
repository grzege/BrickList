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
        val currentMonth: String = String.format("%02d",(Calendar.getInstance().get(Calendar.MONTH)+1))//miesiące indeksowane są od 0
        val currentDay: String =  String.format("%02d",Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
        val currentHour: String =  String.format("%02d",Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        val currentMin: String =  String.format("%02d",Calendar.getInstance().get(Calendar.MINUTE))
        val currentSec: String =  String.format("%02d",Calendar.getInstance().get(Calendar.SECOND))
        return "$currentDay.$currentMonth.$currentYear $currentHour:$currentMin:$currentSec"
    }
}