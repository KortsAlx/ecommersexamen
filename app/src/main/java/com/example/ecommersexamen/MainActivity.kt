package com.example.ecommersexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ecommersexamen.model.ProductModel
import com.example.ecommersexamen.viewmodel.ProductsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var productsViewModel : ProductsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt = findViewById<TextView>(R.id.txViewrr)

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)


        productsViewModel.fetchProductosFromWebService().observe(this, object : Observer<List<ProductModel>>{
            override fun onChanged(t: List<ProductModel>?) {
                Log.d("datos", " viewmodel webservice "+t)
                productsViewModel.insertAllProducts(t)
            }
        })

        productsViewModel.getAllProducts().observe(this, object : Observer<List<ProductModel>>{
            override fun onChanged(t: List<ProductModel>?) {
                Log.d("datos", " viewmodel "+t)
                txt.text = t.toString()

            }
        })


    }
}