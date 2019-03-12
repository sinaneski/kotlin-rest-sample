package com.swarts.kts.book.controller

import com.swarts.kts.book.dto.ErrorResponse
import com.swarts.kts.book.excetption.BadRequestException
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.excetption.BookNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

import org.springframework.http.HttpStatus

class ExceptionControllerTest {

    @Test
    fun `given the book is not exist in database when request a book with isbn then return error` ()  {

        val exceptionController = ExceptionController()

        val exception = BookNotFoundException("No book found with isbn = 1234")
        val errorResponse = ErrorResponse(exception.message!!)

        val response = exceptionController.handleBookNotFoundException(exception)

        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body).isEqualTo(errorResponse)
    }

    @Test
    fun `given the book is already exist in database when add a book then return error` ()  {

        val exceptionController = ExceptionController()

        val exception = BookAlreadyExistException("Book already exist with isbn 1234")
        val errorResponse = ErrorResponse(exception.message!!)

        val response = exceptionController.handleBookAlreadyExistException(exception)

        assertThat(response.statusCode).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
        assertThat(response.body).isEqualTo(errorResponse)
    }

    @Test
    fun `given the book is mismatch with requested isbn when update a book then return error` ()  {

        val exceptionController = ExceptionController()

        val exception = BadRequestException("The isbn = 1234 and book information (book.isbn = 5678) is not match")
        val errorResponse = ErrorResponse(exception.message!!)

        val response = exceptionController.handleBadRequestException(exception)

        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body).isEqualTo(errorResponse)
    }
}