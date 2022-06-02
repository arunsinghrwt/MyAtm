package com.arun.myatm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
// Created by Arun Singh Rawat on 02-06-2022.
 **/

@Entity(tableName = "atmAmount")
data class AtmBalanceModel(
  @ColumnInfo(name = "atmAmt")
  var atmAmt: String,
  @ColumnInfo(name = "twoK")
  var twok: String,
  @ColumnInfo(name = "fiveH")
  var fiveH: String,
  @ColumnInfo(name = "twoH")
  var twoH: String,
  @ColumnInfo(name = "oneH")
  var oneH: String
)
{
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "atmId")
  var atmId: Int? = null
}