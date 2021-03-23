package com.example.simpleapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.simpleapp.Constants.PIN_CODE_KEY
import com.example.simpleapp.Constants.SHARED_PREF_FILE
import com.example.simpleapp.adapter.PinCodeAdapter
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.helpers.PinState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinCodeAdapter: PinCodeAdapter
    private lateinit var masterKeyAlias: String
    private lateinit var appStore: SharedPreferences
    private lateinit var permanentPin: String
    private lateinit var currentPinState: PinState
    private lateinit var confirmationPin: String

    private var temporaryPin = ""

    companion object {
        const val PIN_SIZE = 4
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEncryptedSharedPref()
        initAppState()
        initAdapter()
        initListeners()
    }

    private fun initEncryptedSharedPref() {
        masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        appStore = EncryptedSharedPreferences.create(
            SHARED_PREF_FILE,
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    private fun initAppState() {
        permanentPin = appStore.getString(PIN_CODE_KEY, "").toString()
        currentPinState = if (permanentPin.isNotEmpty()) {
            binding.btnReset.visibility = View.VISIBLE
            PinState.ENTER
        } else {
            binding.tvTitle.text = resources.getString(R.string.title_create)
            PinState.CREATE
        }
    }

    private fun initListeners() {
        binding.apply {
            btnReset.setOnClickListener { resetPin() }
            tvNumber0.setOnClickListener { configureNumberView(0, it) }
            tvNumber1.setOnClickListener { configureNumberView(1, it) }
            tvNumber2.setOnClickListener { configureNumberView(2, it) }
            tvNumber3.setOnClickListener { configureNumberView(3, it) }
            tvNumber4.setOnClickListener { configureNumberView(4, it) }
            tvNumber5.setOnClickListener { configureNumberView(5, it) }
            tvNumber6.setOnClickListener { configureNumberView(6, it) }
            tvNumber7.setOnClickListener { configureNumberView(7, it) }
            tvNumber8.setOnClickListener { configureNumberView(8, it) }
            tvNumber9.setOnClickListener { configureNumberView(9, it) }
            imgBackSpace.setOnClickListener { removeNumber(it) }
            imgLogOut.setOnClickListener{ makeLogOut() }
            imgFingerPrint.setOnClickListener{ showMessage(R.string.popup_finger_warning) }
        }
    }

    private fun makeLogOut() {
        updateViewAppearance(PinState.ENTER)
        clearPinCodeField()
    }

    private fun clearPinCodeField() {
        temporaryPin = ""
        pinCodeAdapter.updateState(temporaryPin.length)
    }

    private fun resetPin() {
       if (currentPinState == PinState.ENTER) {
           updateViewAppearance(PinState.RESET, View.GONE, View.GONE, resources.getString(R.string.title_confirm))
           clearPinCodeField()
       }
    }

    private fun configureNumberView(number: Int, item: View) {
        if (currentPinState != PinState.LOGIN)
        {
            item.startAnimation(
                AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
            )
            addNumber(number)
        }
    }

    private fun checkPinState() {
        when (currentPinState) {
            PinState.RESET -> deletePin()
            PinState.ENTER -> checkPinField()
            PinState.CONFIRM -> recordPermanentPin()
            PinState.CREATE -> createPin()
        }
        clearPinCodeField()
    }

    private fun deletePin() {
        if (temporaryPin == permanentPin) {
            removeSetUppedPin()
        } else
            showMessage(R.string.popup_different)
    }

    private fun checkPinField() {
        if (temporaryPin == permanentPin) {
            updateViewAppearance(PinState.LOGIN, View.GONE, View.VISIBLE,
                resources.getString(R.string.title_logged_in))
            showMessage(R.string.popup_success_enter)
        } else
            showMessage(R.string.popup_fail_enter)
    }

    private fun removeSetUppedPin() {
        appStore.edit().remove(PIN_CODE_KEY).apply()
        showMessage(R.string.popup_reset)
        updateViewAppearance(PinState.CREATE, View.GONE)
    }

    private fun updateViewAppearance(
        status: PinState,
        buttonResetVisibility: Int = View.VISIBLE,
        buttonLogOutVisibility: Int = View.GONE,
        message: String = resources.getString(R.string.title)) {
        binding.apply {
          btnReset.visibility = buttonResetVisibility
          imgLogOut.visibility = buttonLogOutVisibility
          tvTitle.text = message
        }
        currentPinState = status
    }

    private fun recordPermanentPin() {
        if (confirmationPin == temporaryPin) {
            updateViewAppearance(PinState.ENTER)
            appStore
                .edit()
                .putString(PIN_CODE_KEY, confirmationPin)
                .apply()
            permanentPin = confirmationPin
            showMessage(R.string.popup_record)
        } else {
            showMessage(R.string.popup_different)
        }
    }

    private fun createPin() {
        confirmationPin = temporaryPin
        updateViewAppearance(PinState.CONFIRM, View.GONE, View.GONE, resources.getString(R.string.title_repeat))
    }

    private fun showMessage(id: Int) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
    }

    private fun addNumber(number: Int) {
        if (temporaryPin.length < pinCodeAdapter.itemCount) {
            temporaryPin += number.toString()
            pinCodeAdapter.updateState(temporaryPin.length)
        }
        if (temporaryPin.length == PIN_SIZE)
            checkPinState()
    }

    private fun removeNumber(item: View) {
        item.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
        if (temporaryPin.isNotEmpty()) {
            temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
            pinCodeAdapter.updateState(temporaryPin.length)
        }
    }

    private fun initAdapter() {
        pinCodeAdapter  = PinCodeAdapter(temporaryPin.length)
        val recyclerView = binding.rvPinCode
        recyclerView.apply {
            adapter = pinCodeAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

}