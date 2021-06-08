package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView
import com.example.simpleapp.models.themes.ThemeApp

interface MainActivityContract {
    interface View: BaseView {
        fun openSettingsDialog()

        fun applyAppTheme(themeType: ThemeApp)
    }

    interface Presenter: BasePresenter<View> {
        fun onSettingsButtonClicked()
    }
}