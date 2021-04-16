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
import com.example.simpleapp.Constants.PIN_STATE
import com.example.simpleapp.Constants.TAG
import com.example.simpleapp.R
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.models.utils.PinState
import com.example.simpleapp.presenters.MainActivityPresenter
import com.example.simpleapp.utils.getEnum
import com.example.simpleapp.utils.putEnum

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinAdapter: PinAdapter
    private lateinit var presenter: MainActivityContract.Presenter
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Main onCreate")
        bundle = savedInstanceState
        presenter = MainActivityPresenter((applicationContext as BaseApp).pinModel)
        initAdapter()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Main onStart")
        presenter.subscribe(
            this,
            if (bundle != null) readFromBundle(bundle) else null
        )
    }

    override fun onStop() {
        Log.d(TAG, "Main onStop")
        presenter.unsubscribe()
        super.onStop()
    }

    private fun readFromBundle(outState: Bundle?): PinState {
        return outState!!.getEnum(PIN_STATE, PinState.CREATE)
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
        pinAdapter = presenter.createPinAdapter()
        binding.rvPinCode.apply {
            adapter = pinAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Main onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "Main onSaveInstanceState")
        outState.putEnum(PIN_STATE, presenter.getPinState())
        super.onSaveInstanceState(outState)
    }

    override fun showPopupMessage(@StringRes popupTextResId: Int) =
        Toast.makeText(this, popupTextResId, Toast.LENGTH_SHORT).show()

    override fun updatePinField(pinLen: Int) {
        pinAdapter.updateState(pinLen)
    }

    override fun showOrHideBackspaceButton(isVisible: Boolean) {
        binding.imgBackSpace.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun showOrHideResetButton(isVisible: Boolean) {
        binding.btnReset.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun moveToLogInActivity() {
        val intent = LogInActivity.creteIntent(this)
        startActivity(Intent(intent))
    }

    override fun setTitleText(titleTextResId: Int) {
        binding.tvTitle.text = resources.getString(titleTextResId)
    }


}