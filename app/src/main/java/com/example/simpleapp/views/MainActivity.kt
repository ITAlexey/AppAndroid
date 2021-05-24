package com.example.simpleapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.views.fragments.PinCodeFragment

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showPinCodeFragment()
        }
    }

    private fun showPinCodeFragment() {
        val pinCodeFragment = PinCodeFragment.newInstance()
        supportFragmentManager.commit {
            add(R.id.fragment_container, pinCodeFragment)
        }
    }
}