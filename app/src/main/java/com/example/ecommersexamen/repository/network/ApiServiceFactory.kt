package com.example.ecommersexamen.repository.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommersexamen.model.ProductModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception

class ApiServiceFactory {

    fun providesWebServicesProducts(): LiveData<List<ProductModel>>{
        val data = MutableLiveData<List<ProductModel>>()
        var webServiceResponseList : List<ProductModel>

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://00672285.us-south.apigw.appdomain.cloud")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            val service = retrofit.create(ApiService::class.java)
            service.Productos(null, "7cb89f3e-6b59-4166-9f01-68e56a3fead8").enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("response", " r"+response.body())
                    webServiceResponseList = parseJsonProducts(response.body())
                    data.value = webServiceResponseList
                }
            })

        }catch (e:Exception){

        }

        return data
    }

    private fun parseJsonProducts(response: String?):List<ProductModel>{
        val apiResult = ArrayList<ProductModel>()
        val jsonObject: JSONObject
        val jsonArray : JSONArray

        try {
            jsonObject = JSONObject(response)
            val items : String?= jsonObject.optString("items")
            jsonArray = JSONArray(items)
            for (i in 0 until jsonArray.length()){
                var jsonInfo : JSONObject = jsonArray.getJSONObject(i)
                val productModel = ProductModel()

                productModel.setId(jsonInfo.getString("id"))
                productModel.setTitle(jsonInfo.getString("title"))
                productModel.setRating(jsonInfo.getString("rating"))
                productModel.setPrice(jsonInfo.getString("price"))
                productModel.setImage(jsonInfo.getString("image"))

                apiResult.add(productModel)

            }
        }catch (e:Exception){
            Log.d("ex ", " ex  "+e)


        }

        return apiResult
    }

}