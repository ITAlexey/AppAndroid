package com.example.simpleapp

import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.presenters.MainActivityPresenter
import com.example.simpleapp.models.PinModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class MainActivityPresenterTest {
    private var view: MainActivityContract.View = mock()
    private val pinModel: PinModel = mock()
    private lateinit var presenter: MainActivityPresenter

    @Before
    fun setUp() {
        presenter = MainActivityPresenter(pinModel)
    }

    @Test
    fun `onNumberButtonClicked verify that isPinFull has been called`() {
        presenter.onNumberButtonClicked(1)
        verify(pinModel, times(1)).isPinFull()
    }
}