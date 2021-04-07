package com.example.simpleapp.presenters

import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.PinState

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val pinModel: PinModel
) :
    MainActivityContract.Presenter {

    override fun onViewCreated() {
        if (pinModel.currentPinState == PinState.LOGOUT) {
            view.showResetButton()
        } else {
            view.modifyTitleText(R.string.title_create)
        }
    }

    override fun onNumberButtonClicked(number: Int) {
        if (pinModel.temporaryPin.length < PIN_SIZE) {
            pinModel.addNumber(number)
            view.showBackspaceButton()
            if (pinModel.temporaryPin.length == PIN_SIZE) {
                pinModel.processPinField()
                selectTitleText(pinModel.currentPinState)
                view.hideBackspaceButton()
            }
            view.updatePinField(pinModel.temporaryPin.length)
        }
    }

    private fun selectTitleText(pinState: PinState) {
        when (pinState) {
            PinState.CREATE -> view.modifyTitleText(R.string.title_create)
            PinState.CONFIRM -> view.modifyTitleText(R.string.title_confirm)
            PinState.LOGOUT -> view.apply {
                modifyTitleText(R.string.title_main)
                showResetButton()
            }
            PinState.RESET -> Unit
            PinState.LOGIN -> view.showLogInActivity()
        }
    }

    override fun onResetButtonClicked() {
        pinModel.updatePinState(PinState.RESET)
        pinModel.resetTemporaryPin()
        view.updatePinField()
        view.hideResetButton()
        view.modifyTitleText(R.string.title_repeat)
    }

    override fun onBackspaceButtonClicked() {
        if (pinModel.temporaryPin.isNotEmpty()) {
            pinModel.removeNumber()
            view.updatePinField(pinModel.temporaryPin.length)
        }
        if (pinModel.temporaryPin.isEmpty()) {
            view.hideBackspaceButton()
        }
    }

}