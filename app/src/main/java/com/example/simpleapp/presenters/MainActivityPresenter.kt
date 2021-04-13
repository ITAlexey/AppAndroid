package com.example.simpleapp.presenters

import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.R
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.utils.PinState

class MainActivityPresenter(
    private val pinModel: PinModel
) :
    MainActivityContract.Presenter {
    private var view: MainActivityContract.View? = null
    private lateinit var currentPinState: PinState

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
            PinState.CONFIRM -> view?.setTitleText(R.string.title_reset)
            PinState.LOGOUT -> view?.apply {
                setTitleText(R.string.title_logout)
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
        view?.setTitleText(R.string.title_confirm)
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

    override fun subscribe(view: MainActivityContract.View, pinState: PinState) {
        this.view = view
        currentPinState = pinState
        view.setTitleText(pinState.titleTextId)
        if (pinState == PinState.LOGOUT) {
            view.showResetButton()
        }
        if (pinModel.temporaryPin.isNotEmpty()) {
            view.showBackspaceButton()
        }
    }

    override fun subscribe(view: MainActivityContract.View) = subscribe(view, PinState.CREATE)

    override fun getPinState(): PinState = currentPinState

    override fun createPinAdapter(): PinAdapter = PinAdapter(pinModel.temporaryPin.length, PIN_SIZE)

    override fun unsubscribe() {
        view = null
    }

}