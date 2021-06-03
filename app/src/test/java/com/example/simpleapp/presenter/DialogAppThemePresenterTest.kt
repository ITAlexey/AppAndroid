package com.example.simpleapp.presenter

import androidx.appcompat.app.AppCompatDelegate
import com.example.simpleapp.contracts.DialogAppThemeContract
import com.example.simpleapp.models.ThemeModel
import com.example.simpleapp.presenters.DialogAppThemesPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test

class DialogAppThemePresenterTest {
    private val view: DialogAppThemeContract.View = mock()
    private val themeModel: ThemeModel = mock()
    private val presenter = DialogAppThemesPresenter(view, themeModel)

    @Test
    fun `onViewCreated _ when light theme is chosen _ verify turnOnLightThemeButton is called`() {
        whenever(themeModel.getTheme()).thenReturn(LIGHT)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnLightThemeButton()
    }

    @Test
    fun `onViewCreated _ when dark theme is chosen _ verify turnOnDarkThemeButton is called`() {
        whenever(themeModel.getTheme()).thenReturn(NIGHT)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnDarkThemeButton()
    }

    @Test
    fun `onViewCreated _ when default system theme is chosen _ verify turnOnLightThemeButton is called`() {
        whenever(themeModel.getTheme()).thenReturn(DEFAULT)

        presenter.onViewCreated()

        verify(view, times(1)).turnOnDefaultSystemButton()
    }

    @Test
    fun `onChangeThemeButtonClicked _ verify changeAppTheme is called`() {
        presenter.onChangeThemeButtonClicked(LIGHT)

        verify(view, times(1)).changeAppTheme(LIGHT)
    }

    @Test
    fun `onChangeThemeButtonClicked _ verify saveTheme is called`() {
        presenter.onChangeThemeButtonClicked(LIGHT)

        verify(themeModel, times(1)).saveTheme(LIGHT)
    }

    companion object {
        private const val LIGHT = AppCompatDelegate.MODE_NIGHT_NO
        private const val NIGHT = AppCompatDelegate.MODE_NIGHT_YES
        private const val DEFAULT = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}