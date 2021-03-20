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
import com.example.simpleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val adapter: PinCodeAdapter = PinCodeAdapter(4, 0)
    private lateinit var binding: ActivityMainBinding
    private lateinit var masterKeyAlias: String
    private lateinit var appStore: SharedPreferences
    private lateinit var setUppedPin: String
    private var pinSetUpState = false
    private var isSetUpBtnClicked = false
    private var isResetBtnClicked = false
    private var isConfirmationPinCodeState = false
    private lateinit var confirmationPin: String
    private var temporaryPin = ""

    companion object {
        const val SHARED_PREF_FILE = "PINCODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEncryptedSharedPref()
        initStateOfApp()
        initRv()
        initListeners()
    }

    private fun initEncryptedSharedPref() {
        masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        appStore = EncryptedSharedPreferences.create("PinCode",
            masterKeyAlias,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    private fun initStateOfApp() {
        setUppedPin = appStore.getString(SHARED_PREF_FILE, "").toString()
        pinSetUpState = setUppedPin.isNotEmpty()
        if (pinSetUpState)
            binding.setupBtn.visibility = View.GONE
    }

    private fun initListeners() {
        binding.resetBtn.setOnClickListener { resetPinCode(it) }
        binding.setupBtn.setOnClickListener { setUpPinCode(it) }
        binding.number0.setOnClickListener { applyActions(0, it) }
        binding.number1.setOnClickListener { applyActions(1, it) }
        binding.number2.setOnClickListener { applyActions(2, it) }
        binding.number3.setOnClickListener { applyActions(3, it) }
        binding.number4.setOnClickListener { applyActions(4, it) }
        binding.number5.setOnClickListener { applyActions(5, it) }
        binding.number6.setOnClickListener { applyActions(6, it) }
        binding.number7.setOnClickListener { applyActions(7, it) }
        binding.number8.setOnClickListener { applyActions(8, it) }
        binding.number9.setOnClickListener { applyActions(9, it) }
        binding.acceptArrow.setOnClickListener { checkPinCode() }
        binding.backspaceImg.setOnClickListener { removeNumber(it) }
    }

    private fun clearPinCodeField() {
        binding.acceptArrow.visibility = View.INVISIBLE
        temporaryPin = ""
        adapter.updateState(temporaryPin.length)
    }

    private fun resetPinCode(btn: View) {
       if (pinSetUpState) {
           btn.visibility = View.GONE
           binding.infoMsg.text = resources.getString(R.string.info_msg_confirm)
           clearPinCodeField()
           isResetBtnClicked = true
       } else
           showMessage(R.string.popup_never_setup)
    }

    private fun applyActions(number: Int, item: View) {
        item.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
        addNumber(number)
    }

    private fun setUpPinCode(btn: View) {
        clearPinCodeField()
        btn.visibility = View.GONE
        binding.resetBtn.visibility = View.INVISIBLE
        binding.infoMsg.text = resources.getString(R.string.info_msg_create)
        isSetUpBtnClicked = true
    }


    private fun checkPinCode() {
        binding.acceptArrow.visibility = View.INVISIBLE
        when {
            isResetBtnClicked -> if (temporaryPin == setUppedPin) removeSetUppedPin()
                                else showMessage(R.string.popup_different)
            pinSetUpState -> if (temporaryPin == setUppedPin) showMessage(R.string.popup_success_enter)
                            else showMessage(R.string.popup_fail_enter)
            isConfirmationPinCodeState -> recordPermanentPinCode()
            isSetUpBtnClicked -> confirmEnteredPinCode()
            else -> showMessage(R.string.popup_miss_setup)
        }
        clearPinCodeField()
    }

    private fun removeSetUppedPin() {
        appStore.edit().remove(SHARED_PREF_FILE).apply()
        showMessage(R.string.popup_reset)
        refreshActivity()
    }

    private fun refreshActivity() {
        pinSetUpState = false
        isResetBtnClicked = false
        isConfirmationPinCodeState = false
        isSetUpBtnClicked = false
        binding.setupBtn.visibility = View.VISIBLE
        binding.resetBtn.visibility = View.VISIBLE
        binding.infoMsg.text = resources.getString(R.string.info_msg)
    }

    private fun recordPermanentPinCode() {
        if (confirmationPin == temporaryPin) {
            binding.infoMsg.text = resources.getString(R.string.info_msg)
            appStore
                .edit()
                .putString(SHARED_PREF_FILE, confirmationPin)
                .apply()
            setUppedPin = confirmationPin
            pinSetUpState = true
            binding.resetBtn.visibility = View.VISIBLE
            showMessage(R.string.popup_record)
        } else {
            showMessage(R.string.popup_different)
        }
    }

    private fun confirmEnteredPinCode() {
        confirmationPin = temporaryPin
        isConfirmationPinCodeState = true
        binding.infoMsg.text = resources.getString(R.string.info_msg_repeat)
    }

    private fun showMessage(id: Int) {
        Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()
    }

    private fun addNumber(number: Int) {
        if (temporaryPin.length == adapter.itemCount - 1)
            binding.acceptArrow.visibility = View.VISIBLE
        if (temporaryPin.length < adapter.itemCount) {
            temporaryPin += number.toString()
            adapter.updateState(temporaryPin.length)
        }
    }

    private fun removeNumber(item: View) {
        binding.acceptArrow.visibility = View.INVISIBLE
        item.startAnimation(
            AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked)
        )
        if (temporaryPin.isNotEmpty()) {
            temporaryPin = temporaryPin.substring(0, temporaryPin.length - 1)
            adapter.updateState(temporaryPin.length)
        }
    }

    private fun initRv() {
        val recyclerView = binding.rvPinCode
        recyclerView.addOnItemTouchListener(RecyclerVIewDisabler())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }

}