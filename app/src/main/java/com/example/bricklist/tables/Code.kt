package com.example.bricklist.tables

class Code {
    val id:Int
    val itemID:Int
    val colorID:Int
    val code:Int
    val image:String
    constructor(id:Int,itemID:Int,colorID:Int,code:Int,image:String){
        this.id=id
        this.itemID=itemID
        this.colorID=colorID
        this.code=code
        this.image=image
    }
}