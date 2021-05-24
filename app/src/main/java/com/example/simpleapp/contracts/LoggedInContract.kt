package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface LoggedInContract {
    interface View : BaseView {
        fun moveToPinCodeFragment()
        fun showPinSumResult()
    }

    interface Presenter : BasePresenter<View> {
        fun onLogOutButtonClicked()
    }
}