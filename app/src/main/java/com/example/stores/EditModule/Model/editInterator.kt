package com.example.stores.EditModule.Model

import com.example.stores.StoreAplicacion
import com.example.stores.common.Entity.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/*
* Nombre: 
* Fecha: 13/04/2021
* Autor: 
* Version:
* Descripción:
*/

class editInterator {
    fun saveStore(elemento: StoreEntity, callBack: (Long) -> Unit){
        doAsync {
            val newid = StoreAplicacion.dataBase.storeDao().IaddStore(elemento)
            uiThread {
                callBack(newid)
            }
        }
    }
    fun updateStore(elemento: StoreEntity, callBack: (StoreEntity) -> Unit){
        doAsync {
            StoreAplicacion.dataBase.storeDao().IupdateStore(elemento)
            uiThread {
                callBack(elemento)
            }
        }
    }
}