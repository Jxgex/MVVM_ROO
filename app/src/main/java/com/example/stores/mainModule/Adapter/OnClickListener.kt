package com.example.stores.mainModule.Adapter

import com.example.stores.common.Entity.StoreEntity

interface OnClickListener {
    fun OnClickListener(storeEntity: StoreEntity)
    fun OnFavoritePut(informacion: StoreEntity)
    fun OnDeleteItem(informacion: StoreEntity)
}