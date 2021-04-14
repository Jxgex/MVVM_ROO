package com.example.stores.mainModule.Model

import android.widget.Toast
import com.example.stores.StoreAplicacion
import com.example.stores.common.Entity.StoreEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/*
* Nombre: 
* Fecha: Jorge Eduardo Martínez Mohedano
* Autor: 
* Version:
* Descripción:
*/
class mainInteractor {
    fun getStores(callBack: (MutableList<StoreEntity>)-> Unit){
        doAsync {
            val storesList = StoreAplicacion.dataBase.storeDao().IgetAllStores()
            uiThread {
                callBack(storesList)
            }
        }
    }
    fun deleteStore(elemento:StoreEntity, callBack: (StoreEntity) -> Unit){
        doAsync {
            StoreAplicacion.dataBase.storeDao().IdeleteStore(elemento)
            uiThread {
                //mAdapter.delete(informacion)
                //Toast.makeText(application,"Eliminado correctamente", Toast.LENGTH_LONG).show()
                callBack(elemento)
            }
        }
    }
    fun updateStore(elemento:StoreEntity, callBack: (StoreEntity) -> Unit){
        doAsync {
            StoreAplicacion.dataBase.storeDao().IupdateStore(elemento)
            uiThread {
                //mAdapter.update(informacion)
                callBack(elemento)
            }
        }
    }

}