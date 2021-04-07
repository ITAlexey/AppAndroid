package com.example.simpleapp.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.BaseApp
import com.example.simpleapp.contracts.LogInActivityContract
import com.example.simpleapp.databinding.ActivityLogInBinding
import com.example.simpleapp.presenters.LogInActivityPresenter

class LogInActivity : AppCompatActivity(), LogInActivityContract.View {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var presenter: LogInActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model = (application as BaseApp).pinModel
        presenter = LogInActivityPresenter(this, model)
        presenter.onViewCreated()
        initListeners()
    }

    private fun initListeners() {
       binding.imgLogOut.setOnClickListener{presenter.onLogOutButtonClicked(this)}
    }

    override fun showSumResult(sumResult: String) {
        binding.tvSumResult.text = sumResult
    }
}