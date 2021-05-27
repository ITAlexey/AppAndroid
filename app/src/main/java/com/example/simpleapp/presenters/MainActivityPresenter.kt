package com.example.simpleapp.presenters

import com.example.simpleapp.contracts.MainActivityContract

class MainActivityPresenter(private val view: MainActivityContract.View) : MainActivityContract.Presenter {

    override fun onSettingsButtonClicked() {
         view.openSettingsDialog()
    }
}