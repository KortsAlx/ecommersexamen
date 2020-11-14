package com.example.ecommersexamen.repository.database

import android.os.AsyncTask

class ProductSync internal constructor(db: ProductRoom): AsyncTask<Void, Void, Void>(){

    private val dao : ProductDao

    init {
        dao = db.productDao
    }

    override fun doInBackground(vararg params: Void?): Void? {
        dao.deleteAll()
        return null
    }
}