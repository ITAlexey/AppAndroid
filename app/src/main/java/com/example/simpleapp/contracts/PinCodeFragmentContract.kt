package com.example.simpleapp.contracts

import androidx.annotation.StringRes
import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface PinCodeFragmentContract {
    interface View: BaseView {
        fun updatePinField(pinLen: Int = 0)

        fun updateVisibilityBackspaceButton(isVisible: Boolean)

        fun updateVisibilityResetButton(isVisible: Boolean)

        fun moveToLoggedInFragment(pinSumResult: Int)

        fun setTitleText(@StringRes titleTextResId: Int)

        fun showPopupMessage(@StringRes popupTextResId: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun onNumberButtonClicked(number: Int)

        fun onResetButtonClicked()

        fun onBackspaceButtonClicked()
    }
}