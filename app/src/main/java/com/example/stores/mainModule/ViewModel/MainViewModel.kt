package com.example.stores.mainModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stores.common.Entity.StoreEntity
import com.example.stores.mainModule.Model.mainInteractor

/*
* Nombre: 
* Fecha: 13/Abril/2021
* Autor: Jorge Eduardo Martinez Mohedano
* Version: 1.0
* Descripción:
*/
class MainViewModel: ViewModel(){
    private var interactor: mainInteractor
    private var storeList: MutableList<StoreEntity>
    init {
        interactor = mainInteractor()
        storeList = mutableListOf()
    }
    private val Stores: MutableLiveData<MutableList<StoreEntity>> by lazy{
        MutableLiveData<MutableList<StoreEntity>>().also {
            loadStores()
        }
    }

    fun getStore():LiveData<MutableList<StoreEntity>>{
        return Stores
    }
    fun deleteStore(storeEntity: StoreEntity){
        interactor.deleteStore(storeEntity,{
            val index = storeList.indexOf(storeEntity)
            if(index != -1){
                storeList.removeAt(index)
                Stores.value = storeList
            }
        })
    }
    fun updateStore(storeEntity: StoreEntity){
        storeEntity.Favorita = !storeEntity.Favorita
        interactor.updateStore(storeEntity,{
            val index = storeList.indexOf(storeEntity)
            if(index != -1){
                storeList.set(index,storeEntity)
                Stores.value = storeList
            }
        })
    }
    private fun loadStores(){
        interactor.getStores {
            Stores.value = it
            storeList = it
        }
    }
}