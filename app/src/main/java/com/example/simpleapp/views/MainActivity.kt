package com.example.simpleapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import com.example.simpleapp.BaseApp
import com.example.simpleapp.R
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinAdapter: PinAdapter
    private lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
        initPresenter(savedInstanceState)
        initListeners()
    }

    private fun initPresenter(outState: Bundle?) {
        val model = (applicationContext as BaseApp).pinModel
        presenter = MainActivityPresenter(this, model, outState)
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
        pinAdapter = PinAdapter()
        binding.rvPinCode.apply {
            adapter = pinAdapter
            setHasFixedSize(true)
        }
    }

    private fun updateViewVisibility(view: View, isVisible: Boolean) {
        view.isInvisible = !isVisible
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSavedInstanceStateCalled(outState)
    }

    override fun showPopupMessage(@StringRes popupTextResId: Int) =
        Toast.makeText(this, popupTextResId, Toast.LENGTH_SHORT).show()

    override fun updatePinField(pinLen: Int) {
        pinAdapter.updateState(pinLen)
    }

    override fun updateVisibilityBackspaceButton(isVisible: Boolean) {
        updateViewVisibility(binding.imgBackSpace, isVisible)
    }

    override fun updateVisibilityResetButton(isVisible: Boolean) {
        updateViewVisibility(binding.btnReset, isVisible)
    }


    override fun moveToLogInActivity() {
        val intent = WelcomeActivity.creteIntent(this)
        startActivity(intent)
    }

    override fun setTitleText(@StringRes titleTextResId: Int) {
        binding.tvTitle.text = resources.getString(titleTextResId)
    }
}