package com.example.simpleapp.contracts

interface LogInActivityContract {
    interface Presenter: BasePresenter {
        fun onLogOutButtonClicked()
    }

    interface View: BaseView {
        fun showSumResult(sumResult: String)
    }
}