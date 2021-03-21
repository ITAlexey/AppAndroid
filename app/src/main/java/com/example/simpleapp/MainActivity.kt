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
            binding.btnSetup.visibility = View.GONE
            PinState.SETUP
        } else {
           PinState.NONE
        }
    }

    private fun initListeners() {
        binding.apply {
            btnReset.setOnClickListener { resetPinCode(it) }
            btnSetup.setOnClickListener { setUpPinCode(it) }
            tvNbr0.setOnClickListener { configureNbrView(0, it) }
            tvNbr1.setOnClickListener { configureNbrView(1, it) }
            tvNbr2.setOnClickListener { configureNbrView(2, it) }
            tvNbr3.setOnClickListener { configureNbrView(3, it) }
            tvNbr4.setOnClickListener { configureNbrView(4, it) }
            tvNbr5.setOnClickListener { configureNbrView(5, it) }
            tvNbr6.setOnClickListener { configureNbrView(6, it) }
            tvNbr7.setOnClickListener { configureNbrView(7, it) }
            tvNbr8.setOnClickListener { configureNbrView(8, it) }
            tvNbr9.setOnClickListener { configureNbrView(9, it) }
            acceptArrow.setOnClickListener { checkPinCode() }
            backspaceImg.setOnClickListener { removeNumber(it) }
        }
    }

    private fun clearPinCodeField() {
        binding.acceptArrow.visibility = View.INVISIBLE
        temporaryPin = ""
        pinCodeAdapter.updateState(temporaryPin.length)
    }

    private fun resetPinCode(btn: View) {
       if (currentPinState == PinState.SETUP || currentPinState == PinState.LOGIN) {
           btn.visibility = View.GONE
           binding.tvTitle.text = resources.getString(R.string.info_msg_confirm)
           currentPinState = PinState.RESET
           clearPinCodeField()
       } else
           showMessage(R.string.popup_never_setup)
    }

    private fun configureNbrView(number: Int, item: View) {
        item.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
        addNumber(number)
    }

    private fun setUpPinCode(btn: View) {
        clearPinCodeField()
        btn.visibility = View.GONE
        binding.btnReset.visibility = View.INVISIBLE
        binding.tvTitle.text = resources.getString(R.string.info_msg_create)
        currentPinState = PinState.CREATE
    }


    private fun checkPinCode() {
        binding.acceptArrow.visibility = View.INVISIBLE
        when (currentPinState) {
            PinState.LOGIN -> showMessage(R.string.popup_logged_in)
            PinState.RESET ->
                if (temporaryPin == permanentPin) {
                    removeSetUppedPin()
                } else
                    showMessage(R.string.popup_different)
            PinState.SETUP ->
                if (temporaryPin == permanentPin) {
                    currentPinState = PinState.LOGIN
                    showMessage(R.string.popup_success_enter)
                } else
                    showMessage(R.string.popup_fail_enter)
            PinState.CONFIRM -> recordPermanentPin()
            PinState.CREATE -> createPin()
            else -> showMessage(R.string.popup_miss_setup)
        }
        clearPinCodeField()
    }

    private fun removeSetUppedPin() {
        appStore.edit().remove(PIN_CODE_KEY).apply()
        showMessage(R.string.popup_reset)
        currentPinState = PinState.NONE
        updateView()
    }

    private fun updateView() {
        binding.btnSetup.visibility = View.VISIBLE
        binding.btnReset.visibility = View.VISIBLE
        binding.tvTitle.text = resources.getString(R.string.info_msg)
    }

    private fun recordPermanentPin() {
        if (confirmationPin == temporaryPin) {
            binding.tvTitle.text = resources.getString(R.string.info_msg)
            appStore
                .edit()
                .putString(PIN_CODE_KEY, confirmationPin)
                .apply()
            permanentPin = confirmationPin
            binding.btnReset.visibility = View.VISIBLE
            currentPinState = PinState.SETUP
            showMessage(R.string.popup_record)
        } else {
            showMessage(R.string.popup_different)
        }
    }

    private fun createPin() {
        confirmationPin = temporaryPin
        binding.tvTitle.text = resources.getString(R.string.info_msg_repeat)
        currentPinState = PinState.CONFIRM
    }

    private fun showMessage(id: Int) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
    }

    private fun addNumber(number: Int) {
        if (temporaryPin.length == pinCodeAdapter.itemCount - 1)
            binding.acceptArrow.visibility = View.VISIBLE
        if (temporaryPin.length < pinCodeAdapter.itemCount) {
            temporaryPin += number.toString()
            pinCodeAdapter.updateState(temporaryPin.length)
        }
    }

    private fun removeNumber(item: View) {
        binding.acceptArrow.visibility = View.INVISIBLE
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