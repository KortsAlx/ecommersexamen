package com.example.ecommersexamen.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ecommersexamen.model.ProductModel

@Database(entities = arrayOf(ProductModel::class), version = 1, exportSchema = false)
abstract class ProductRoom :RoomDatabase() {

    abstract val productDao : ProductDao

    companion object{
        private var INSTANCE : ProductRoom?=null
        fun getInstance(context: Context): ProductRoom?{
            if(INSTANCE==null){
                synchronized(ProductRoom::class){
                    INSTANCE = Room.databaseBuilder(context,
                        ProductRoom::class.java, "productroom.db")
                        .addCallback(sRoomDataBaseCallback)
                        .build()

                }
            }

            return INSTANCE
        }

        val sRoomDataBaseCallback= object  : RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                ProductSync(INSTANCE!!).execute()


            }
        }
    }
}