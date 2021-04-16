package com.example.simpleapp.models.utils

import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract

enum class PinState {
    LOGIN {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.moveToLogInActivity()
        }

        override fun nextState(): PinState = LOGOUT

        override fun showFailMessage(view: MainActivityContract.View?) {
        }

        override fun showSuccessMessage(view: MainActivityContract.View?) {
        }
    },
    LOGOUT {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.showOrHideResetButton(true)
            view?.setTitleText(R.string.title_logout)
        }

        override fun nextState(): PinState = LOGIN

        override fun showFailMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_fail)
        }

        override fun showSuccessMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_saved)
        }
    },
    CREATE {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.showOrHideResetButton(false)
            view?.setTitleText(R.string.title_create)
        }

        override fun nextState(): PinState = CONFIRM

        override fun showFailMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_simple)
        }

        override fun showSuccessMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_reset)
        }
    },
    CONFIRM {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.showOrHideResetButton(false)
            view?.setTitleText(R.string.title_confirm)
        }

        override fun nextState(): PinState = LOGOUT

        override fun showFailMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_different)
        }

        override fun showSuccessMessage(view: MainActivityContract.View?) {
        }
    },
    RESET {
        override fun modifyViewAppearance(view: MainActivityContract.View?) {
            view?.showOrHideResetButton(false)
            view?.setTitleText(R.string.title_reset)
        }

        override fun nextState(): PinState = CREATE

        override fun showFailMessage(view: MainActivityContract.View?) {
            view?.showPopupMessage(R.string.popup_different)
        }

        override fun showSuccessMessage(view: MainActivityContract.View?) {
        }
    };

    abstract fun modifyViewAppearance(view: MainActivityContract.View?)
    abstract fun nextState(): PinState
    abstract fun showFailMessage(view: MainActivityContract.View?)
    abstract fun showSuccessMessage(view: MainActivityContract.View?)
}