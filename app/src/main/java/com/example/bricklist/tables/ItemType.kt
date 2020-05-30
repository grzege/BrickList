package com.example.bricklist.tables

class ItemType {
    val id:Int
    val code:String
    val name:String
    val namePL:String

    constructor(id:Int,code:String,name:String,namePL:String)
    {
        this.id=id
        this.code=code
        this.name=name
        this.namePL=namePL
    }
}