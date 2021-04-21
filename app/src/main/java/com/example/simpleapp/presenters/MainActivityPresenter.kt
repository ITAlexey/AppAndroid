package com.example.simpleapp.presenters

import androidx.annotation.StringRes
import com.example.simpleapp.R
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
        if (!pinModel.isPinFull()) {
            pinModel.addNumber(number)
            view?.updateVisibilityBackspaceButton(isVisible = true)
            if (pinModel.isPinFull()) {
                processPin()
                view?.updateVisibilityBackspaceButton(isVisible = false)
            }
            view?.updatePinField(pinModel.getPinLength())
        }
    }

    private fun processPin() {
        var isSuccess = false
        when (currentPinState) {
            PinState.CREATE -> isSuccess = pinModel.createPinIfSuccess()
            PinState.CONFIRM -> isSuccess = pinModel.savePinIfSuccess()
            PinState.LOGOUT -> isSuccess = pinModel.loginIfSuccess()
            PinState.RESET -> isSuccess = pinModel.deletePinIfSuccess()
            PinState.LOGIN -> Unit
        }
        processResult(isSuccess)
        pinModel.resetPin()
    }

    private fun processResult(isSuccess: Boolean) {
        if (isSuccess) {
            processSuccessMessage()
            currentPinState = currentPinState.nextState()
            modifyViewsAppearance()
        } else {
            processFailMessage()
        }
    }

    private fun modifyViewsAppearance() {
        when (currentPinState) {
            PinState.CREATE -> modifyAppearance(R.string.title_create, isVisible = false)
            PinState.CONFIRM ->modifyAppearance(R.string.title_confirm, isVisible = false)
            PinState.LOGOUT ->modifyAppearance(R.string.title_logout, isVisible = true)
            PinState.RESET ->modifyAppearance(R.string.title_reset, isVisible = false)
            PinState.LOGIN -> view?.moveToLogInActivity()
        }
    }

    private fun modifyAppearance(@StringRes titleTextResId: Int, isVisible: Boolean) {
        view?.setTitleText(titleTextResId)
        view?.updateVisibilityResetButton(isVisible)
    }

    private fun processFailMessage() {
        when (currentPinState) {
            PinState.CREATE -> view?.showPopupMessage(R.string.popup_simple)
            PinState.CONFIRM, PinState.RESET ->
                view?.showPopupMessage(R.string.popup_different)
            PinState.LOGOUT -> view?.showPopupMessage(R.string.popup_fail)
            PinState.LOGIN -> Unit
        }
    }

    private fun processSuccessMessage() {
        when (currentPinState) {
            PinState.RESET -> view?.showPopupMessage(R.string.popup_reset)
            PinState.CONFIRM -> view?.showPopupMessage(R.string.popup_saved)
            PinState.LOGOUT, PinState.LOGIN, PinState.CREATE -> Unit
        }
    }

    override fun onResetButtonClicked() {
        pinModel.resetPin()
        currentPinState = PinState.RESET
        view?.updatePinField()
        modifyViewsAppearance()
    }

    override fun onBackspaceButtonClicked() {
        if (pinModel.isPinNotEmpty()) {
            pinModel.removeNumber()
            view?.updatePinField(pinModel.getPinLength())
        }
        if (pinModel.isPinEmpty()) {
            view?.updateVisibilityBackspaceButton(isVisible = false)
        }
    }

    override fun subscribe(view: MainActivityContract.View, pinState: PinState?) {
        this.view = view
        currentPinState = definePinState(pinState)
        modifyViewsAppearance()
        if (pinModel.isPinNotEmpty()) {
            view.updateVisibilityBackspaceButton(isVisible = true)
        }
        view.updatePinField(pinModel.getPinLength())
    }

    private fun definePinState(pinState: PinState?): PinState {
        var state = pinState ?: if (pinModel.isPinSaved()) {
            PinState.LOGOUT
        } else {
            PinState.CREATE
        }
        if (state == PinState.LOGIN) {
            state = state.nextState()
        }
        return state
    }

    override fun subscribe(view: MainActivityContract.View) =
        subscribe(view, null)

    override fun getPinState(): PinState =
        currentPinState

    override fun unsubscribe() {
        view = null
    }
}