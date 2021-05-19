package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

class LoggedInContract {
    interface View: BaseView {
        fun showSumResult(sumResult: String)

        fun closeActivity()
    }

    interface Presenter: BasePresenter<View> {
        fun onLogOutButtonClicked()
    }
}