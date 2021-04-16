package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.LogInActivityContract
import com.example.simpleapp.models.PinModel

class LogInActivityPresenter(
    private val pinModel: PinModel
) :
    LogInActivityContract.Presenter {
    private var view: LogInActivityContract.View? = null

    override fun onLogOutButtonClicked() {
        view?.closeActivity()
    }

    override fun subscribe(view: LogInActivityContract.View) {
        this.view = view
        view.showSumResult(pinModel.calculateSumPinNumbers())
    }

    override fun unsubscribe() {
        view = null
    }

}