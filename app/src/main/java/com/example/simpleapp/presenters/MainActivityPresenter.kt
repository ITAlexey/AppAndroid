package com.example.simpleapp.presenters

import android.os.Bundle
import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.utils.PinState

class MainActivityPresenter(
    private val pinModel: PinModel
) :
    MainActivityContract.Presenter {
    private var view: MainActivityContract.View? = null

    override fun onNumberButtonClicked(number: Int) {
        if (pinModel.temporaryPin.length < PIN_SIZE) {
            pinModel.addNumber(number)
            view?.showBackspaceButton()
            if (pinModel.temporaryPin.length == PIN_SIZE) {
                pinModel.processPinField()
                selectTitleText(pinModel.currentPinState)
                view?.hideBackspaceButton()
            }
            view?.updatePinField(pinModel.temporaryPin.length)
        }
    }

    private fun selectTitleText(pinState: PinState) {
        when (pinState) {
            PinState.CREATE -> view?.setTitleText(R.string.title_create)
            PinState.CONFIRM -> view?.setTitleText(R.string.title_confirm)
            PinState.LOGOUT -> view?.apply {
                setTitleText(R.string.title_main)
                showResetButton()
            }
            PinState.RESET -> Unit
            PinState.LOGIN -> view?.showLogInActivity()
        }
    }

    override fun onResetButtonClicked() {
        pinModel.updatePinState(PinState.RESET)
        pinModel.resetTemporaryPin()
        view?.updatePinField()
        view?.hideResetButton()
        view?.setTitleText(R.string.title_repeat)
    }

    override fun onBackspaceButtonClicked() {
        if (pinModel.temporaryPin.isNotEmpty()) {
            pinModel.removeNumber()
            view?.updatePinField(pinModel.temporaryPin.length)
        }
        if (pinModel.temporaryPin.isEmpty()) {
            view?.hideBackspaceButton()
        }
    }

    override fun subscribe(view: MainActivityContract.View, state: Bundle?) {
        // title (depends on pin code's state)
        // reset button (also depends on pin code's state)
        // fullness of pin code (depends on bundle's state)
        // backspace button (depends on fullness of pin code or length of temporary pin)
        this.view = view

    }

    override fun subscribe(view: MainActivityContract.View) = subscribe(view, null)

    override fun unsubscribe() {
        view = null
    }

}