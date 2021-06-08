package com.example.simpleapp.model

import com.example.simpleapp.models.SharedPrefRepo
import com.example.simpleapp.models.pincode.PinModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test

class PinModelTest {
    private val sharedPrefRepo: SharedPrefRepo = mock()
    private val pinModel = PinModel(sharedPrefRepo)

    @Test
    fun `pinState initializing _ when getPin returns nothing _ verify state is create`() {

    }
}