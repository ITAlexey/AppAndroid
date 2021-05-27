package com.example.simpleapp.contracts

import com.example.simpleapp.BasePresenter
import com.example.simpleapp.BaseView

interface MainActivityContract {
    interface View: BaseView {
        fun openSettingsDialog()
    }

    interface Presenter: BasePresenter<View> {
        fun onSettingsButtonClicked()
    }
}