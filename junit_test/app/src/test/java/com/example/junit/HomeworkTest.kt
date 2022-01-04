package com.example.junit

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HomeworkTest {

    @Test
    fun `fib(0) returns 0`(){
        val resultLong = Homework.fib(0)
        assertThat(resultLong).isEqualTo(0L)
    }

    @Test
    fun `fib(1) returns 1`() {
        val resultLong = Homework.fib(1)
        assertThat(resultLong).isEqualTo(1L)
    }

    @Test
    fun `fib(n) returns fib(n-2)+fib(n+1)`() {
        val resultLong = Homework.fib(2)
        assertThat(resultLong).isEqualTo(3L)
    }

    @Test
    fun `()) return false`() {
        val resultBool = Homework.checkBraces("())")
        assertThat(resultBool).isFalse()
    }

    @Test
    fun `() return true`() {
        val resultBool = Homework.checkBraces("()")
        assertThat(resultBool).isTrue()
    }

    @Test
    fun `((()) return false`() {
        val resultBool = Homework.checkBraces("((())")
        assertThat(resultBool).isFalse()
    }
}