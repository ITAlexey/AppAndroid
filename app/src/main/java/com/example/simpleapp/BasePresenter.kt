package com.example.simpleapp

interface BasePresenter<V: BaseView> {
    fun onViewCreated()
}