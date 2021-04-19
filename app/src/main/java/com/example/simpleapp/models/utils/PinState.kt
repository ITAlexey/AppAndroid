package com.example.simpleapp.models.utils

import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract

enum class PinState {
    LOGIN {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.moveToLogInActivity()
        }

        override fun nextState(): PinState = LOGOUT
    },
    LOGOUT {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.updateVisibilityResetButton(true)
            view?.setTitleText(R.string.title_logout)
        }

        override fun nextState(): PinState = LOGIN
    },
    CREATE {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.updateVisibilityResetButton(false)
            view?.setTitleText(R.string.title_create)
        }

        override fun nextState(): PinState = CONFIRM
    },
    CONFIRM {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.updateVisibilityResetButton(false)
            view?.setTitleText(R.string.title_confirm)
        }

        override fun nextState(): PinState = LOGOUT
    },
    RESET {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.updateVisibilityResetButton(false)
            view?.setTitleText(R.string.title_reset)
        }

        override fun nextState(): PinState = CREATE
    };

    abstract fun modifyViewAppearance(view: MainActivityContract.View?)
    abstract fun nextState(): PinState
}