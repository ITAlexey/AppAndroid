package com.example.simpleapp.contracts

import androidx.annotation.StringRes
import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.models.utils.PinState

interface MainActivityContract {
    interface Presenter : BasePresenter<View> {
        fun onNumberButtonClicked(number: Int)
        fun onResetButtonClicked()
        fun onBackspaceButtonClicked()
        fun subscribe(view: View, pinState: PinState?)
        fun getPinState(): PinState?
        fun createPinAdapter(): PinAdapter
    }

    interface View : BaseView {
        fun updatePinField(pinLen: Int = 0)
        fun showOrHideBackspaceButton(isVisible: Boolean)
        fun showOrHideResetButton(isVisible: Boolean)
        fun moveToLogInActivity()
        fun setTitleText(@StringRes titleTextResId: Int)
        fun showPopupMessage(@StringRes popupTextResId: Int)
    }
}