package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.models.themes.ThemeModel

class MainActivityPresenter(
    private val view: MainActivityContract.View,
    private val themeModel: ThemeModel
) : MainActivityContract.Presenter {

    override fun onSettingsButtonClicked() {
        view.openSettingsDialog()
    }

    override fun onViewCreated() {
        val theme = themeModel.getSavedTheme()
        view.applyAppTheme(theme)
    }
}