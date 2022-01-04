package com.example.junit

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "",
            "123",
            "123"
        )
        // truth library
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "Phillip",
            "123",
            "123"
        )
        assertThat(result).isTrue()
        assertThat("hello").isEqualTo("hello")
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "Peter",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "Peter",
            "",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `confirmed password does not match password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "Peter",
            "1234",
            "123"
        )
        assertThat(result).isFalse()

        // or..
//        assertThat("confirmedPw").matches("Pw")
    }

    @Test
    fun `password over 2 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput( // access to main source set
            "Peter",
            "123",
            "123"
        )
        // truth library
        assertThat(result).isFalse()
    }
}