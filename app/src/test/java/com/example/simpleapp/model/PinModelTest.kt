package com.example.simpleapp.model

import com.example.simpleapp.models.SharedPrefRepo
import com.example.simpleapp.models.pincode.EncryptionUtils
import com.example.simpleapp.models.pincode.PinModel
import com.example.simpleapp.models.pincode.PinState
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PinModelTest {
    private val sharedPrefRepo: SharedPrefRepo = mock()

    private fun initModel(returnedValue: String): PinModel {
        whenever(sharedPrefRepo.getStringByKey(any(), any())).thenReturn(returnedValue)
        mockkObject(EncryptionUtils)
        every { EncryptionUtils.decryptData(any()) }.returns(returnedValue)
        return PinModel(sharedPrefRepo)
    }


    @Test
    fun `pinState initializing _ when getPin is called _ verify getStringByKey is called`() {
        initModel(EMPTY_STRING)

        verify(sharedPrefRepo, times(1)).getStringByKey(any(), any())
    }

    @Test
    fun `pinState initializing _ when getPin returns empty line _ verify pinState is create`() {
        val model = initModel(EMPTY_STRING)

        Assertions.assertEquals(PinState.CREATE, model.pinState)
    }

    @Test
    fun `pinState initializing _ when getPin returns non empty line _ verify pinState is create`() {
        val model = initModel(NON_EMPTY_STRING)

        Assertions.assertEquals(PinState.LOGOUT, model.pinState)
    }

    @Test
    fun `updatePinState _ when passed values is login _ verify pin state has changed`() {
        val model = initModel(NON_EMPTY_STRING)

        model.updatePinState(PinState.LOGIN)

        Assertions.assertEquals(PinState.LOGIN, model.pinState)
    }

    @Test
    fun `addNumber _ when digit is passed _ verify temporary pin length was increased`() {
        val model = initModel(NON_EMPTY_STRING)

        model.addNumber(DIGIT_ONE)

        Assertions.assertTrue(model.isPinNotEmpty)
    }

    @Test
    fun `removeNumber _ when length of temp pin is one _ verify pin length was decreased`() {
        val model = initModel(NON_EMPTY_STRING)

        model.addNumber(DIGIT_ONE)
        model.removeNumber()

        Assertions.assertTrue(model.isPinEmpty)
    }

    @Test
    fun `calculateSumPinNumbers _ verify the result is correct`() {
        val model = initModel(PIN_NUMBERS)

        Assertions.assertEquals(PIN_NUMBERS_SUM, model.calculateSumPinNumbers())
    }

    @Test
    fun `resetPin _ verify temporary pin is empty`() {
        val model = initModel(NON_EMPTY_STRING)

        model.addNumber(PIN_NUMBERS.toInt())
        model.resetPin()

        Assertions.assertTrue(model.isPinEmpty)
    }

    @Test
    fun `updateProcessedPinStatus _ verify pin status was updated`() {
        val model = initModel(NON_EMPTY_STRING)

        model.updateProcessedPinStatus(SUCCESS, PinState.CONFIRM)

        Assertions.assertEquals(SUCCESS to PinState.CONFIRM, model.processedPinStatus)
    }

    @Test
    fun `savePin _ verify putStringByKey is called`() {
        val model = initModel(NON_EMPTY_STRING)
        mockkObject(EncryptionUtils)
        every { EncryptionUtils.encryptData(any()) }.returns(NON_EMPTY_STRING)

        model.savePin()

        verify(sharedPrefRepo, times(1)).putStringByKey(KEY_PIN_CODE, NON_EMPTY_STRING)
    }

    @Test
    fun `removePin _ verify removeDataByKey is called`() {
        val model = initModel(NON_EMPTY_STRING)

        model.removePin()

        verify(sharedPrefRepo, times(1)).removeDataByKey(KEY_PIN_CODE)
    }


    companion object {
        private const val NON_EMPTY_STRING = "1234"
        private const val EMPTY_STRING = ""
        private const val DIGIT_ONE = 1
        private const val PIN_NUMBERS = "2267"
        private const val PIN_NUMBERS_SUM = 17
        private const val SUCCESS = true
        private const val KEY_PIN_CODE = "KEY_PIN_CODE"
    }
}