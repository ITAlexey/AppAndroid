package com.example.simpleapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.BaseApp
import com.example.simpleapp.Constants.TAG
import com.example.simpleapp.contracts.LogInActivityContract
import com.example.simpleapp.databinding.ActivityLogInBinding
import com.example.simpleapp.presenters.LogInActivityPresenter

class LogInActivity : AppCompatActivity(), LogInActivityContract.View {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var presenter: LogInActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Log onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LogInActivityPresenter((applicationContext as BaseApp).pinModel)
        initListeners()
    }

    override fun onStart() {
        Log.d(TAG, "Log onStart")
        presenter.subscribe(this)
        super.onStart()
    }

    private fun initListeners() {
       binding.imgLogOut.setOnClickListener{presenter.onLogOutButtonClicked()}
    }

    override fun showSumResult(sumResult: String) {
        binding.tvSumResult.text = sumResult
    }

    override fun closeActivity() {
        Log.d(TAG, "Log closeActivity")
        finish()
    }

    override fun onStop() {
        Log.d(TAG, "Log onStop")
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Log onDestroy")
    }

    companion object {
        fun creteIntent(packageContext: Context) : Intent {
            return Intent(packageContext, LogInActivity::class.java)
        }
    }
}