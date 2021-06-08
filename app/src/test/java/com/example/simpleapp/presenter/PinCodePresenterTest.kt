package com.example.simpleapp.presenter

import com.example.simpleapp.R
import com.example.simpleapp.contracts.PinCodeFragmentContract
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.pincode.PinState
import com.example.simpleapp.presenters.PinCodePresenter
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

//TODO change some fields to any() method

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
    fun `onNumberButtonClicked _ when pin is not full _ then addNumber is called`() {
        setupPinFullness()

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).addNumber(1)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is not full _ verify updateVisibilityBackspaceButton is called`() {
        setupPinFullness()
        setupPinNotEmptiness()

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updateVisibilityBackspaceButton(false)
    }

    @Test
    fun `onNumberButtonClicked _ when pin is not full _ verify updatePinField is called`() {
        setupPinFullness()
        setupPinNotEmptiness()
        setupPinLength(1)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updatePinField(1)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess is called and pin is simple _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, false to PinState.CREATE)
        setupPinSimplicity(isPinSimple = true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(false, PinState.CREATE)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess is called and pin isn't simple _ verify saveAsConfirmationPin is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, false to PinState.CREATE)
        setupPinSimplicity()

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).saveAsConfirmationPin()
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess is called and pin isn't simple _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, false to PinState.CREATE)
        setupPinSimplicity()

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(true, PinState.CONFIRM)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess failed _ verify showPopupMessaged is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, false to PinState.CREATE)
        setupPinSimplicity()

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_simple)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess succeeded _ verify showPopupMessage is never called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, true to PinState.CREATE)

        presenter.onNumberButtonClicked(1)

        verify(view, times(0)).showPopupMessage(R.string.popup_simple)
    }

    @Test
    fun `onNumberButtonClicked _ when createPinIfSuccess succeeded _ verify updatePinState is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CREATE, true to PinState.CONFIRM)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updatePinState(PinState.CONFIRM)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess is called and pins are equal _ verify savePin is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CONFIRM, false to PinState.CONFIRM)
        whenever(pinModel.checkPinsEquality()).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).savePin()
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess is called and pins are equal _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CONFIRM, false to PinState.CONFIRM)
        whenever(pinModel.checkPinsEquality()).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(true, PinState.LOGOUT)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess is called and pins are not equal _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CONFIRM, false to PinState.CONFIRM)
        whenever(pinModel.checkPinsEquality()).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(false, PinState.CONFIRM)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess succeeded _ verify showPopupMessage is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CONFIRM, true to PinState.LOGOUT)
        whenever(pinModel.checkPinsEquality()).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_saved)
    }

    @Test
    fun `onNumberButtonClicked _ when savePinIfSuccess failed _ verify showPopupMessaged is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.CONFIRM, false to PinState.CONFIRM)
        whenever(pinModel.checkPinsEquality()).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_different)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess is called  _ verify getPin is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, false to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).getPin()
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess and pin isn't correct  _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, false to PinState.LOGOUT)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(false, PinState.LOGOUT)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess and pin is correct  _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, false to PinState.LOGOUT)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(true, PinState.LOGIN)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess succeeded _ verify calculateSumPinNumbers is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, true to PinState.LOGIN)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).calculateSumPinNumbers()
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess succeeded _ verify moveToLoggedInFragment is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, true to PinState.LOGIN)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).moveToLoggedInFragment(0)
    }

    @Test
    fun `onNumberButtonClicked _ when loginIfSuccess failed _ verify showPopupMessaged is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.LOGOUT, false to PinState.LOGOUT)
        whenever(pinModel.checkPinsEquality()).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_fail)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess is called _ verify getPin is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.RESET, false to PinState.LOGOUT)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).getPin()
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess pin is correct _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.RESET, false to PinState.LOGOUT)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(true, PinState.CREATE)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess pin isn't correct _ verify updateProcessedPinStatus is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.RESET, false to PinState.LOGOUT)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(pinModel, times(1)).updateProcessedPinStatus(false, PinState.LOGOUT)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess succeeded _ verify showPopupMessage is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.RESET, true to PinState.CREATE)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(true)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_reset)
    }

    @Test
    fun `onNumberButtonClicked _ when deletePinIfSuccess failed _ verify showPopupMessage is called`() {
        setupPinFullness(isPinFull = true)
        setupPinStateAndStatus(PinState.RESET, false to PinState.LOGOUT)
        whenever(pinModel.getPin()).thenReturn("1234")
        whenever(pinModel.isPinCorrect("1234")).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).showPopupMessage(R.string.popup_different)
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is not empty _ verify removeNumber is called`() {
        setupPinNotEmptiness(isPinNotEmpty = true)

        presenter.onBackspaceButtonClicked()

        verify(pinModel, times(1)).removeNumber()
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is not empty _ verify updatePinField is called`() {
        setupPinNotEmptiness(isPinNotEmpty = true)
        setupPinLength(3)

        presenter.onBackspaceButtonClicked()

        verify(view, times(1)).updatePinField(3)
    }

    @Test
    fun `onBackspaceButtonClicked _ when pin is empty _ verify updateVisibilityBackspaceButton is called`() {
        setupPinNotEmptiness(isPinNotEmpty = false)
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

    private fun setupPinLength(length: Int) {
        whenever(pinModel.pinLength).thenReturn(length)
    }

    private fun setupPinNotEmptiness(isPinNotEmpty: Boolean = false) {
        whenever(pinModel.isPinNotEmpty).thenReturn(isPinNotEmpty)
    }

    private fun setupPinFullness(isPinFull: Boolean = false) {
        whenever(pinModel.isPinFull).thenReturn(false, isPinFull)
    }

    private fun setupPinSimplicity(isPinSimple: Boolean = false) {
        whenever(pinModel.isPinSimple).thenReturn(isPinSimple)
    }

    private fun setupPinStateAndStatus(pinState: PinState, pinStatus: Pair<Boolean, PinState>) {
        whenever(pinModel.pinState).thenReturn(pinState)
        whenever(pinModel.processedPinStatus).thenReturn(pinStatus)
    }
}