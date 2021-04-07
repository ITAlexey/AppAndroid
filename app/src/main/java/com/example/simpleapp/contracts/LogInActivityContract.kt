package com.example.simpleapp.contracts

import androidx.appcompat.app.AppCompatActivity

interface LogInActivityContract {
    interface Presenter: BasePresenter {
        fun onLogOutButtonClicked(activity: AppCompatActivity)
    }

    interface View: BaseView {
        fun showSumResult(sumResult: String)
    }
}