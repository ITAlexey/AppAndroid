package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.LoggedInContract
import com.example.simpleapp.models.pincode.PinModel

class LoggedInPresenter(
    private val view: LoggedInContract.View,
    pinModel: PinModel
) : LoggedInContract.Presenter {

    init {
        view.showPinSumResult()
    }

    override fun onLogOutButtonClicked() {
        view.moveToPinCodeFragment()
    }
}