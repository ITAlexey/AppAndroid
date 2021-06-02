package com.example.simpleapp.model

import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.SharedPrefRepo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PinModelTest {
    private val sharedPrefRepo: SharedPrefRepo = mock()
    private lateinit var model: PinModel

    @Before
    fun setUp() {
        model = PinModel(sharedPrefRepo)
    }

    @Test
    fun `addNumber check if not empty`() {
        model.addNumber(GOOD_PIN)
        Assert.assertTrue(model.isPinNotEmpty)
    }

    @Test
    fun `removeNumber delete one digit`() {
        model.addNumber(GOOD_PIN)
        model.removeNumber()
        Assert.assertEquals(3, model.pinLength)
    }

    @Test
    fun `createPinIfSuccess passing simple pin with same numbers`() {
        model.addNumber(SIMPLE_PIN_SAME)
        val result = model.createPinIfSuccess()
        Assert.assertFalse(result)
    }

    @Test
    fun `createPinIfSuccess passing simple pin with increasing number by one`() {
        model.addNumber(SIMPLE_PIN_INC)
        val result = model.createPinIfSuccess()
        Assert.assertFalse(result)
    }

    @Test
    fun `createPinIfSuccess passing simple pin with decreasing number by one`() {
        model.addNumber(SIMPLE_PIN_DEC)
        val result = model.createPinIfSuccess()
        Assert.assertFalse(result)
    }

    @Test
    fun `createPinIfSuccess passing good pin`() {
        model.addNumber(GOOD_PIN)
        val result = model.createPinIfSuccess()
        Assert.assertTrue(result)
    }

    @Test
    fun `deletePinIfSuccess verify that isPinCorrect has been called`() {
        model.addNumber(GOOD_PIN)
        whenever(sharedPrefRepo.isPinCorrect(GOOD_PIN.toString())).thenReturn(true)
        model.deletePinIfSuccess()
        verify(sharedPrefRepo, times(1)).isPinCorrect(GOOD_PIN.toString())
    }

    @Test
    fun `deletePinIfSuccess verify that removePin has been called`() {
        model.addNumber(GOOD_PIN)
        whenever(sharedPrefRepo.isPinCorrect(GOOD_PIN.toString())).thenReturn(true)
        model.deletePinIfSuccess()
        verify(sharedPrefRepo, times(1)).removePin()
    }

    @Test
    fun `deletePinIfSuccess correct case of pin`() {
        model.addNumber(GOOD_PIN)
        whenever(sharedPrefRepo.isPinCorrect(GOOD_PIN.toString())).thenReturn(true)
        val result = model.deletePinIfSuccess()
        Assert.assertTrue(result)
    }

    @Test
    fun `deletePinIfSuccess incorrect case of pin`() {
        model.addNumber(GOOD_PIN)
        whenever(sharedPrefRepo.isPinCorrect(SIMPLE_PIN_DEC.toString())).thenReturn(false)
        val result = model.deletePinIfSuccess()
        Assert.assertFalse(result)
    }

    @Test
    fun `savePinIfSuccess verify that savePin has been called`() {
        model.addNumber(GOOD_PIN)
        model.createPinIfSuccess()
        model.savePinIfSuccess()
        verify(sharedPrefRepo, times(1)).savePin(model.temporaryPin)
    }

    @Test
    fun `savePinIfSuccess case of fail`() {
        model.addNumber(SIMPLE_PIN_INC)
        model.createPinIfSuccess()
        val result = model.savePinIfSuccess()
        Assert.assertFalse(result)
    }

    @Test
    fun `savePinIfSuccess case of success`() {
        model.addNumber(GOOD_PIN)
        model.createPinIfSuccess()
        val result = model.savePinIfSuccess()
        Assert.assertTrue(result)
    }

    @Test
    fun `loginIfSuccess verify that isPinCorrect has been called`() {
        model.addNumber(GOOD_PIN)
        model.loginIfSuccess()
        verify(sharedPrefRepo, times(1)).isPinCorrect(model.temporaryPin)
    }

    @Test
    fun `calculateSumPinNumbers verify that getPin has been called`() {
        whenever(sharedPrefRepo.getPin()).thenReturn("2145")
        model.calculateSumPinNumbers()
        verify(sharedPrefRepo, times(1)).getPin()
    }

    @Test
    fun `isPinSaved verify that getPin has been called`() {
        whenever(sharedPrefRepo.getPin()).thenReturn("3333")
        model.isPinSaved
        verify(sharedPrefRepo, times(1)).getPin()
    }

    companion object {
        private const val SIMPLE_PIN_SAME = 1111
        private const val SIMPLE_PIN_INC = 1234
        private const val SIMPLE_PIN_DEC = 4321
        private const val GOOD_PIN = 1221
    }
}