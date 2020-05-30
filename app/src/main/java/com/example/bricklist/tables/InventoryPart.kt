package com.example.bricklist.tables

class InventoryPart {
    val id:Int
    val inventoryID:Int
    val typeID:Int
    val quantityInSet:Int
    val quantityInStore:Int
    val colorID:Int
    val extra:Int

    constructor(id:Int,inventoryID:Int,typeID:Int,quantityInSet:Int,quantityInStore:Int,
                colorID:Int,extra:Int){
        this.id=id
        this.inventoryID=inventoryID
        this.typeID=typeID
        this.quantityInSet=quantityInSet
        this.quantityInStore=quantityInStore
        this.colorID=colorID
        this.extra=extra
    }
}