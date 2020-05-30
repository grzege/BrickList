package com.example.bricklist.tables

class InventoryPart {
    val itemID:String
    val itemType:String
    val colorID:Int
    val quantityInSet:Int
    val quantityInStore:Int

    constructor(itemID:String,itemType:String,color:Int,quantityInSet:Int,quantityInStore:Int=0){
        this.itemID=itemID
        this.itemType=itemType
        this.colorID=color
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
    }
}