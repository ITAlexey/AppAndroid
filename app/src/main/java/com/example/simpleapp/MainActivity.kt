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
import com.example.simpleapp.adapter.PinCodeAdapter
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.helpers.PinState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinCodeAdapter: PinCodeAdapter
    private lateinit var sharedPreferences: SharedPreferences

    private var currentPinState = PinState.UNDEFINED
    private var permanentPin = ""
    private var confirmationPin = ""
    private var temporaryPin = ""


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
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            SHARED_PREF_FILE,
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun initAppState() {
        permanentPin = sharedPreferences.getString(PIN_CODE_KEY, "").toString()
        if (permanentPin.isNotEmpty()) {
            binding.btnReset.visibility = View.VISIBLE
            updatePinState(PinState.ENTER)
        } else {
            binding.tvTitle.text = resources.getString(R.string.title_create)
            updatePinState(PinState.CREATE)
        }
    }

    private fun initListeners() {
        binding.apply {
            btnReset.setOnClickListener { resetPin() }
            tvNumber0.setOnClickListener { onNumberClicked(0, it) }
            tvNumber1.setOnClickListener { onNumberClicked(1, it) }
            tvNumber2.setOnClickListener { onNumberClicked(2, it) }
            tvNumber3.setOnClickListener { onNumberClicked(3, it) }
            tvNumber4.setOnClickListener { onNumberClicked(4, it) }
            tvNumber5.setOnClickListener { onNumberClicked(5, it) }
            tvNumber6.setOnClickListener { onNumberClicked(6, it) }
            tvNumber7.setOnClickListener { onNumberClicked(7, it) }
            tvNumber8.setOnClickListener { onNumberClicked(8, it) }
            tvNumber9.setOnClickListener { onNumberClicked(9, it) }
            imgBackSpace.setOnClickListener { removeNumber(it) }
            imgLogOut.setOnClickListener { onLogOutButtonClicked() }
            imgFingerPrint.setOnClickListener { showMessage(R.string.popup_finger_warning) }
        }
    }

    private fun onLogOutButtonClicked() {
        updateViewAppearance()
        updatePinState(PinState.ENTER)
        clearPinCodeField()
    }

    private fun clearPinCodeField() {
        temporaryPin = ""
        pinCodeAdapter.updateState(temporaryPin.length)
    }

    private fun resetPin() {
        if (currentPinState == PinState.ENTER) {
            updateViewAppearance(
                buttonResetVisibility = View.GONE,
                buttonLogOutVisibility = View.GONE,
                id = R.string.title_confirm
            )
            updatePinState(PinState.ENTER)
            clearPinCodeField()
        }
    }

    private fun applyAnimation(item: View) {
        item.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
    }

    private fun onNumberClicked(number: Int, item: View) {
        if (currentPinState != PinState.LOGIN) {
            applyAnimation(item)
            addNumber(number)
        }
    }

    private fun processEnterPin() {
        when (currentPinState) {
            PinState.RESET -> deletePinIfPossible()
            PinState.ENTER -> loginIfSuccess()
            PinState.CONFIRM -> savePinIfSuccess()
            PinState.CREATE -> createPin()
            else -> Unit
        }
        clearPinCodeField()
    }

    private fun deletePinIfPossible() {
        if (temporaryPin == permanentPin) {
            removeSavedPin()
        } else
            showMessage(R.string.popup_different)
    }

    private fun loginIfSuccess() {
        if (temporaryPin == permanentPin) {
            updateViewAppearance(
                buttonLogOutVisibility = View.GONE,
                buttonResetVisibility = View.VISIBLE,
                id = R.string.title_logged_in
            )
            updatePinState(PinState.LOGIN)
            showMessage(R.string.popup_success_enter)
        } else
            showMessage(R.string.popup_fail_enter)
    }

    private fun removeSavedPin() {
        sharedPreferences.edit().remove(PIN_CODE_KEY).apply()
        showMessage(R.string.popup_reset)
        updateViewAppearance(buttonLogOutVisibility = View.GONE)
        updatePinState(PinState.CREATE)
    }

    private fun updateViewAppearance(
        buttonResetVisibility: Int = View.VISIBLE,
        buttonLogOutVisibility: Int = View.GONE,
        id: Int = R.string.title
    ) {
        binding.apply {
            btnReset.visibility = buttonResetVisibility
            imgLogOut.visibility = buttonLogOutVisibility
            tvTitle.text = resources.getString(id)
        }
    }

    private fun updatePinState(state: PinState) {
        currentPinState = state
    }

    private fun savePinIfSuccess() {
        if (confirmationPin == temporaryPin) {
            updateViewAppearance()
            updatePinState(PinState.ENTER)
            sharedPreferences
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
        updateViewAppearance(buttonResetVisibility = View.GONE, id = R.string.title_repeat)
        updatePinState(PinState.CONFIRM)
    }

    private fun showMessage(id: Int) =
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()

    private fun addNumber(number: Int) {
        if (temporaryPin.length < pinCodeAdapter.itemCount) {
            temporaryPin += number.toString()
            pinCodeAdapter.updateState(temporaryPin.length)
        }
        if (temporaryPin.length == PIN_SIZE)
            processEnterPin()
    }

    private fun removeNumber(item: View) {
        applyAnimation(item)
        if (temporaryPin.isNotEmpty()) {
            temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
            pinCodeAdapter.updateState(temporaryPin.length)
        }
    }

    private fun initAdapter() {
        pinCodeAdapter = PinCodeAdapter(temporaryPin.length)
        val recyclerView = binding.rvPinCode
        recyclerView.apply {
            adapter = pinCodeAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val SHARED_PREF_FILE = "PINCODE"
        private const val PIN_CODE_KEY = "pincode"
        const val PIN_SIZE = 4
    }

}