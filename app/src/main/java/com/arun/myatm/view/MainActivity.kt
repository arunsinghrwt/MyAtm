package com.arun.myatm.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arun.myatm.BR

import com.arun.myatm.databinding.ActivityMainBinding
import com.arun.myatm.model.AtmBalanceModel
import com.arun.myatm.model.TransactionListModel
import com.arun.myatm.view.utility.saveFirstInstall
import com.arun.myatm.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var context: Context
    private lateinit var transactionAdapter : TransactionAdapter
    private lateinit var preferences: SharedPreferences
    private val transactionList: ArrayList<TransactionListModel> = ArrayList()
    private lateinit var atmBalanceModel: AtmBalanceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this@MainActivity
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initView()
    }

    private fun initView() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        setAtmData()
        addAdapter()
        binding.btnWithdraw.setOnClickListener {
             if(verifyAmount()){
                 getNoteCount()
             }
         }



    }




    private fun getNoteCount() {
        val withdrawAmt = binding.editAmount.text.toString().toInt()
        var amount = withdrawAmt
        var hundredCount : Int = 0
        var twoHundredCount : Int = 0
        var fiveHundredCount: Int = 0
        var twoThousandCount : Int = 0
        if(amount >= 2000 )   {
            twoThousandCount = amount/2000
            if (twoThousandCount >= atmBalanceModel.twok.toInt()){
                twoThousandCount = atmBalanceModel.twok.toInt()
                amount -= twoThousandCount * 2000;
            }else{
                amount -= twoThousandCount * 2000;
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (amount >= 500 ) {
                fiveHundredCount = amount/500
                if (fiveHundredCount >= atmBalanceModel.fiveH.toInt()){
                    fiveHundredCount = atmBalanceModel.fiveH.toInt()
                    amount -= twoThousandCount * 500;
                }else{
                    amount -= fiveHundredCount * 500;
                }

            }
        }, 100)
        Handler(Looper.getMainLooper()).postDelayed({
            if( amount >= 200)  {
                twoHundredCount = amount/200;
                if (twoHundredCount > atmBalanceModel.twoH.toInt()){
                    twoHundredCount = atmBalanceModel.twoH.toInt()
                    amount -= twoHundredCount * 200;
                }else{
                    amount -= twoHundredCount * 200;
                }

            }
        }, 200)
        Handler(Looper.getMainLooper()).postDelayed({
            if( amount >= 100 ) {
                hundredCount = amount/100;
                if (hundredCount > atmBalanceModel.oneH.toInt()){
                    hundredCount = atmBalanceModel.oneH.toInt()
                    amount -= hundredCount * 100;
                }else{
                    amount -= hundredCount * 100;
                }
            }
        }, 300)

        Handler(Looper.getMainLooper()).postDelayed({
            val transactionListModel  = TransactionListModel(withdrawAmt.toString(),twoThousandCount.toString(),fiveHundredCount.toString(),twoHundredCount.toString(),hundredCount.toString())
            mainViewModel.insertTransactionData(context,transactionListModel)
            val atmBlc = atmBalanceModel.atmAmt.toInt() - withdrawAmt
            val oneH = atmBalanceModel.oneH.toInt() - hundredCount
            val twoH = atmBalanceModel.twoH.toInt() - twoHundredCount
            val fiveH = atmBalanceModel.fiveH.toInt() - fiveHundredCount
            val twoK = atmBalanceModel.twok.toInt() - twoThousandCount
            val atmBalanceModel  = AtmBalanceModel(atmBlc.toString(),twoK.toString(),fiveH.toString(),twoH.toString(),oneH.toString())
            mainViewModel.insertAtmData(context,atmBalanceModel)
            binding.editAmount.setText("")
            Toast.makeText(this,"Your amount successfully withdrawl",Toast.LENGTH_LONG).show()

        }, 400)


    }

    private fun setAtmData() {
        val firstInstall = preferences.getBoolean("firstInstall",true)
        if (firstInstall){
            val atmBalanceModel  = AtmBalanceModel("100000","25","40","100","100")
            mainViewModel.insertAtmData(context,atmBalanceModel)
            saveFirstInstall("firstInstall",false,this)
        }
        mainViewModel.getTransactionDetails(this)!!.observe(this, Observer {
            if (!it.isNullOrEmpty() ){
                binding.errorTrans.visibility = View.GONE
                binding.viewTrans.visibility = View.VISIBLE
                binding.errorYourTrans.visibility = View.GONE
                binding.transactionRv.visibility = View.VISIBLE
                setLastTransactionData(it)
                transactionList.clear()
                transactionList.addAll(it)
                transactionAdapter.notifyDataSetChanged()
            }else{
                binding.errorTrans.visibility = View.VISIBLE
                binding.viewTrans.visibility = View.GONE
                binding.errorYourTrans.visibility = View.VISIBLE
                binding.transactionRv.visibility = View.GONE
            }

        })

        mainViewModel.getAtmDetails(this)!!.observe(this, Observer {
            if (!it.isNullOrEmpty() ){
                val size = it.size
                atmBalanceModel = it[size-1]
                val atmBalanceModel : AtmBalanceModel = it[size-1]
                binding.setVariable(BR.atmBalance,atmBalanceModel)
            }
        })







    }

    private fun setLastTransactionData(it: List<TransactionListModel>) {
        val size = it.size
        val transactionListModel : TransactionListModel = it[size-1]
        binding.setLastTransaction(transactionListModel)
    }

    private fun addAdapter() {
        transactionAdapter = TransactionAdapter(transactionList)
        binding.transactionRv.layoutManager = LinearLayoutManager(context)
        binding.transactionRv.adapter = transactionAdapter
    }

    private fun verifyAmount(): Boolean {
        when {
            binding.editAmount.text.isNullOrEmpty() -> {
                binding.editAmount.error = "Withdraw amount can not be empty"
                return false
            }
            binding.editAmount.text.toString().toInt() > atmBalanceModel.atmAmt.toInt() -> {
                binding.editAmount.error = "ATM Cash Limit exceeds"
                return false
            }
            binding.editAmount.text.toString().toInt() % 100 != 0 -> {
                binding.editAmount.error = "Withdraw amount should be 100 multiply"
                return false
            }
            else -> {
                binding.editAmount.error = null
                return true
            }
        }
    }
}