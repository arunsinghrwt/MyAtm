package com.arun.myatm.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arun.myatm.model.AtmBalanceModel
import com.arun.myatm.model.TransactionListModel


/**
// Created by Arun Singh Rawat on 02-06-2022.

 **/

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransactionData(transactionListModel: TransactionListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMainAtmData(atmBalanceModel: AtmBalanceModel)

    @Query("SELECT * FROM atmAmount ORDER BY atmId")
    fun getMainAtmDetails() : LiveData<List<AtmBalanceModel>>

    @Query("SELECT * FROM transactionsList ORDER BY transactionId")
    fun getTransactionDetails(): LiveData<List<TransactionListModel>>
}