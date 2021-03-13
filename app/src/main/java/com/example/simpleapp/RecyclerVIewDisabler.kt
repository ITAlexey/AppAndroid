package com.example.simpleapp

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class RecyclerVIewDisabler : RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean = true

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }
}