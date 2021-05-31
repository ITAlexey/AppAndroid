package com.example.simpleapp.models

class ThemeModel(private val sharedPrefRepo: SharedPrefRepo) {

    fun saveTheme(themeMode: Int) {
        sharedPrefRepo.putIntByKey(KEY_APP_THEME, themeMode)
    }

    fun getTheme(): Int =
        sharedPrefRepo.getIntByKey(KEY_APP_THEME, -1)

    companion object {
        private const val KEY_APP_THEME = "KEY_APP_THEME"
    }
}