package com.example.ecommersexamen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommersexamen.R
import com.example.ecommersexamen.model.HistoryModel
import com.example.ecommersexamen.model.ProductModel

class HistorialAdapter() : RecyclerView.Adapter<HistorialAdapter.ViewHolderHistorial>(),
    Filterable {

    var historyList : ArrayList<HistoryModel>?=null

    class ViewHolderHistorial(view: View): RecyclerView.ViewHolder(view){

        val textHistorial :TextView

        init {
            textHistorial = view.findViewById(R.id.datohistorial)
        }

    }

    fun setListItems (historyLis: ArrayList<HistoryModel>?){
        this.historyList = historyLis
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistorial {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_hist, parent, false)

        return ViewHolderHistorial(view)
    }

    override fun getItemCount(): Int {
        if(historyList!=null){
            return historyList!!.size
        }else
            return 0

    }

    override fun onBindViewHolder(holder: ViewHolderHistorial, position: Int) {
        holder!!.textHistorial.text = historyList!!.get(position).getSugerencia()

    }

    fun search(s:String?, itemfilter:MutableList<HistoryModel>?, onNothingFound: (() -> Unit)?){

        filter.filter(s)
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            val filters = FilterResults()

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                historyList!!.clear()

                return filters.also {
                    it.values = historyList
                }

            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (historyList.isNullOrEmpty())

                notifyDataSetChanged()
            }

        }

    }
}