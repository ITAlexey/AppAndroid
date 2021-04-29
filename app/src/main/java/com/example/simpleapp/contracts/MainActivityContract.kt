package com.example.simpleapp.contracts

import android.os.Bundle
import androidx.annotation.StringRes
import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface MainActivityContract {
    interface Presenter : BasePresenter<View> {
        fun onNumberButtonClicked(number: Int)
        fun onResetButtonClicked()
        fun onBackspaceButtonClicked()
        fun onSavedInstanceStateCalled(outState: Bundle)
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