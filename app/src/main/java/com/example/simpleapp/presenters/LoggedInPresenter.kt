package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.LoggedInContract
import com.example.simpleapp.models.PinModel

class LoggedInPresenter(
    private val view: LoggedInContract.View,
    pinModel: PinModel
) : LoggedInContract.Presenter {
    init {
        val result = pinModel.calculateSumPinNumbers()
        view.showSumResult(result)
    }

    override fun onLogOutButtonClicked() {
        view.closeActivity()
    }
}