package com.example.simpleapp.presenter

import com.example.simpleapp.contracts.DialogAppThemeContract
import com.example.simpleapp.models.themes.ThemeApp
import com.example.simpleapp.models.themes.ThemeModel
import com.example.simpleapp.presenters.DialogAppThemesPresenter
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

class DialogAppThemePresenterTest {
    private val view: DialogAppThemeContract.View = mock()
    private val themeModel: ThemeModel = mock()
    private val presenter = DialogAppThemesPresenter(view, themeModel)

    @Test
    fun `onViewCreated _ when light theme is chosen _ verify turnOnLightThemeButton is called`() {
        whenever(themeModel.getSavedTheme()).thenReturn(ThemeApp.LIGHT)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnLightThemeButton()
    }

    @Test
    fun `onViewCreated _ when dark theme is chosen _ verify turnOnDarkThemeButton is called`() {
        whenever(themeModel.getSavedTheme()).thenReturn(ThemeApp.DARK)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnDarkThemeButton()
    }

    @Test
    fun `onViewCreated _ when default system theme is chosen _ verify turnOnLightThemeButton is called`() {
        whenever(themeModel.getSavedTheme()).thenReturn(ThemeApp.SYSTEM)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnDefaultSystemButton()
    }

    @Test
    fun `onChangeThemeButtonClicked _ verify changeAppTheme is called`() {
        presenter.onChangeThemeButtonClicked(ThemeApp.LIGHT)

        verify(view, times(1)).changeAppTheme(ThemeApp.LIGHT)
    }
}