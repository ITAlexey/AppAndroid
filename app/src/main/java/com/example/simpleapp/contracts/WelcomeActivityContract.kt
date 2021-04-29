package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface WelcomeActivityContract {
    interface Presenter: BasePresenter<View> {
        fun onLogOutButtonClicked()
    }

    interface View: BaseView {
        fun showSumResult(sumResult: String)
        fun closeActivity()
    }
}