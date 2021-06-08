package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.DialogAppThemeContract
import com.example.simpleapp.models.themes.ThemeApp
import com.example.simpleapp.models.themes.ThemeModel

class DialogAppThemesPresenter(
    private val view: DialogAppThemeContract.View,
    private val themeModel: ThemeModel
) : DialogAppThemeContract.Presenter {

    override fun onChangeThemeButtonClicked(themeType: ThemeApp) {
        view.changeAppTheme(themeType)
        themeModel.saveTheme(themeType)
    }

    override fun onViewCreated() {
        updateButtons()
    }

    private fun updateButtons() {
        when (themeModel.getSavedTheme()) {
            ThemeApp.DARK -> view.turnOnDarkThemeButton()
            ThemeApp.LIGHT -> view.turnOnLightThemeButton()
            else -> view.turnOnDefaultSystemButton()
        }
    }

}