package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.WelcomeActivityContract
import com.example.simpleapp.simpleapp.PinModel

class WelcomeActivityPresenter(
    private val view: WelcomeActivityContract.View,
    pinModel: PinModel
) : WelcomeActivityContract.Presenter {

    init {
        val result = pinModel.calculateSumPinNumbers()
        view.showSumResult(result)
    }
    override fun onLogOutButtonClicked() {
        view.closeActivity()
    }
}