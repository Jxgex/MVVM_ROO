package com.example.stores.EditModule.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stores.EditModule.Model.editInterator
import com.example.stores.common.Entity.StoreEntity

/*
* Nombre: 
* Fecha: 13/04/2021
* Autor: 
* Version:
* Descripción:
*/
class editViewMode: ViewModel() {
    private val storeSelected = MutableLiveData<StoreEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()
    private val interactor:editInterator
    init {
        interactor = editInterator()
    }
    ///Getters And Setters ///
    fun setStoreSelect(storeEntity: StoreEntity){
        storeSelected.value = storeEntity
    }
    fun getStoreSelect():LiveData<StoreEntity>{
        return  storeSelected
    }
    fun setshowFab(opcion:Boolean){
        showFab.value = opcion
    }
    fun getshowFab():LiveData<Boolean>{
        return  showFab
    }
    fun setresult(objeto:Any){
        result.value = objeto
    }
    fun getresult():LiveData<Any>{
        return result
    }
    fun SaveStore(storeEntity: StoreEntity){
        interactor.saveStore(storeEntity,{ newid->
            result.value = newid
        })
    }
    fun UpdateStore(storeEntity: StoreEntity){
        interactor.updateStore(storeEntity,{ storeUpdate ->
            result.value = storeUpdate
        })
    }
}