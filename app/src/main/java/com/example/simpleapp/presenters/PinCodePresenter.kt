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

    override fun onViewCreated() =
        processPinState()

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
            PinState.RESET -> updateViewsAppearance(
                R.string.title_reset,
                backspaceButtonVisibility
            )
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
            processPinStateTransition()
            pinModel.resetPin()
            processResult()
        } else {
            view.updateVisibilityBackspaceButton(pinModel.isPinNotEmpty)
            view.updatePinField(pinModel.pinLength)
        }
    }

    private fun processPinStateTransition() {
        when (pinModel.pinState) {
            PinState.CREATE -> createPinIfSuccess()
            PinState.CONFIRM -> savePinIfSuccess()
            PinState.LOGOUT -> loginIfSuccess()
            PinState.RESET -> deletePinIfSuccess()
            PinState.LOGIN -> Unit
        }
    }

    private fun createPinIfSuccess() {
        if (!pinModel.isPinSimple) {
            pinModel.saveAsConfirmationPin()
            pinModel.updateProcessedPinStatus(true, PinState.CONFIRM)
        } else {
            pinModel.updateProcessedPinStatus(false, PinState.CREATE)
        }
    }

    private fun savePinIfSuccess() {
        if (pinModel.checkPinsEquality()) {
            pinModel.savePin()
            pinModel.updateProcessedPinStatus(true, PinState.LOGOUT)
        } else {
            pinModel.updateProcessedPinStatus(false, PinState.CONFIRM )
        }
    }

    private fun loginIfSuccess() {
        val pin = pinModel.getPin()
        if (pinModel.isPinCorrect(pin)) {
            pinModel.updateProcessedPinStatus(true, PinState.LOGIN)
        } else {
            pinModel.updateProcessedPinStatus(false, PinState.LOGOUT)
        }
    }

    private fun deletePinIfSuccess() {
        val pin = pinModel.getPin()
        if (pinModel.isPinCorrect(pin)) {
            pinModel.removePin()
            pinModel.updateProcessedPinStatus(true, PinState.CREATE)
        } else {
            pinModel.updateProcessedPinStatus(false, PinState.LOGOUT)
        }
    }

    private fun processResult() {
        val processedResultIsSuccess = pinModel.processedPinStatus.first
        val processedResultNewPinState = pinModel.processedPinStatus.second
        when {
            processedResultNewPinState == PinState.LOGIN -> processLoginState()
            processedResultIsSuccess -> processSuccessMessage(processedResultNewPinState)
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