package com.example.simpleapp.presenter

import com.example.simpleapp.R
import com.example.simpleapp.contracts.PinCodeFragmentContract
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.pincode.PinState
import com.example.simpleapp.presenters.PinCodePresenter
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

class PinCodePresenterTest {
    private val view: PinCodeFragmentContract.View = mock()
    private val pinModel: PinModel = mock()
    private val presenter = PinCodePresenter(view, pinModel)

    @Test
    fun `onViewCreated _ when create status _ verify setTitleText is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_create)
    }

    @Test
    fun `onViewCreated _ when create status _ verify reset and backspace updateButtonVisibility are called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)

        presenter.onViewCreated()

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
        verify(view, times(1)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated _ when create status _ verify updatePinField is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)
        whenever(pinModel.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onViewCreated _ when confirm status _ verify setTitleText is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_confirm)
    }

    @Test
    fun `onViewCreated _ when confirm status _ verify reset and backspace updateButtonVisibility are called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)

        presenter.onViewCreated()

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
        verify(view, times(1)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated _ when confirm status _ verify updatePinField is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)
        whenever(pinModel.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onViewCreated _ when logout status _ verify setTitleText is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_logout)
    }

    @Test
    fun `onViewCreated _ when logout status _ verify reset and backspace updateButtonVisibility are called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)

        presenter.onViewCreated()

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
        verify(view, times(1)).updateVisibilityResetButton(true)
    }

    @Test
    fun `onViewCreated _ when logout status _ verify updatePinField is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onViewCreated _ when reset status _ verify setTitleText is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_reset)
    }

    @Test
    fun `onViewCreated _ when reset status _ verify reset and backspace updateButtonVisibility are called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)

        presenter.onViewCreated()

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
        verify(view, times(1)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated _ when reset status _ verify updatePinField is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)
        whenever(pinModel.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onViewCreated _ when login status _ verify setTitleText is never called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGIN)

        presenter.onViewCreated()

        verify(view, times(0)).setTitleText(R.string.title_reset)
    }

    @Test
    fun `onViewCreated _ when login status _ verify reset and backspace updateButtonVisibility are never called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGIN)

        presenter.onViewCreated()

        verify(view, times(0)).updateVisibilityBackspaceButton(true)
        verify(view, times(0)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated _ when login status _ verify updatePinField is never called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGIN)
        whenever(pinModel.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(0)).updatePinField(4)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full _ then addNumber is never called`() {
        whenever(pinModel.isPinFull).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(0)).addNumber(1)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is not full _ then addNumber is called`() {
        whenever(pinModel.isPinFull).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).addNumber(1)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is not full _ then updateVisibilityBackspaceButton is called`() {
        whenever(pinModel.isPinFull).thenReturn(false)
        whenever(pinModel.isPinNotEmpty).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is not full _ then updatePinField is called`() {
        whenever(pinModel.isPinFull).thenReturn(false)
        whenever(pinModel.pinLength).thenReturn(1)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updatePinField(1)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full and state is create _ verify createPinIfSuccess is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).createPinIfSuccess()
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess failed _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_simple)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess succeeded _ verify showPopupMessaged is never called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(view, times(0)).showPopupMessage(R.string.popup_simple)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess succeeded _ verify updatePinState is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.CONFIRM)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updatePinState(PinState.CONFIRM)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full and state is confirm _ verify savePinIfSuccess is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.CONFIRM)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).savePinIfSuccess()
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess failed _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_different)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess succeeded _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.CONFIRM)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_saved)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess succeeded _ verify updatePinState is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.CONFIRM)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updatePinState(PinState.LOGOUT)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full and state is logout _ verify loginIfSuccess is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).loginIfSuccess()
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess failed _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_fail)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess succeeded _ verify updatePinState is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.LOGIN)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updatePinState(PinState.LOGOUT)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess succeeded _ verify calculateSumPinNumbers is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.LOGIN)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).calculateSumPinNumbers()
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess succeeded _ verify moveToLoggedInFragment is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGOUT)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.LOGIN)
        whenever(pinModel.calculateSumPinNumbers()).thenReturn(2)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).moveToLoggedInFragment(2)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full and state is reset _ verify deletePinIfSuccess is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.RESET)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).deletePinIfSuccess()
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess failed _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_different)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess succeeded _ verify showPopupMessaged is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_reset)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess succeeded _ verify updatePinState is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.RESET)
        whenever(pinModel.processedPinResult).thenReturn(true to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updatePinState(PinState.CREATE)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is full after addNumber _ verify resetPin is called`() {
        whenever(pinModel.isPinFull)
            .thenReturn(false)
            .thenReturn(true)
        whenever(pinModel.pinState).thenReturn(PinState.LOGIN)
        whenever(pinModel.processedPinResult).thenReturn(false to PinState.LOGIN)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).resetPin()
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is not empty _ verify removeNumber is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)

        presenter.onBackspaceButtonClicked()

        verify(pinModel, times(1)).removeNumber()
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is not empty _ verify updatePinField is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(true)
        whenever(pinModel.pinLength).thenReturn(3)

        presenter.onBackspaceButtonClicked()

        verify(view, times(1)).updatePinField(3)
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is empty _ verify updateVisibilityBackspaceButton is called`() {
        whenever(pinModel.isPinNotEmpty).thenReturn(false)
        whenever(pinModel.isPinEmpty).thenReturn(true)

        presenter.onBackspaceButtonClicked()

        verify(view, times(1)).updateVisibilityBackspaceButton(false)
    }

    @Test
    fun `onResetButtonClicked _ verify resetPin is called`() {
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)

        presenter.onResetButtonClicked()

        verify(pinModel, times(1)).resetPin()
    }

    @Test
    fun `onResetButtonClicked _ verify updatePinState is called`() {
        whenever(pinModel.pinState).thenReturn(PinState.CREATE)

        presenter.onResetButtonClicked()

        verify(pinModel, times(1)).updatePinState(PinState.RESET)
    }
}