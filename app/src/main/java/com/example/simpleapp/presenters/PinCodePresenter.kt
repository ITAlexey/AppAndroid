package com.example.simpleapp.presenters

import androidx.annotation.StringRes
import com.example.simpleapp.R
import com.example.simpleapp.contracts.PinCodeFragmentContract
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.pincode.PinState

class PinCodePresenter(
    private val view: PinCodeFragmentContract.View,
    private val pinModel: PinModel,
) : PinCodeFragmentContract.Presenter {

    override fun onViewCreated() {
        processPinState()
    }

    private fun processPinState() {
        val backspaceButtonVisibility = pinModel.isPinNotEmpty
        when (pinModel.pinState) {
            PinState.CREATE -> updateViewsAppearance(
                R.string.title_create,
                backspaceButtonVisibility
            )
            PinState.CONFIRM -> updateViewsAppearance(
                R.string.title_confirm,
                backspaceButtonVisibility
            )
            PinState.LOGOUT -> updateViewsAppearance(
                R.string.title_logout,
                backspaceButtonVisibility,
                resetButtonVisibility = true
            )
            PinState.RESET -> updateViewsAppearance(R.string.title_reset, backspaceButtonVisibility)
            PinState.LOGIN -> Unit
        }
    }

    private fun updateViewsAppearance(
        @StringRes titleTextResId: Int,
        backspaceVisibility: Boolean,
        resetButtonVisibility: Boolean = false
    ) {
        view.setTitleText(titleTextResId)
        view.updateVisibilityResetButton(resetButtonVisibility)
        view.updateVisibilityBackspaceButton(backspaceVisibility)
        view.updatePinField(pinModel.pinLength)
    }

    override fun onNumberButtonClicked(number: Int) {
        if (!pinModel.isPinFull) {
            pinModel.addNumber(number)
            processPinIfFull()
        }
    }

    private fun processPinIfFull() {
        if (pinModel.isPinFull) {
            val result = getProcessResult()
            pinModel.resetPin()
            processResult(result.first, result.second)
        } else {
            view.updateVisibilityBackspaceButton(pinModel.isPinNotEmpty)
            view.updatePinField(pinModel.pinLength)
        }
    }

    private fun getProcessResult(): Pair<Boolean, PinState> {
        return when (pinModel.pinState) {
            PinState.CREATE -> pinModel.createPinIfSuccess()
            PinState.CONFIRM -> pinModel.savePinIfSuccess()
            PinState.LOGOUT -> pinModel.loginIfSuccess()
            PinState.RESET -> pinModel.deletePinIfSuccess()
            PinState.LOGIN -> false to PinState.LOGOUT
        }
    }

    private fun processResult(isSuccess: Boolean, newPinState: PinState) {
        when {
            newPinState == PinState.LOGIN -> processLoginState()
            isSuccess -> processSuccessMessage(newPinState)
            else -> processFailMessage()
        }
    }

    private fun processLoginState() {
        pinModel.updatePinState(PinState.LOGOUT)
        val pinSumResult = pinModel.calculateSumPinNumbers()
        view.moveToLoggedInFragment(pinSumResult)
    }

    private fun processSuccessMessage(newPinState: PinState) {
        when (pinModel.pinState) {
            PinState.RESET -> view.showPopupMessage(R.string.popup_reset)
            PinState.CONFIRM -> view.showPopupMessage(R.string.popup_saved)
            PinState.LOGOUT, PinState.LOGIN, PinState.CREATE -> Unit
        }
        pinModel.updatePinState(newPinState)
        processPinState()
    }

    private fun processFailMessage() {
        when (pinModel.pinState) {
            PinState.CREATE -> view.showPopupMessage(R.string.popup_simple)
            PinState.CONFIRM, PinState.RESET ->
                view.showPopupMessage(R.string.popup_different)
            PinState.LOGOUT -> view.showPopupMessage(R.string.popup_fail)
            PinState.LOGIN -> Unit
        }
        processPinState()
    }

    override fun onResetButtonClicked() {
        pinModel.resetPin()
        pinModel.updatePinState(PinState.RESET)
        processPinState()
    }

    override fun onBackspaceButtonClicked() {
        if (pinModel.isPinNotEmpty) {
            pinModel.removeNumber()
            view.updatePinField(pinModel.pinLength)
        }
        if (pinModel.isPinEmpty) {
            view.updateVisibilityBackspaceButton(isVisible = false)
        }
    }
}