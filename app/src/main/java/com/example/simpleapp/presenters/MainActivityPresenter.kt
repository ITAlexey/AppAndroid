package com.example.simpleapp.presenters

import com.example.simpleapp.Constants.PIN_SIZE
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.utils.PinState

class MainActivityPresenter(
    private val pinModel: PinModel
) :
    MainActivityContract.Presenter {
    private var view: MainActivityContract.View? = null
    private var currentPinState: PinState? = null

    override fun onNumberButtonClicked(number: Int) {
        if (!pinModel.isTemporaryPinFull()) {
            pinModel.addNumber(number)
            view?.showOrHideBackspaceButton(true)
            if (pinModel.isTemporaryPinFull()) {
                processPin()
                view?.showOrHideBackspaceButton(false)
            }
            view?.updatePinField(pinModel.temporaryPin.length)
        }
    }

    private fun processPin() {
        val processResult: (Boolean) -> Unit = { success ->
            if (success) {
                currentPinState = currentPinState!!.nextState()
                currentPinState!!.modifyViewAppearance(view)
                currentPinState!!.showSuccessMessage(view)
            } else {
                currentPinState!!.showFailMessage(view)
            }
        }

        when (currentPinState) {
            PinState.CREATE -> pinModel.createPinIfSuccess(processResult)
            PinState.CONFIRM -> pinModel.savePinIfSuccess(processResult)
            PinState.LOGOUT -> pinModel.loginIfSuccess(processResult)
            PinState.RESET -> pinModel.deletePinIfSuccess(processResult)
            PinState.LOGIN -> Unit
        }
        pinModel.resetTemporaryPin()
    }

    override fun onResetButtonClicked() {
        pinModel.resetTemporaryPin()
        currentPinState = PinState.RESET
        view?.updatePinField()
        currentPinState!!.modifyViewAppearance(view)
    }

    override fun onBackspaceButtonClicked() {
        if (!pinModel.isTemporaryPinEmpty()) {
            pinModel.removeNumber()
            view?.updatePinField(pinModel.temporaryPin.length)
        }
        if (pinModel.isTemporaryPinEmpty()) {
            view?.showOrHideBackspaceButton(false)
        }
    }

    override fun subscribe(view: MainActivityContract.View, pinState: PinState?) {
        this.view = view
        currentPinState =
            pinState ?: if (pinModel.isPinSaved()) PinState.LOGOUT else PinState.CREATE
        if (currentPinState == PinState.LOGIN) {
            currentPinState = currentPinState!!.nextState()
        }
        currentPinState!!.modifyViewAppearance(this.view)
        if (pinModel.temporaryPin.isNotEmpty()) {
            view.showOrHideBackspaceButton(true)
        }
    }

    override fun subscribe(view: MainActivityContract.View) = subscribe(view, null)

    override fun getPinState(): PinState? = currentPinState

    override fun createPinAdapter(): PinAdapter = PinAdapter(pinModel.temporaryPin.length, PIN_SIZE)

    override fun unsubscribe() {
       view = null
    }
}