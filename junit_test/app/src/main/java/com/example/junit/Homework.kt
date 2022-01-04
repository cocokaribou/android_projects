package com.example.junit

object Homework {

    /**
     * Return the n-th fibonacci number
     * They are defined like this:
     * fib(0) = 0
     * fib(1) = 1
     * fib(n) = fib(n-2) + fib(n-1)
     */
    fun fib(n: Int): Long {
        if (n <= 1) {
            return n.toLong()
        }
        return fib(n - 1) + fib(n - 2)
    }

    /**
     * Checks if the braces are set correctly
     * e.g. "(a*b))" should return false
     */
    fun checkBraces(string: String): Boolean {
        return string.count { it == '(' } == string.count{ it == ')'}
    }
}