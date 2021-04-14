package com.example.simpleapp.presenters

import androidx.appcompat.app.AppCompatActivity
import com.example.simpleapp.contracts.LogInActivityContract
import com.example.simpleapp.models.PinModel
import com.example.simpleapp.models.utils.PinState

class LogInActivityPresenter(
    private val view: LogInActivityContract.View,
    private val pinModel: PinModel
) :
    LogInActivityContract.Presenter {

    override fun onLogOutButtonClicked(activity: AppCompatActivity) {
        activity.finish()
    }

    override fun subscribe(view: LogInActivityContract.View) {
        TODO("Not yet implemented")
    }

    override fun unsubscribe() {
        TODO("Not yet implemented")
    }

    fun onViewCreated() {
        val result = pinModel.calculateSumPinNumbers()
        view.showSumResult(result)
    }

}