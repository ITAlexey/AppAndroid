package com.example.simpleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter: PinCodeAdapter = PinCodeAdapter(4, 0)
    private lateinit var binding: ActivityMainBinding
    private var pinCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRv(binding)
        binding.number0.setOnClickListener { updatePinCode(binding.number0.text as String) }
        binding.number1.setOnClickListener { updatePinCode(binding.number1.text as String) }
        binding.number2.setOnClickListener { updatePinCode(binding.number2.text as String) }
        binding.number3.setOnClickListener { updatePinCode(binding.number3.text as String) }
        binding.number4.setOnClickListener { updatePinCode(binding.number4.text as String) }
        binding.number5.setOnClickListener { updatePinCode(binding.number5.text as String) }
        binding.number6.setOnClickListener { updatePinCode(binding.number6.text as String) }
        binding.number7.setOnClickListener { updatePinCode(binding.number7.text as String) }
        binding.number8.setOnClickListener { updatePinCode(binding.number8.text as String) }
        binding.number9.setOnClickListener { updatePinCode(binding.number9.text as String) }
        binding.backspaceImg.setOnClickListener {reducePinCode()}
    }

    private fun updatePinCode(number: String) {
        if (pinCode.length < adapter.itemCount) {
            pinCode += number
            adapter.updateState(pinCode.length)
        }
    }

    private fun reducePinCode() {
        if (pinCode.isNotEmpty()) {
            pinCode = pinCode.substring(0, pinCode.length - 1)
            adapter.updateState(pinCode.length)
        }
    }

    private fun initRv(binding: ActivityMainBinding) {
        val recyclerView = binding.rvPinCode
        recyclerView.addOnItemTouchListener(RecyclerVIewDisabler())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }

}