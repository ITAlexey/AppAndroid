package com.example.simpleapp.models.utils

import com.example.simpleapp.R

enum class PinState(val titleTextId: Int) {
    LOGIN(R.string.title_logged_in),
    LOGOUT(R.string.title_logout),
    CREATE(R.string.title_create),
    CONFIRM(R.string.title_reset),
    RESET(R.string.title_confirm)
}