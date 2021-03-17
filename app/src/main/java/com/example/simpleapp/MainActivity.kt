package com.example.simpleapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter: PinCodeAdapter = PinCodeAdapter(4, 0)
    private lateinit var binding: ActivityMainBinding
    private lateinit var appState: SharedPreferences
    private lateinit var setUppedPin: String
    private var pinSetUpState = false
    private var isSetUpBtnClicked = false
    private var isConfirmationPinCodeState = false
    private lateinit var intermediatePin: String
    private var temporaryPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appState = getSharedPreferences("PinCode", Context.MODE_PRIVATE)
        setUppedPin = appState.getString("PINCODE", "").toString()
        pinSetUpState = setUppedPin.isNotEmpty()
        initRv()
        if (pinSetUpState)
            binding.setupBtn.visibility = View.INVISIBLE
        trackButtons()
    }

    private fun trackButtons() {
        binding.setupBtn.setOnClickListener { setUpPinCode(it) }
        binding.number0.setOnClickListener { applyActions("0", it) }
        binding.number1.setOnClickListener { applyActions("1", it) }
        binding.number2.setOnClickListener { applyActions("2", it) }
        binding.number3.setOnClickListener { applyActions("3", it) }
        binding.number4.setOnClickListener { applyActions("4", it) }
        binding.number5.setOnClickListener { applyActions("5", it) }
        binding.number6.setOnClickListener { applyActions("6", it) }
        binding.number7.setOnClickListener { applyActions("7", it) }
        binding.number8.setOnClickListener { applyActions("8", it) }
        binding.number9.setOnClickListener { applyActions("9", it) }
        binding.acceptArrow.setOnClickListener { checkPinCode() }
        binding.backspaceImg.setOnClickListener { removeNumber() }
    }

    private fun applyActions(number: String, item: View?) {
        item?.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
        addNumber(number)
    }

    private fun setUpPinCode(view: View?) {
        view?.visibility = View.GONE
        binding.infoMsg.text = "Create pin code"
        isSetUpBtnClicked = true
    }


    private fun checkPinCode() {
        binding.acceptArrow.visibility = View.INVISIBLE
        when {
            pinSetUpState -> comparePinCodes()
            isConfirmationPinCodeState -> recordPermanentPinCode()
            isSetUpBtnClicked -> confirmEnteredPinCode()
            else -> showMessage("Set up pin code first")
        }
        temporaryPin = ""
        adapter.updateState(temporaryPin.length)
    }

    private fun recordPermanentPinCode() {
        if (intermediatePin == temporaryPin) {
            binding.infoMsg.text = "Enter pin code"
            val editor = appState.edit()
            editor.putString("PINCODE", intermediatePin)
            editor.apply()
            setUppedPin = intermediatePin
            pinSetUpState = true
            showMessage("Pin code has been recorded!")
        } else {
            showMessage("Pin codes are different, try again")
        }
    }

    private fun confirmEnteredPinCode() {
        intermediatePin = temporaryPin
        isConfirmationPinCodeState = true
        binding.infoMsg.text = "Repeat pin code"
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun comparePinCodes() {
        if (temporaryPin == setUppedPin)
            showMessage("You are logged in!")
        else
            showMessage("Incorrect pin code, try again")
    }

    private fun addNumber(number: String) {
        if (temporaryPin.length == adapter.itemCount - 1)
            binding.acceptArrow.visibility = View.VISIBLE
        if (temporaryPin.length < adapter.itemCount) {
            temporaryPin += number
            adapter.updateState(temporaryPin.length)
        }
    }

    private fun removeNumber() {
        binding.acceptArrow.visibility = View.INVISIBLE
        if (temporaryPin.isNotEmpty()) {
            temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
            adapter.updateState(temporaryPin.length)
        }
    }

    private fun initRv() {
        val recyclerView = binding.rvPinCode
        recyclerView.addOnItemTouchListener(RecyclerVIewDisabler())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }

}