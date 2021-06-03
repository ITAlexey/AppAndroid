package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface DialogAppThemeContract {
    interface View : BaseView {
        fun changeAppTheme(themeType: Int)

        fun turnOnLightThemeButton()

        fun turnOnDarkThemeButton()

        fun turnOnDefaultSystemButton()
    }

    interface Presenter : BasePresenter<View> {
        fun onChangeThemeButtonClicked(themeType: Int)

        fun onViewCreated()
    }
}