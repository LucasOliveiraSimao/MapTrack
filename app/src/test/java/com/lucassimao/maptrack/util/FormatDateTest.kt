package com.lucassimao.maptrack.util

import junit.framework.TestCase
import org.junit.Test

class FormatDateTest{

    @Test
    fun `test with 1682290800000 seconds  (23-04-2023)`() {

        val totalTime = 1682290800000L

        val expected = "23/04/23"
        val actual =formatDate(totalTime)

        TestCase.assertEquals(expected, actual)
    }
}