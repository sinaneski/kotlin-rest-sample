package com.swarts.kts.book.controller

import com.swarts.kts.book.dto.ErrorResponse
import com.swarts.kts.book.excetption.BadRequestException
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.excetption.BookNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ExceptionController : ResponseEntityExceptionHandler() {

    private val log = KotlinLogging.logger {}

    @ExceptionHandler(value = [(BookNotFoundException::class)])
    fun handleBookNotFoundException(ex: BookNotFoundException, request: WebRequest? = null): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(ex.message!!)

        log.info { "BookNotFoundException: $errorResponse" }

        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [(BookAlreadyExistException::class)])
    fun handleBookAlreadyExistException(ex: BookAlreadyExistException, request: WebRequest? = null): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(ex.message!!)

        log.info { "BookAlreadyExistException: $errorResponse" }

        return ResponseEntity(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(value = [(BadRequestException::class)])
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest? = null): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(ex.message!!)

        log.info { "BadRequestException: $errorResponse" }

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}