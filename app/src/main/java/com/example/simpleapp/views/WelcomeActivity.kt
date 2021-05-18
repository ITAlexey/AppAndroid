package com.example.simpleapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.BaseApp
import com.example.simpleapp.contracts.WelcomeActivityContract
import com.example.simpleapp.databinding.ActivityWelcomeBinding
import com.example.simpleapp.presenters.WelcomeActivityPresenter

class WelcomeActivity : AppCompatActivity(), WelcomeActivityContract.View {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var presenter: WelcomeActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initListeners()
    }

    private fun initPresenter() {
        val model = (applicationContext as BaseApp).pinModel
        presenter = WelcomeActivityPresenter(this, model)
    }

    private fun initListeners() {
       binding.imgLogOut.setOnClickListener{presenter.onLogOutButtonClicked()}
    }

    override fun showSumResult(sumResult: String) {
        binding.tvSumResult.text = sumResult
    }

    override fun closeActivity() {
        finish()
    }

    companion object {
        fun creteIntent(packageContext: Context) : Intent {
            return Intent(packageContext, WelcomeActivity::class.java)
        }
    }
}