package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface DialogSettingsContract {
    interface View : BaseView {
       fun applyLightTheme()

       fun applyDarkTheme()

       fun applyDefaultSystemTheme()
    }

    interface Presenter : BasePresenter<View> {
        fun onChangeThemeButtonClicked(themeType: Int)
    }
}