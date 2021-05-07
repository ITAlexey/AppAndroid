package com.example.simpleapp.presenters

import android.os.Bundle
import androidx.annotation.StringRes
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.utils.PinState
import com.example.simpleapp.utils.getEnum
import com.example.simpleapp.utils.putEnum

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val pinModel: PinModel,
    bundle: Bundle?
) : MainActivityContract.Presenter {

    private var currentPinState: PinState

    init {
        currentPinState = getStateFromBundle(bundle) ?: getStateFromModel()
        modifyViewsAppearance()
        if (pinModel.isPinNotEmpty) {
            view.updateVisibilityBackspaceButton(isVisible = true)
        }
        view.updatePinField(pinModel.pinLength)
    }

    private fun getStateFromBundle(outState: Bundle?): PinState? {
        return outState?.getEnum(PIN_STATE, PinState.CREATE)
    }

    private fun getStateFromModel(): PinState {
        return if (pinModel.isPinSaved) {
            PinState.LOGOUT
        } else {
            PinState.CREATE
        }
    }

    private fun processPinIfFull() {
        if (pinModel.isPinFull) {
            var isSuccess = false
            when (currentPinState) {
                PinState.CREATE -> isSuccess = pinModel.createPinIfSuccess()
                PinState.CONFIRM -> isSuccess = pinModel.savePinIfSuccess()
                PinState.LOGOUT -> isSuccess = pinModel.loginIfSuccess()
                PinState.RESET -> isSuccess = pinModel.deletePinIfSuccess()
                PinState.LOGIN -> Unit
            }
            view.updateVisibilityBackspaceButton(isVisible = false)
            processResult(isSuccess)
            pinModel.resetPin()
        }
    }

    private fun processResult(isSuccess: Boolean) {
        if (isSuccess) {
            processSuccessMessage()
            updatePinState()
            modifyViewsAppearance()
            processIfLoginState()
        } else {
            processFailMessage()
        }
    }

    private fun processIfLoginState() {
        if (currentPinState == PinState.LOGIN) {
            updatePinState()
            view.moveToLogInActivity()
        }
    }

    private fun modifyViewsAppearance() {
        when (currentPinState) {
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
        when (currentPinState) {
            PinState.CREATE -> view.showPopupMessage(R.string.popup_simple)
            PinState.CONFIRM, PinState.RESET ->
                view.showPopupMessage(R.string.popup_different)
            PinState.LOGOUT -> view.showPopupMessage(R.string.popup_fail)
            PinState.LOGIN -> Unit
        }
    }

    private fun processSuccessMessage() {
        when (currentPinState) {
            PinState.RESET -> view.showPopupMessage(R.string.popup_reset)
            PinState.CONFIRM -> view.showPopupMessage(R.string.popup_saved)
            PinState.LOGOUT, PinState.LOGIN, PinState.CREATE -> Unit
        }
    }

    private fun updatePinState() {
        updatePinState()
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
        currentPinState = PinState.RESET
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

    override fun onSavedInstanceStateCalled(outState: Bundle) {
        outState.putEnum(PIN_STATE, currentPinState)
    }

    companion object {
        private const val PIN_STATE = "com.example.simpleapp.pin_state"
    }
}