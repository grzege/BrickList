package com.example.bricklist.tables

class Brick {
    val id:Int
    val name:String
    val colorName:String
    val itemID:String
    val quantityInSet:Int
    var quantityInStore:Int
    val colorID:Int
    val code:Int?
    constructor(
        id:Int, name:String, colorName: String, itemID: String, quantityInSet:Int, quantityInStore:Int, colorID:Int) {
        this.id=id
        this.name=name
        this.colorName=colorName
        this.itemID=itemID
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
        this.colorID=colorID
        this.code=null
    }
    constructor(id:Int, name:String, colorName: String, itemID: String, quantityInSet:Int, quantityInStore:Int, colorID:Int, code:Int) {
        this.id=id
        this.name=name
        this.colorName=colorName
        this.itemID=itemID
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
        this.colorID=colorID
        this.code=code
    }
}