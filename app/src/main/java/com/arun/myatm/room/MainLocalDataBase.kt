package com.arun.myatm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arun.myatm.model.AtmBalanceModel
import com.arun.myatm.model.TransactionListModel


/**
// Created by Arun Singh Rawat on 02-06-2022.

 **/


@Database(entities = [TransactionListModel::class, AtmBalanceModel::class], version = 1, exportSchema = false)
abstract class MainLocalDataBase :RoomDatabase() {

  abstract fun mainDao() : MainDao

  companion object {

    @Volatile
    private var INSTANCE: MainLocalDataBase? = null

    fun getMainDataBaseClient(context: Context) : MainLocalDataBase {

      if (INSTANCE != null) return INSTANCE!!

      synchronized(this) {
        INSTANCE = Room
          .databaseBuilder(context, MainLocalDataBase::class.java, "MAIN_LOCAL_DATABASE")
          .fallbackToDestructiveMigration()
          .build()


        return INSTANCE!!

      }
    }

  }

}