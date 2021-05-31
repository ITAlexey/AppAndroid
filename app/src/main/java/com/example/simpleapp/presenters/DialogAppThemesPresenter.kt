package com.example.simpleapp.presenters

import androidx.appcompat.app.AppCompatDelegate
import com.example.simpleapp.contracts.DialogSettingsContract
import com.example.simpleapp.models.ThemeModel
import java.lang.IllegalStateException

class DialogAppThemesPresenter(
    private val view: DialogSettingsContract.View,
    private val themeModel: ThemeModel
) : DialogSettingsContract.Presenter {

    override fun onChangeThemeButtonClicked(themeType: Int) {
        view.changeAppTheme(themeType)
        themeModel.saveTheme(themeType)
    }

    override fun onViewCreated() {
        updateButtons()
    }

    private fun updateButtons() {
        when (themeModel.getTheme()) {
            AppCompatDelegate.MODE_NIGHT_YES -> view.turnOnDarkThemeButton()
            AppCompatDelegate.MODE_NIGHT_NO -> view.turnOnLightThemeButton()
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> view.turnOnDefaultSystemButton()
            else -> throw  IllegalStateException("Invalid themeMode")
        }
    }

}