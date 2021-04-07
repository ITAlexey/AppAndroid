package com.example.simpleapp.presenters

import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.contracts.LogInActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.PinState

class LogInActivityPresenter(
    private val view: LogInActivityContract.View,
    private val pinModel: PinModel
) :
    LogInActivityContract.Presenter {

    override fun onLogOutButtonClicked(activity: AppCompatActivity) {
        pinModel.updatePinState(PinState.LOGOUT)
        activity.finish()
    }

    override fun onViewCreated() {
        val result = getSumResultPinNumbers()
        view.showSumResult(result)
    }

    private fun getSumResultPinNumbers(): String =
        pinModel.permanentPin!!.map { Integer.valueOf(it.toString()) }.sum().toString()
}