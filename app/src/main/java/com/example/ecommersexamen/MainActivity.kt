package com.example.ecommersexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ecommersexamen.model.ProductModel
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommersexamen.model.HistoryModel
import com.example.ecommersexamen.repository.database.HistoryRoom
import com.example.ecommersexamen.repository.database.HistorySearchDao
import com.example.ecommersexamen.ui.HistorialAdapter
import com.example.ecommersexamen.ui.ProductsAdapter
import com.example.ecommersexamen.viewmodel.ProductsViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnSuggestionListener {

    lateinit var productsViewModel : ProductsViewModel
    lateinit var retrofitRecyclerView : RecyclerView
    lateinit var searchView : SearchView

    lateinit var adapterslits: ArrayAdapter<*>

    lateinit var recyclerlistsearch : RecyclerView

    var itemsFilter : MutableList<HistoryModel>?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerlistsearch = findViewById(R.id.recyclerViewHistorial) as RecyclerView
        searchView = findViewById(R.id.searhVw) as SearchView

        //val sss= searchView.
        //val itemmArr : ArrayList<HistoryModel> = ArrayList(itemshistory)

        retrofitRecyclerView = findViewById(R.id.rViewProducts) as RecyclerView
        searchView.setOnQueryTextListener(this)
        searchView.setOnSuggestionListener(this)
        //adapterslits = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, )

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
        search(query)

        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        return true

    }

    override fun onSuggestionSelect(position: Int): Boolean {
        //NADA
        TODO("Not yet implemented")
    }

    override fun onSuggestionClick(position: Int): Boolean {
        val itemsearch = searchView.suggestionsAdapter.getItem(position)
        Log.d("item", " searcch "+itemsearch)

        val mDao = HistoryRoom.getInstance(applicationContext)!!.hisotrydao
        val itemshistory = mDao.getAllHistorial().toMutableList()

        historyAdapter.setListItems(ArrayList(itemshistory))

        //searchView.setQuery(itemshistory.toString(), false)

        Log.d("onSclick", " click"+itemshistory)



        return false

    }

    fun setData(){
        val threar = Thread{

            val mDao = HistoryRoom.getInstance(this)!!.hisotrydao
            itemsFilter = mDao.getAllHistorial().toMutableList()//mDao.getAllFilters()



        }

        threar.start()
    }
    private fun search(s:String?){
        setData()
        Log.d("search", " search "+s)

        Log.d("items ", " filters "+itemsFilter)

        historyAdapter.search(s, itemsFilter){

        }

    }

    lateinit var productAdapter : ProductsAdapter
    lateinit var historyAdapter : HistorialAdapter
    private fun setAdapter(){
        productAdapter = ProductsAdapter()
        historyAdapter = HistorialAdapter()
        retrofitRecyclerView.layoutManager = LinearLayoutManager(this)
        retrofitRecyclerView.adapter = productAdapter

        recyclerlistsearch.layoutManager = LinearLayoutManager(this)
        recyclerlistsearch.adapter = historyAdapter




    }


}
