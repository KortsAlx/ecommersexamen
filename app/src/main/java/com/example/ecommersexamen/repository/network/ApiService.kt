package com.example.ecommersexamen.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/demo-gapsi/search?")
    fun Productos(@Query("query") query: Int?, @Header("X-IBM-Client-Id") auth : String) : Call<String>

}