package com.example.ecommersexamen.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.example.ecommersexamen.R
import com.example.ecommersexamen.model.ProductModel

class ProductsAdapter(): RecyclerView.Adapter<ProductsAdapter.ViewHolderProducts>(), Filterable {

    var productsList : ArrayList<ProductModel>?=null
    var itemsFilter : MutableList<ProductModel> ?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderProducts {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_product, parent, false)

        return ViewHolderProducts(view)

    }

    fun setListItems (productsList: ArrayList<ProductModel>?){
        this.productsList = productsList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {

        if(productsList!=null){
            return productsList!!.size
        }else
            return 0
    }

    override fun onBindViewHolder(holder: ViewHolderProducts, position: Int) {
        val products = productsList?.get(position)
        holder!!.txPrice?.text = productsList!!.get(position).getPrice()
        holder!!.txTitle?.text = productsList!!.get(position).getTitle()

        Glide.with(holder.itemView.context)
            .load(productsList!!.get(position).getImage()) // image url // any image in case of error
            .override(200, 200) // resizing
            .centerCrop()
            .into(holder!!.image);
    }


    class ViewHolderProducts(view: View): RecyclerView.ViewHolder(view){

        val txPrice :TextView
        val txTitle :TextView
        val image : ImageView

        init {
            txPrice = view.findViewById(R.id.precio_data)
            txTitle = view.findViewById(R.id.title_data)
            image = view.findViewById(R.id.iViewProduct)
        }
    }
    fun search(s:String?,itemsFilter: MutableList<ProductModel>?, onNothingFound: (() -> Unit)?){
        Log.d("TAG", " items filter"+itemsFilter)
        this.itemsFilter= itemsFilter

        filter.filter(s)


    }


    override fun getFilter(): Filter {
        return object : Filter() {
            val filters = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                productsList!!.clear()

                productsList!!.addAll(itemsFilter!!.toMutableList())

                return filters.also {
                    it.values = productsList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                notifyDataSetChanged()

            }
        }
    }
}