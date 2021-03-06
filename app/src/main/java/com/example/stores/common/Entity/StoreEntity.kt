package com.example.stores.common.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StoreEntity")
data class StoreEntity(@PrimaryKey(autoGenerate = true) var id:Long = 0,
                 var Nombre:String,
                 var Phone:String,
                 var Favorita:Boolean = false,
                 var website:String = "",
                 var ImgURL:String){
    constructor(): this(Nombre = "", Phone = "", website = "", ImgURL = "")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as StoreEntity
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}