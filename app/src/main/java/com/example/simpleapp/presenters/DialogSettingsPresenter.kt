package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.DialogSettingsContract
import com.example.simpleapp.views.DialogSettingsFragment.Companion.DARK
import com.example.simpleapp.views.DialogSettingsFragment.Companion.LIGHT
import com.example.simpleapp.views.DialogSettingsFragment.Companion.SYSTEM

class DialogSettingsPresenter(private val view: DialogSettingsContract.View) : DialogSettingsContract.Presenter {

    override fun onChangeThemeButtonClicked(themeType: Int) {
        when (themeType) {
            DARK -> view.applyDarkTheme()
            LIGHT -> view.applyLightTheme()
            SYSTEM -> view.applyDefaultSystemTheme()
        }
    }
}