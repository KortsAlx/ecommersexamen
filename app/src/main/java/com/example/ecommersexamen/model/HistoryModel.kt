package com.example.ecommersexamen.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "historial")
class HistoryModel {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private var id : String? =null

    @ColumnInfo(name = "sugerencia")
    @SerializedName("sugerencia")
    private var sugerencia : String? =null

    fun getId():String?{
        return id
    }

    fun setId(id:String){
        this.id = id
    }

    fun getSugerencia():String?{
        return sugerencia
    }

    fun setSugerencia(sugerencia:String){
        this.sugerencia = sugerencia
    }

}