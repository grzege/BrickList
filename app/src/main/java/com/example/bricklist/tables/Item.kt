package com.example.bricklist.tables

class Item {
    val itemID:String
    val itemType:String
    val color:Int
    val quantity:Int

    constructor(itemID:String,itemType:String,color:Int,quantity:Int){
        this.itemID=itemID
        this.itemType=itemType
        this.color=color
        this.quantity=quantity
    }
}