package com.example.bricklist.tables

class InventoryPart {
    val id:Int
    val itemID:String
    val itemType:String
    val colorID:Int
    val quantityInSet:Int
    val quantityInStore:Int

    constructor(id:Int,itemID:String,itemType:String,color:Int,quantityInSet:Int,quantityInStore:Int=0){
        this.id=id
        this.itemID=itemID
        this.itemType=itemType
        this.colorID=color
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
    }
    constructor(itemID:String,itemType:String,color:Int,quantityInSet:Int,quantityInStore:Int=0){
        this.id=0
        this.itemID=itemID
        this.itemType=itemType
        this.colorID=color
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
    }
}