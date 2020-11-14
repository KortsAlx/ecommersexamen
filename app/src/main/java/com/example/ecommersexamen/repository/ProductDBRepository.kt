package com.example.ecommersexamen.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.ecommersexamen.model.ProductModel
import com.example.ecommersexamen.repository.database.ProductDao
import com.example.ecommersexamen.repository.database.ProductRoom

class ProductDBRepository {
    private var productDao: ProductDao
    private var mAllProducts : LiveData<List<ProductModel>>

    constructor(application: Application){
        val db = ProductRoom.getInstance(application)
        productDao = db!!.productDao
        mAllProducts = productDao.getAllProducts()
    }

    fun getAllProducts(): LiveData<List<ProductModel>>{ return mAllProducts}

    fun insertProducts(productList : List<ProductModel>?){
        Inserts(productDao).execute(productList)
    }

    class Inserts internal constructor(productsDao : ProductDao): AsyncTask<List<ProductModel>, Void, Void>(){
        private var mAsyncProductDao : ProductDao
        init {
            mAsyncProductDao = productsDao

        }

        override fun doInBackground(vararg params: List<ProductModel>): Void? {
            if(params[0]!= null){
                mAsyncProductDao.insertProductAll(params[0])
            }
            return null
        }

    }
}