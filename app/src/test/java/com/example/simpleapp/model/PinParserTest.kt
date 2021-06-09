package com.example.simpleapp.model

import com.example.simpleapp.models.pincode.PinParser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PinParserTest {

    @Test
    fun `checkOnSimplicity _ when pin consists of same digits`() {
       Assertions.assertTrue(PinParser.checkOnSimplicity(SAME_DIGITS))
    }

    @Test
    fun `checkOnSimplicity _ when pin consists of digits increased by one`() {
        Assertions.assertTrue(PinParser.checkOnSimplicity(INCREASED_DIGITS))
    }

    @Test
    fun `checkOnSimplicity _ when pin consists of digits decreased by one`() {
        Assertions.assertTrue(PinParser.checkOnSimplicity(DECREASED_DIGITS))
    }

    @Test
    fun `checkOnSimplicity _ when pin is valid`() {
        Assertions.assertFalse(PinParser.checkOnSimplicity(VALID_PIN))
    }

    companion object {
        private const val SAME_DIGITS = "1111"
        private const val INCREASED_DIGITS = "1234"
        private const val DECREASED_DIGITS = "4321"
        private const val VALID_PIN = "1122"
    }
}