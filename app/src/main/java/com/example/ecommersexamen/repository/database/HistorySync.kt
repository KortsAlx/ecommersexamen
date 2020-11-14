package com.example.ecommersexamen.repository.database

import android.os.AsyncTask

class HistorySync internal constructor(db:HistoryRoom): AsyncTask<Void, Void, Void>(){
    private val dao : HistorySearchDao

    init {
        dao = db.hisotrydao
    }

    override fun doInBackground(vararg params: Void?): Void? {
        dao.deleteAllhistorial()
        return null

    }
}