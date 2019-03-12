package com.swarts.kts.book.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.ErrorResponse
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.excetption.BookNotFoundException
import com.swarts.kts.book.service.BookService
import com.swarts.kts.book.transformer.objectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest
internal class ExceptionControllerITest {

    @Autowired
    private lateinit var mockMvc : MockMvc

    @MockBean
    private lateinit var service: BookService


    @Test
    fun `given book is not exist when book requested with isbn then return error` ()  {

        val isbn = "1234"
        val exception = BookNotFoundException("Not found $isbn")

        val errorResponse = ErrorResponse(exception.message!!)

        Mockito.`when`(service.getBookByIsbn(isbn)).thenThrow(exception)

        mockMvc.perform(get("/books/1234"))
                .andExpect(status().isNotFound)
                .andExpect(content().json(objectMapper().writeValueAsString(errorResponse)))
    }

    @Test
    fun `given the book is already exist in database when add a book then return error` ()  {

        val bookRequest = loadBookRequest()
        val exception = BookAlreadyExistException("Book already exist with isbn = ${bookRequest.isbn}")
        val errorResponse = ErrorResponse(exception.message!!)

        Mockito.`when`(service.saveBook(bookRequest)).thenThrow(exception)

        mockMvc.perform(post("/books")
                .content(objectMapper().writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnprocessableEntity)
                .andExpect(content().json(objectMapper().writeValueAsString(errorResponse)))
    }

    @Test
    fun `given the book is not exist in database when update a book then update return error` ()  {

        val bookRequest = loadBookRequest()
        val exception = BookNotFoundException("No book found with isbn = ${bookRequest.isbn}")
        val errorResponse = ErrorResponse(exception.message!!)

        Mockito.`when`(service.updateBook(bookRequest.isbn, bookRequest)).thenThrow(exception)

        mockMvc.perform(put("/books/${bookRequest.isbn}")
                .content(objectMapper().writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound)
                .andExpect(content().json(objectMapper().writeValueAsString(errorResponse)))
    }

    @Test
    fun `given the book isbn and request isbn mismatch in database when update a book then update return error` ()  {

        val bookRequest = loadBookRequest()
        val exception = BookNotFoundException("No book found with isbn = ${bookRequest.isbn}")
        val errorResponse = ErrorResponse(exception.message!!)

        Mockito.`when`(service.updateBook("5678", bookRequest)).thenThrow(exception)

        mockMvc.perform(put("/books/5678")
                .content(objectMapper().writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound)
                .andExpect(content().json(objectMapper().writeValueAsString(errorResponse)))
    }

    @Test
    fun `given the book is not exist in database when delete a book then return error` ()  {

        val isbn = "1234"
        val exception = BookNotFoundException("No book found with isbn = ${isbn}")
        val errorResponse = ErrorResponse(exception.message!!)

        Mockito.`when`(service.deleteBook(isbn)).thenThrow(exception)

        mockMvc.perform(delete("/books/$isbn"))
                .andExpect(status().isNotFound)
                .andExpect(content().json(objectMapper().writeValueAsString(errorResponse)))
    }

    private fun loadBookRequest() : BookRequest {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-request.json").inputStream)
    }
}