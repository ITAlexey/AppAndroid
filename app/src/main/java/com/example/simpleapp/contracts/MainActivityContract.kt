package com.example.simpleapp.contracts

import androidx.annotation.StringRes

interface MainActivityContract {
    interface Presenter : BasePresenter {
        fun onViewCreated()
        fun onNumberButtonClicked(number: Int)
        fun onResetButtonClicked()
        fun onBackspaceButtonClicked()
    }

    interface View : BaseView {
       fun updatePinField(pinLen: Int = 0)
       fun showBackspaceButton()
       fun hideBackspaceButton()
       fun showResetButton()
       fun hideResetButton()
       fun modifyTitleText(@StringRes titleTextResId: Int)
       fun showMessage(@StringRes popupTextResId: Int)
    }
}