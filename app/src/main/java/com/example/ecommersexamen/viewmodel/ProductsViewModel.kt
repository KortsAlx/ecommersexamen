package com.example.ecommersexamen.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ecommersexamen.model.ProductModel
import com.example.ecommersexamen.repository.ProductDBRepository
import com.example.ecommersexamen.repository.network.ApiServiceFactory

class ProductsViewModel : AndroidViewModel{
    var productsDBRepository : ProductDBRepository
    var webserviceRepository : ApiServiceFactory
    lateinit var retroObserverProducts : LiveData<List<ProductModel>>

    var mAllProducts: LiveData<List<ProductModel>>


    constructor(application: Application): super(application){
        productsDBRepository = ProductDBRepository(application)
        webserviceRepository = ApiServiceFactory()
        mAllProducts = productsDBRepository.getAllProducts()

    }

    fun getAllProducts():LiveData<List<ProductModel>>{return mAllProducts}

    fun fetchProductosFromWebService():LiveData<List<ProductModel>>{
        retroObserverProducts = webserviceRepository.providesWebServicesProducts()
        return retroObserverProducts
    }

    fun insertAllProducts(productsList: List<ProductModel>?){
        productsDBRepository.insertProducts(productsList)
    }
}