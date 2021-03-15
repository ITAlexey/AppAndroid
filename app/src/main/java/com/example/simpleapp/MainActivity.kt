package com.example.simpleapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter: PinCodeAdapter = PinCodeAdapter(4, 0)
    private lateinit var binding: ActivityMainBinding
    private var pinSetUpState = false
    private var mainPin = ""
    private var enteredPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRv()
        trackButtons()
    }

    private fun trackButtons() {
        binding.setupBtn.setOnClickListener { setUpPinCode(it) }
        binding.number0.setOnClickListener { addNumber("0") }
        binding.number1.setOnClickListener { addNumber("1") }
        binding.number2.setOnClickListener { addNumber("2") }
        binding.number3.setOnClickListener { addNumber("3") }
        binding.number4.setOnClickListener { addNumber("4") }
        binding.number5.setOnClickListener { addNumber("5") }
        binding.number6.setOnClickListener { addNumber("6") }
        binding.number7.setOnClickListener { addNumber("7") }
        binding.number8.setOnClickListener { addNumber("8") }
        binding.number9.setOnClickListener { addNumber("9") }
        binding.acceptArrow.setOnClickListener { acceptPinCode() }
        binding.backspaceImg.setOnClickListener { removeNumber() }
    }

    private fun setUpPinCode(view: View?) {
        view?.visibility = View.GONE
        displayViews()
    }

    private fun displayViews() {
        binding.rvPinCode.visibility = View.VISIBLE
        binding.infoMsg.visibility = View.VISIBLE
        binding.numbers.visibility = View.VISIBLE
    }

    private fun acceptPinCode() {
        if (!pinSetUpState) {
            pinSetUpState = true
            binding.infoMsg.text = "Input pin code"
            binding.acceptArrow.visibility = View.INVISIBLE
            adapter.updateState(enteredPin.length)
        } else {
            if (mainPin == enteredPin) {
               Toast.makeText(applicationContext, "You are logged in!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Incorrect pin code, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNumber(number: String) {
        if (mainPin.length == adapter.itemCount - 1 || enteredPin.length == adapter.itemCount - 1)
            binding.acceptArrow.visibility = View.VISIBLE
        if (!pinSetUpState) {
            if (mainPin.length < adapter.itemCount) {
                mainPin += number
                adapter.updateState(mainPin.length)
            }
        } else {
            if (enteredPin.length < adapter.itemCount) {
                enteredPin += number
                adapter.updateState(enteredPin.length)
            }
        }
    }

    private fun removeNumber() {
        binding.acceptArrow.visibility = View.INVISIBLE
        if (!pinSetUpState) {
            if (mainPin.isNotEmpty()) {
                mainPin = mainPin.substring(0, mainPin.length - 1)
                adapter.updateState(mainPin.length)
            }
        } else {
            if (enteredPin.isNotEmpty()) {
                enteredPin = enteredPin.substring(0, enteredPin.length - 1)
                adapter.updateState(enteredPin.length)
            }
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