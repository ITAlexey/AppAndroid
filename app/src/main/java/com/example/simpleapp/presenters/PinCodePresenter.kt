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

    init {
        modifyViewsAppearance()
        if (pinModel.isPinNotEmpty) {
            view.updateVisibilityBackspaceButton(isVisible = true)
        }
        view.updatePinField(pinModel.pinLength)
    }

    override fun onNumberButtonClicked(number: Int) {
        if (!pinModel.isPinFull) {
            pinModel.addNumber(number)
            view.updateVisibilityBackspaceButton(isVisible = true)
            processPinIfFull()
            view.updatePinField(pinModel.pinLength)
        }
    }

    override fun onResetButtonClicked() {
        pinModel.resetPin()
        pinModel.updatePinState(PinState.RESET)
        view.updatePinField()
        modifyViewsAppearance()
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

    private fun processPinIfFull() {
        if (pinModel.isPinFull) {
            var result = false to PinState.LOGOUT
            when (pinModel.pinState) {
                PinState.CREATE -> result = pinModel.createPinIfSuccess()
                PinState.CONFIRM -> result = pinModel.savePinIfSuccess()
                PinState.LOGOUT -> result = pinModel.loginIfSuccess()
                PinState.RESET -> result = pinModel.deletePinIfSuccess()
                PinState.LOGIN -> Unit
            }
            view.updateVisibilityBackspaceButton(isVisible = false)
            processResult(result.first, result.second)
            pinModel.resetPin()
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
        modifyViewsAppearance()
    }

    private fun modifyViewsAppearance() {
        when (pinModel.pinState) {
            PinState.CREATE -> modifyAppearance(R.string.title_create, isVisible = false)
            PinState.CONFIRM -> modifyAppearance(R.string.title_confirm, isVisible = false)
            PinState.LOGOUT -> modifyAppearance(R.string.title_logout, isVisible = true)
            PinState.RESET -> modifyAppearance(R.string.title_reset, isVisible = false)
            PinState.LOGIN -> Unit
        }
    }

    private fun modifyAppearance(@StringRes titleTextResId: Int, isVisible: Boolean) {
        view.setTitleText(titleTextResId)
        view.updateVisibilityResetButton(isVisible)
    }

    private fun processFailMessage() {
        when (pinModel.pinState) {
            PinState.CREATE -> view.showPopupMessage(R.string.popup_simple)
            PinState.CONFIRM, PinState.RESET ->
                view.showPopupMessage(R.string.popup_different)
            PinState.LOGOUT -> view.showPopupMessage(R.string.popup_fail)
            PinState.LOGIN -> Unit
        }
    }
}