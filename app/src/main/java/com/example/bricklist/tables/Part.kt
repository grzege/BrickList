package com.example.bricklist.tables

class Part {
    val id:Int
    val typeID:Int
    val code:String
    val name:String
    val namePL:String
    val categoryID:Int
    constructor(id:Int,typeID:Int,code:String,name:String,namePL:String,categoryID:Int) {
        this.id=id
        this.typeID=typeID
        this.code=code
        this.name=name
        this.namePL=namePL
        this.categoryID=categoryID
    }
}