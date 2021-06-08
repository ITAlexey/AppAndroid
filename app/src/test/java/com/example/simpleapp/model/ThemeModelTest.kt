package com.example.simpleapp.model

import com.example.simpleapp.models.SharedPrefRepo
import com.example.simpleapp.models.themes.ThemeApp
import com.example.simpleapp.models.themes.ThemeModel
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ThemeModelTest {
    private val sharedPrefRepo: SharedPrefRepo = mock()
    private val themeModel = ThemeModel(sharedPrefRepo)

    @Test
    fun `getSavedTheme _ verify getIntByKey is called`() {
        whenever(sharedPrefRepo.getIntByKey(KEY_APP_THEME, SYSTEM)).thenReturn(LIGHT)

        themeModel.getSavedTheme(KEY_APP_THEME, LIGHT)

        verify(sharedPrefRepo, times(1)).getIntByKey(KEY_APP_THEME, LIGHT)
    }

    @Test
    fun `getSavedTheme _ when default value isn't define _verify getIntByKey returns system index`() {
        whenever(sharedPrefRepo.getIntByKey(KEY_APP_THEME, SYSTEM)).thenReturn(SYSTEM)

        themeModel.getSavedTheme(KEY_APP_THEME)

        verify(sharedPrefRepo, times(1)).getIntByKey(KEY_APP_THEME, SYSTEM)
    }

    @Test
    fun `getSavedTheme _ when key isn't saved and default theme is light _ verify getIntByKey returns light`() {
        whenever(sharedPrefRepo.getIntByKey(KEY_APP_THEME, LIGHT)).thenReturn(LIGHT)

        val theme = themeModel.getSavedTheme(KEY_APP_THEME, LIGHT)

        Assertions.assertEquals(ThemeApp.LIGHT, theme)
    }

    @Test
    fun `getSavedTheme _ when key isn't saved and default theme is dark _ verify getIntByKey returns dark`() {
        whenever(sharedPrefRepo.getIntByKey(KEY_APP_THEME, DARK)).thenReturn(DARK)

        val theme = themeModel.getSavedTheme(KEY_APP_THEME, DARK)

        Assertions.assertEquals(ThemeApp.DARK, theme)
    }

    @Test
    fun `getSavedTheme _ when key isn't saved and default theme is system _ verify getIntByKey returns system`() {
        whenever(sharedPrefRepo.getIntByKey(KEY_APP_THEME, SYSTEM)).thenReturn(SYSTEM)

        val theme = themeModel.getSavedTheme(KEY_APP_THEME, SYSTEM)

        Assertions.assertEquals(ThemeApp.SYSTEM, theme)
    }

    @Test
    fun `saveTheme _ when theme is light _ verify putIntByKey is called`() {
        themeModel.saveTheme(ThemeApp.LIGHT, KEY_APP_THEME)

        verify(sharedPrefRepo, times(1)).putIntByKey(KEY_APP_THEME, LIGHT)
    }

    @Test
    fun `saveTheme _ when theme is dark _ verify putIntByKey is called`() {
        themeModel.saveTheme(ThemeApp.DARK, KEY_APP_THEME)

        verify(sharedPrefRepo, times(1)).putIntByKey(KEY_APP_THEME, DARK)
    }

    @Test
    fun `saveTheme _ when theme is system _ verify putIntByKey is called`() {
        themeModel.saveTheme(ThemeApp.SYSTEM, KEY_APP_THEME)

        verify(sharedPrefRepo, times(1)).putIntByKey(KEY_APP_THEME, SYSTEM)
    }

    companion object {
        private const val KEY_APP_THEME = "THEME"
        private val LIGHT = ThemeApp.LIGHT.themeMode
        private val DARK = ThemeApp.DARK.themeMode
        private val SYSTEM = ThemeApp.SYSTEM.themeMode
    }
}