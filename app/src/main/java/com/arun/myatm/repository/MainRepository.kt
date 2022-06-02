package com.arun.myatm.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.arun.myatm.model.AtmBalanceModel
import com.arun.myatm.model.TransactionListModel
import com.arun.myatm.room.MainLocalDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


/**
// Created by Arun Singh Rawat on 02-06-2022.



 **/

class MainRepository {

    companion object {

        var mainLocalDataBase: MainLocalDataBase? = null


        private fun initializeDB(context: Context) : MainLocalDataBase {
            return MainLocalDataBase.getMainDataBaseClient(context)
        }

        fun insertTransactionData(context: Context, transactionListModel : TransactionListModel) {
            mainLocalDataBase = initializeDB(context)
            CoroutineScope(IO).launch {
                mainLocalDataBase!!.mainDao().insertTransactionData(transactionListModel)
            }
        }

        fun getTransactionDetails(context: Context) : LiveData<List<TransactionListModel>> {
            mainLocalDataBase = initializeDB(context)
            return mainLocalDataBase!!.mainDao().getTransactionDetails()
        }

        fun insertAtmData(context: Context, atmBalanceModel : AtmBalanceModel) {
            mainLocalDataBase = initializeDB(context)
            CoroutineScope(IO).launch {
                mainLocalDataBase!!.mainDao().insertMainAtmData(atmBalanceModel)
            }
        }

        fun getAtmDetails(context: Context) : LiveData<List<AtmBalanceModel>> {
            mainLocalDataBase = initializeDB(context)
            return mainLocalDataBase!!.mainDao().getMainAtmDetails()
        }

    }
}
