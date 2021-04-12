package com.example.simpleapp.contracts

import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface LogInActivityContract {
    interface Presenter: BasePresenter<View> {
        fun onLogOutButtonClicked(activity: AppCompatActivity)
    }

    interface View: BaseView {
        fun showSumResult(sumResult: String)
    }
}