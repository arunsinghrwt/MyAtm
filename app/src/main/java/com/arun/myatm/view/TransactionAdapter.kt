package com.arun.myatm.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.arun.myatm.R
import com.arun.myatm.databinding.TransactionRowBinding
import com.arun.myatm.model.TransactionListModel


/**
// Created by Arun Singh Rawat on 02-06-2022.



 **/

class TransactionAdapter (private val transactionList: List<TransactionListModel> ) :  RecyclerView.Adapter<BindAbleViewHolder>() {


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindAbleViewHolder  {
    val binding: TransactionRowBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.transaction_row,
      parent,
      false)
    return BindAbleViewHolder(binding)

  }

  override fun onBindViewHolder(holder: BindAbleViewHolder, position: Int) {
    if(!transactionList.isNullOrEmpty()){
      val transactionListModel: TransactionListModel = transactionList[position]
      Log.e("list","-->>>+"+transactionList[position].atmAmt)
      holder.bind(transactionListModel)
    }
  }

  override fun getItemCount(): Int {
    return transactionList.size
  }

}

class BindAbleViewHolder(private val binding: TransactionRowBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(itemViewModel: TransactionListModel) {
    binding.setVariable(androidx.databinding.library.baseAdapters.BR.transaction , itemViewModel)
    binding.executePendingBindings()
  }
}


