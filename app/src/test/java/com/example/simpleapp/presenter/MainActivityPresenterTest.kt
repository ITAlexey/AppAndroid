package com.example.simpleapp.presenter

import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.ThemeModel
import com.example.simpleapp.presenters.MainActivityPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test


class MainActivityPresenterTest {
    private val view: MainActivityContract.View = mock()
    private val themeModel: ThemeModel = mock()
    private val presenter = MainActivityPresenter(view, themeModel)

    @Test
    fun `onViewCreated _ verify getTheme is called`() {
        presenter.onViewCreated()

        verify(themeModel, times(1)).getTheme()
    }

    @Test
    fun `onViewCreated _ verify applyAppTheme is called`() {
        whenever(themeModel.getTheme()).thenReturn(1)

        presenter.onViewCreated()

        verify(view, times(1)).applyAppTheme(1)
    }
}