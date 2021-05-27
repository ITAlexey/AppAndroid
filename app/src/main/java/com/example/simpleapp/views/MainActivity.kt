package com.example.simpleapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.presenters.MainActivityPresenter
import com.example.simpleapp.views.fragments.PinCodeFragment

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter = MainActivityPresenter(this)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showPinCodeFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.imgBtnSettings.setOnClickListener { presenter.onSettingsButtonClicked() }
    }

    private fun showPinCodeFragment() {
        val pinCodeFragment = PinCodeFragment.newInstance()
        supportFragmentManager.commit {
            add(R.id.fragment_container, pinCodeFragment)
        }
    }

    override fun openSettingsDialog() {
        val dialog = DialogSettingsFragment.newInstance(0)
        dialog.show(supportFragmentManager, "hello")
    }
}