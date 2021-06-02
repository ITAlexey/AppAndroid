package com.example.simpleapp.presenter

import android.os.Bundle
import com.example.simpleapp.contracts.PinCodeFragmentContract
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.presenters.PinCodePresenter
import com.nhaarman.mockitokotlin2.*

class PinCodePresenterTest {
    private val view: PinCodeFragmentContract.View = mock()
    private val model: PinModel = mock()
    private val bundle: Bundle = mock()
    private val presenter = PinCodePresenter(view, model)

    fun test1() {

    }
}