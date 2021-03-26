package com.example.simpleapp

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.simpleapp.adapter.PinCodeAdapter
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.helpers.PinState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pinCodeAdapter: PinCodeAdapter
    private lateinit var sharedPreferences: SharedPreferences

    private var currentPinState = PinState.WAITED
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
        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        sharedPreferences = EncryptedSharedPreferences.create(
            this,
            SHARED_PREF_FILE,
            masterKey,
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
            btnReset.setOnClickListener { onResetButtonClicked() }
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
            imgBackSpace.setOnClickListener { onBackSpaceButtonClicked(it) }
            imgLogOut.setOnClickListener { onLogOutButtonClicked() }
        }
    }

    private fun onLogOutButtonClicked() {
        updateViewAppearance()
        updatePinState(PinState.ENTER)
        updatePinField()
    }

    private fun updatePinField() {
        temporaryPin = ""
        pinCodeAdapter.updateState(temporaryPin.length)
    }

    private fun onResetButtonClicked() {
        if (currentPinState == PinState.ENTER) {
            updateViewAppearance(
                buttonResetVisibility = View.GONE,
                buttonLogOutVisibility = View.GONE,
                titleTextResId = R.string.title_confirm
            )
            updatePinState(PinState.RESET)
            updatePinField()
        }
    }

    private fun applyAnimationOnPinView(item: View) =
        item.startAnimation(AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked))

    private fun onNumberClicked(number: Int, item: View) {
        if (currentPinState != PinState.LOGIN) {
            applyAnimationOnPinView(item)
            addNumber(number)
            updateBackSpaceButtonAppearance()
        }
    }

    private fun updateBackSpaceButtonAppearance() {
        binding.imgBackSpace.visibility = if (temporaryPin.isNotEmpty()) {
            View.VISIBLE
        } else {
            View.INVISIBLE
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
        updatePinField()
    }

    private fun deletePinIfPossible() {
        if (temporaryPin == permanentPin) {
            removeSavedPin()
        } else {
            showMessage(R.string.popup_different)
        }
    }


    private fun loginIfSuccess() {
        if (temporaryPin == permanentPin) {
            updateViewAppearance(
                buttonResetVisibility = View.GONE,
                buttonLogOutVisibility = View.VISIBLE,
                titleTextResId = R.string.title_logged_in
            )
            updatePinState(PinState.LOGIN)
            showMessage(R.string.popup_success_enter)
        } else {
            showMessage(R.string.popup_fail_enter)
        }
    }

    private fun removeSavedPin() {
        sharedPreferences.edit().remove(PIN_CODE_KEY).apply()
        showMessage(R.string.popup_reset)
        updateViewAppearance(
            buttonResetVisibility = View.GONE,
            titleTextResId = R.string.title_create
        )
        updatePinState(PinState.CREATE)
    }

    private fun updateViewAppearance(
        buttonResetVisibility: Int = View.VISIBLE,
        buttonLogOutVisibility: Int = View.GONE,
        @StringRes
        titleTextResId: Int = R.string.title
    ) {
        binding.apply {
            btnReset.visibility = buttonResetVisibility
            imgLogOut.visibility = buttonLogOutVisibility
            tvTitle.text = resources.getString(titleTextResId)
        }
    }

    private fun updatePinState(state: PinState) {
        currentPinState = state
    }

    private fun savePinIfSuccess() {
        if (confirmationPin == temporaryPin) {
            updateViewAppearance()
            updatePinState(PinState.ENTER)
            saveToSharedPref()
            permanentPin = confirmationPin
            showMessage(R.string.popup_record)
        } else {
            showMessage(R.string.popup_different)
        }
    }

    private fun saveToSharedPref() =
        sharedPreferences
            .edit()
            .putString(PIN_CODE_KEY, confirmationPin)
            .apply()

    private fun createPin() {
        confirmationPin = temporaryPin
        updateViewAppearance(
            buttonResetVisibility = View.GONE,
            titleTextResId = R.string.title_repeat
        )
        updatePinState(PinState.CONFIRM)
    }

    private fun showMessage(@StringRes popupTextResId: Int) =
        Toast.makeText(this, popupTextResId, Toast.LENGTH_SHORT).show()

    private fun addNumber(number: Int) {
        if (temporaryPin.length < pinCodeAdapter.itemCount) {
            temporaryPin += number.toString()
            pinCodeAdapter.updateState(temporaryPin.length)
        }
        if (temporaryPin.length == PIN_SIZE) {
            processEnterPin()
        }
    }

    private fun onBackSpaceButtonClicked(item: View) {
        applyAnimationOnPinView(item)
        removeNumber()
        updateBackSpaceButtonAppearance()
    }

    private fun removeNumber() {
        if (temporaryPin.isNotEmpty()) {
            temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
            pinCodeAdapter.updateState(temporaryPin.length)
        }
    }

    private fun initAdapter() {
        pinCodeAdapter = PinCodeAdapter(temporaryPin.length, PIN_SIZE)
        val recyclerView = binding.rvPinCode
        recyclerView.apply {
            adapter = pinCodeAdapter
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val SHARED_PREF_FILE = "PINCODE"
        private const val PIN_CODE_KEY = "pincode"
        private const val PIN_SIZE = 4
    }

}