package com.example.ecommersexamen.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
class ProductModel {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private var id : String? =null

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    private var rating : String? =null

    @ColumnInfo(name = "price")
    @SerializedName("price")
    private var price : String? =null

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private var image : String? =null

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private var title : String? =null




    fun getId():String?{
        return id
    }

    fun setId(id:String){
        this.id = id
    }

    fun getRating():String?{
        return rating
    }

    fun setRating(rating:String){
        this.rating = rating
    }
    fun getPrice():String?{
        return price
    }

    fun setPrice(price:String){
        this.price = price
    }

    fun getImage():String?{
        return image
    }

    fun setImage(image:String){
        this.image = image
    }


    fun getTitle():String?{
        return title
    }

    fun setTitle(title:String){
        this.title = title
    }


}