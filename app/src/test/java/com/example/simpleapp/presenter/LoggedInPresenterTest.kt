package com.example.simpleapp.presenter

import com.example.simpleapp.contracts.LoggedInContract
import com.example.simpleapp.presenters.LoggedInPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test

class LoggedInPresenterTest {
    private val view: LoggedInContract.View = mock()
    private val presenter = LoggedInPresenter(view)

    @Test
    fun `onViewCreated _ verify showPinSumResult is called`() {
        presenter.onViewCreated()

        verify(view, times(1)).showPinSumResult()
    }

    @Test
    fun `onLogOutButtonClicked _ verify moveToPinCodeFragment is called`() {
        presenter.onLogOutButtonClicked()

        verify(view, times(1)).moveToPinCodeFragment()
    }
}