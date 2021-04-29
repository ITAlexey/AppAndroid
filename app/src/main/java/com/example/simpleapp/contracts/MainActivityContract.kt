package com.example.simpleapp.contracts

import androidx.annotation.StringRes
import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView
import com.example.simpleapp.simpleapp.utils.PinState

interface MainActivityContract {
    interface Presenter : BasePresenter<View> {
        fun onNumberButtonClicked(number: Int)
        fun onResetButtonClicked()
        fun onBackspaceButtonClicked()
        fun subscribe(view: View, pinState: PinState?)
        fun getPinState(): PinState?
    }

    interface View : BaseView {
        fun updatePinField(pinLen: Int = 0)
        fun updateVisibilityBackspaceButton(isVisible: Boolean)
        fun updateVisibilityResetButton(isVisible: Boolean)
        fun moveToLogInActivity()
        fun setTitleText(@StringRes titleTextResId: Int)
        fun showPopupMessage(@StringRes popupTextResId: Int)
    }
}