package com.example.simpleapp.models.themes

import androidx.appcompat.app.AppCompatDelegate

enum class ThemeApp(val themeMode: Int) {
    LIGHT(AppCompatDelegate.MODE_NIGHT_NO),
    DARK(AppCompatDelegate.MODE_NIGHT_YES),
    SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}