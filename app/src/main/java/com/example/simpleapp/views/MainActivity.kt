package com.example.simpleapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.simpleapp.BaseApp
import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.Constants.TAG
import com.example.simpleapp.R
import com.example.simpleapp.adapter.PinCodeAdapter
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinCodeAdapter: PinCodeAdapter
    private lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Log.d(TAG, "mainActivity created")
        setContentView(binding.root)
        initAdapter()
        val model = (applicationContext as BaseApp).pinModel
        presenter = MainActivityPresenter(model)
        presenter.subscribe(this, savedInstanceState)
        initListeners()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    private fun initListeners() {
        binding.apply {
            btnReset.setOnClickListener { presenter.onResetButtonClicked() }
            tvNumber0.setOnClickListener { onKeyboardButtonClicked(0, it) }
            tvNumber1.setOnClickListener { onKeyboardButtonClicked(1, it) }
            tvNumber2.setOnClickListener { onKeyboardButtonClicked(2, it) }
            tvNumber3.setOnClickListener { onKeyboardButtonClicked(3, it) }
            tvNumber4.setOnClickListener { onKeyboardButtonClicked(4, it) }
            tvNumber5.setOnClickListener { onKeyboardButtonClicked(5, it) }
            tvNumber6.setOnClickListener { onKeyboardButtonClicked(6, it) }
            tvNumber7.setOnClickListener { onKeyboardButtonClicked(7, it) }
            tvNumber8.setOnClickListener { onKeyboardButtonClicked(8, it) }
            tvNumber9.setOnClickListener { onKeyboardButtonClicked(9, it) }
            imgBackSpace.setOnClickListener { onKeyboardButtonClicked(item = it) }
        }
    }

    private fun animateKeyboardButton(item: View) =
        item.startAnimation(AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked))

    private fun onKeyboardButtonClicked(number: Int = -1, item: View) {
        animateKeyboardButton(item)
        when {
            number < 0 -> presenter.onBackspaceButtonClicked()
            else -> presenter.onNumberButtonClicked(number)
        }
    }

    private fun initAdapter() {
        pinCodeAdapter = PinCodeAdapter(0, PIN_SIZE)
        binding.rvPinCode.apply {
            adapter = pinCodeAdapter
            setHasFixedSize(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // save temporary pin and pin state
    }

    override fun showMessage(@StringRes popupTextResId: Int) =
        Toast.makeText(this, popupTextResId, Toast.LENGTH_SHORT).show()

    override fun showBackspaceButton() {
        binding.imgBackSpace.visibility = View.VISIBLE
    }

    override fun hideBackspaceButton() {
        binding.imgBackSpace.visibility = View.INVISIBLE
    }

    override fun updatePinField(pinLen: Int) {
        pinCodeAdapter.updateState(pinLen)
    }

    override fun hideResetButton() {
        binding.btnReset.visibility = View.INVISIBLE
    }

    override fun showLogInActivity() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    override fun setTitleText(titleTextResId: Int) {
        binding.tvTitle.text = resources.getString(titleTextResId)
    }

    override fun showResetButton() {
        binding.btnReset.visibility = View.VISIBLE
    }


}