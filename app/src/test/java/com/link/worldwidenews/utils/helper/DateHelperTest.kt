package com.link.worldwidenews.utils.helper

import org.junit.Assert.assertEquals
import org.junit.Test

class DateHelperTest {
    @Test
    fun ` 'convertDateFormatToAnother()' 'with  srcDateFormat equal null' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat: String? = null
        val srcDate = "08/12/2022"
        val destDateFormat = "yyyy-dd-MM"

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)


    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with   srcDateFormat and destDateFormat equal null' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat: String? = null
        val srcDate = "08/12/2022"
        val destDateFormat = null

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)


    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with  destDateFormat equal null' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat = "dd/MM/yyyy"
        val srcDate = "08/12/2022"
        val destDateFormat = null

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)


    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with   srcDateFormat contain illegal pattern' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat = "lllllllllll"
        val srcDate = "08/12/2022"
        val destDateFormat = "yyyy/MM/dd"

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)

    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with   destDateFormat contain illegal pattern' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat = "yyyy/MM/dd"
        val srcDate = "08/12/2022"
        val destDateFormat = "ppp"

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)

    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with   srcDateFormat contain wrong pattern' 'then return result equal srcDate'`() {
        //Arrange
        val srcDateFormat = "dd-MM-yyyy"
        val srcDate = "08/12/2022"
        val destDateFormat = "yyyy/MM/dd"

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, srcDate)

    }

    @Test
    fun ` 'convertDateFormatToAnother()' 'with   srcDateFormat with correct pattern' 'then return result equal destDate'`() {
        //Arrange
        val srcDateFormat = "dd/MM/yyyy"
        val srcDate = "08/12/2022"
        val destDateFormat = "yyyy-MM-dd"
        val destDate = "2022-12-08"

        //act
        val result = DateHelper.convertDateFormatToAnother(srcDateFormat, srcDate, destDateFormat)

        //assert
        assertEquals(result, destDate)

    }

}