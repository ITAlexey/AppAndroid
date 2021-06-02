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
    private val model: PinModel = mock()
    private val presenter = PinCodePresenter(view, model)

    @Test
    fun `onViewCreated when create status, verify setTitleText and updateVisibilityResetButton invoked`() {
        whenever(model.pinState).thenReturn(PinState.CREATE)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_create)
        verify(view, times(1)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated when logout status, verify setTitleText and updateVisibilityResetButton invoked`() {
        whenever(model.pinState).thenReturn(PinState.LOGOUT)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_logout)
        verify(view, times(1)).updateVisibilityResetButton(true)
    }

    @Test
    fun `onViewCreated when reset status, verify setTitleText and updateVisibilityResetButton invoked`() {
        whenever(model.pinState).thenReturn(PinState.RESET)

        presenter.onViewCreated()

        verify(view, times(1)).setTitleText(R.string.title_reset)
        verify(view, times(1)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated when login status, verify setTitleText and updateVisibilityResetButton never invoked`() {
        whenever(model.pinState).thenReturn(PinState.LOGIN)

        presenter.onViewCreated()

        verify(view, times(0)).setTitleText(R.string.title_logout)
        verify(view, times(0)).updateVisibilityResetButton(false)
    }

    @Test
    fun `onViewCreated verify updateVisibilityBackspaceButton invoked`() {
        whenever(model.pinState).thenReturn(PinState.LOGIN)
        whenever(model.isPinNotEmpty).thenReturn(false)

        presenter.onViewCreated()

        verify(view, times(1)).updateVisibilityBackspaceButton(false)
    }

    @Test
    fun `onViewCreated verify updatePinField invoked`() {
        whenever(model.pinState).thenReturn(PinState.LOGIN)
        whenever(model.isPinNotEmpty).thenReturn(false)
        whenever(model.pinLength).thenReturn(4)

        presenter.onViewCreated()

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onNumberButtonClicked when pin is not full, then verify addNumber invoked`() {
        whenever(model.isPinFull).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(model, times(1)).addNumber(1)
    }

    @Test
    fun `onNumberButtonClicked when pin is not full, then verify updateVisibilityBackspaceButton invoked`() {
        whenever(model.isPinFull).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updateVisibilityBackspaceButton(true)
    }

    @Test
    fun `onNumberButtonClicked when pin is not full, then verify updatePinField invoked`() {
        whenever(model.isPinFull).thenReturn(false)
        whenever(model.pinLength).thenReturn(4)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updatePinField(4)
    }

    @Test
    fun `onNumberButtonClicked processPinIfFull when pin is not full verify updateVisibilityBackspaceButton invoked`() {
        whenever(model.isPinFull).thenReturn(false)

        presenter.onNumberButtonClicked(1)

        verify(view, times(1)).updateVisibilityBackspaceButton(false)
    }
}