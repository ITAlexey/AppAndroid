package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.LoggedInContract

class LoggedInPresenter(
    private val view: LoggedInContract.View,
) : LoggedInContract.Presenter {

    override fun onViewCreated() =
        view.showPinSumResult()

    override fun onLogOutButtonClicked() =
        view.moveToPinCodeFragment()
}