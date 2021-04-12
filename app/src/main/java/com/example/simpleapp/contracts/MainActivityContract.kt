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
        fun subscribe(view: View, state: Bundle?)
    }

    interface View : BaseView {
        fun updatePinField(pinLen: Int = 0)
        fun showBackspaceButton()
        fun hideBackspaceButton()
        fun showResetButton()
        fun hideResetButton()
        fun showLogInActivity()
        fun setTitleText(@StringRes titleTextResId: Int)
        fun showMessage(@StringRes popupTextResId: Int)
    }
}