package com.example.ecommersexamen.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommersexamen.model.HistoryModel

@Dao
interface HistorySearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historial: HistoryModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistorialAll(prodcts:List<HistoryModel>)


    @Query("DELETE FROM historial")
    fun deleteAllhistorial()

    @Query("SELECT *FROM historial")
    fun getAllHistorial(): List<HistoryModel>

    @Query("SELECT *FROM historial WHERE sugerencia = :sugerencia")
    fun getAllHistorialSug(sugerencia:String): LiveData<List<HistoryModel>>


}