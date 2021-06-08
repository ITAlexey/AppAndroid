package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView
import com.example.simpleapp.models.themes.ThemeApp

interface DialogAppThemeContract {
    interface View : BaseView {
        fun changeAppTheme(themeType: ThemeApp)

        fun turnOnLightThemeButton()

        fun turnOnDarkThemeButton()

        fun turnOnDefaultSystemButton()
    }

    interface Presenter : BasePresenter<View> {
        fun onChangeThemeButtonClicked(themeType: ThemeApp)
    }
}