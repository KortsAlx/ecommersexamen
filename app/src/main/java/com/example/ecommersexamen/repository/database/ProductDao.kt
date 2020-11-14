package com.example.ecommersexamen.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommersexamen.model.ProductModel


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductAll(prodcts:List<ProductModel>)


    @Query("DELETE FROM products")
    fun deleteAll()

    @Query("SELECT *FROM products")
    fun getAllProducts(): LiveData<List<ProductModel>>

}