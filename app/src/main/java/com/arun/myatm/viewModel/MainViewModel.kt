package com.arun.myatm.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arun.myatm.model.AtmBalanceModel
import com.arun.myatm.model.TransactionListModel
import com.arun.myatm.repository.MainRepository


/**
// Created by Arun Singh Rawat on 02-06-2022.
 **/

class MainViewModel : ViewModel(){

  var liveDataTransactionList: LiveData<List<TransactionListModel>>? = null

  fun insertTransactionData(context: Context, transactionListModel: TransactionListModel) {
    MainRepository.insertTransactionData(context,transactionListModel )
  }

  fun getTransactionDetails(context: Context) : LiveData<List<TransactionListModel>> {
    return MainRepository.getTransactionDetails(context)
  }

  fun insertAtmData(context: Context, atmBalanceModel: AtmBalanceModel) {
    MainRepository.insertAtmData(context,atmBalanceModel )
  }

  fun getAtmDetails(context: Context) : LiveData<List<AtmBalanceModel>> {
    return MainRepository.getAtmDetails(context)
  }

}