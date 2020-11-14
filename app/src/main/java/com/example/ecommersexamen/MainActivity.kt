package com.example.ecommersexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ecommersexamen.model.ProductModel
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommersexamen.ui.ProductsAdapter
import com.example.ecommersexamen.viewmodel.ProductsViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    lateinit var productsViewModel : ProductsViewModel
    lateinit var retrofitRecyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txt = findViewById<TextView>(R.id.txViewrr)
        val searchView : SearchView = findViewById(R.id.searhVw)
        retrofitRecyclerView = findViewById(R.id.rViewProducts) as RecyclerView
        searchView.setOnQueryTextListener(this)

        setAdapter()


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
                //txt.text = t.toString()
                productAdapter.setListItems(ArrayList(t))

            }
        })


    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

    lateinit var productAdapter : ProductsAdapter
    private fun setAdapter(){
        productAdapter = ProductsAdapter()
        retrofitRecyclerView.layoutManager = LinearLayoutManager(this)
        retrofitRecyclerView.adapter = productAdapter


    }
}
