package com.example.ecommersexamen.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ecommersexamen.model.HistoryModel

@Database(entities = arrayOf(HistoryModel::class), version = 1, exportSchema = false)
abstract class HistoryRoom :RoomDatabase(){

    abstract val hisotrydao : HistorySearchDao

    companion object{
        private var INSTANCE : HistoryRoom?=null
        fun getInstance(context: Context): HistoryRoom?{
            if(INSTANCE==null){
                synchronized(HistoryRoom::class){
                    INSTANCE = Room.databaseBuilder(context,
                    HistoryRoom::class.java, "hist.db")
                        .addCallback(sRoomDataBaseCallback)
                        .fallbackToDestructiveMigration()
                        .build()



                }
            }

            return INSTANCE
        }

        val sRoomDataBaseCallback= object  : RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                HistorySync(INSTANCE!!).execute()
                //ProductSync(INSTANCE!!).execute()


            }
        }
    }
}