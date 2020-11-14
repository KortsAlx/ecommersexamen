package com.example.ecommersexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    SearchView.OnSuggestionListener {

    lateinit var productsViewModel : ProductsViewModel
    lateinit var retrofitRecyclerView : RecyclerView
    lateinit var searchView : SearchView

    lateinit var adapterslits: ArrayAdapter<*>

    lateinit var recyclerlistsearch : RecyclerView

    var itemsFilter : MutableList<HistoryModel>?=null
    var itemsNr: MutableList<HistoryModel>?=null




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
        searchView.setOnClickListener {


        }

        val threar = Thread{

            val mDao = HistoryRoom.getInstance(this)!!.hisotrydao
            itemsNr = mDao.getAllHistorial().toMutableList()//mDao.getAllFilters()

            historyAdapter.setListItems(ArrayList(itemsNr))



        }

        threar.start()

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

        val mDao = HistoryRoom.getInstance(this)!!.hisotrydao
        val thread = Thread{
            Single.fromCallable{
                val histomodel = HistoryModel()

                val ultimoid = mDao.getUltimaDate()

                histomodel.setId(ultimoid+1)
                histomodel.setSugerencia(query!!)

                mDao.insert(histomodel)

            }
                .subscribeOn(Schedulers.io())
                .subscribe()



        }
        thread.start()

        if(recyclerlistsearch.visibility == View.GONE){
            recyclerlistsearch.visibility == View.VISIBLE

        }else{
            recyclerlistsearch.visibility == View.GONE


        }


        //Log.d("onqt", " dasdasd")
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        Log.d("onqt", " dasdasd change")
        return true

    }

    override fun onSuggestionSelect(position: Int): Boolean {
        //NADA
        TODO("Not yet implemented")
    }

    override fun onSuggestionClick(position: Int): Boolean {


        //searchView.setQuery(itemshistory.toString(), false)

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
    fun searchLiveData(s: String?){
        Toast.makeText(this, " datos busqueda de "+s, Toast.LENGTH_LONG).show()
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

        //historyAdapter.setListItems(ArrayList(itemsNr))




    }


}
