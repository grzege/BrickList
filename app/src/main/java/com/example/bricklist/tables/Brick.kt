package com.example.bricklist.tables

class Brick {
    val name:String
    val colorName:String
    val itemID:String
    val quantityInSet:Int
    val quantityInStore:Int
    constructor(
        name:String,
        colorName: String,
        itemID: String,
        quantityInSet:Int,
        quantityInStore:Int) {
        this.name=name
        this.colorName=colorName
        this.itemID=itemID
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
    }
}