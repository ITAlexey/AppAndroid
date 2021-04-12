package com.example.simpleapp

interface BasePresenter<in V: BaseView>{
    fun subscribe(view: V)
    fun unsubscribe()
}