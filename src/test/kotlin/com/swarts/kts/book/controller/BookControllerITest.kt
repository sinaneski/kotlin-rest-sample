package com.swarts.kts.book.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import com.swarts.kts.book.transformer.objectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
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
internal class BookControllerITest {

    @Autowired
    private lateinit var mockMvc : MockMvc

    @MockBean
    private lateinit var service: BookService

    @Test
    fun `given books exist when books requested than return all the records` ()  {

        val books = loadBookResponse()

        Mockito.`when`(service.getBooks()).thenReturn(books)

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk)
                .andExpect(content().json(objectMapper().writeValueAsString(books)))

    }

    @Test
    fun `given books is exist when book requested with isbn then return the book` ()  {

        val isbn = "1234"
        val bookResponse = loadBookResponse().first { it.isbn == isbn }

        Mockito.`when`(service.getBookByIsbn(isbn)).thenReturn(bookResponse)

        mockMvc.perform(get("/books/1234"))
                .andExpect(status().isOk)
                .andExpect(content().json(objectMapper().writeValueAsString(bookResponse)))
    }

    @Test
    fun `given the book is not exist in database when add a book then add book` ()  {

        val bookRequest = loadBookRequest()

        doNothing().`when`(service).saveBook(bookRequest)

        mockMvc.perform(post("/books")
                .content(objectMapper().writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated)
    }

    @Test
    fun `given the book is exist in database when update a book then update the  book` ()  {

        val bookRequest = loadBookRequest()
        val isbn = bookRequest.isbn


        doNothing().`when`(service).updateBook(isbn, bookRequest)

        mockMvc.perform(put("/books/$isbn")
                .content(objectMapper().writeValueAsString(bookRequest))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
    }


    @Test
    fun `given the book is exist in database when delete a book then delete the  book` ()  {

        val isbn = "1234"

        doNothing().`when`(service).deleteBook(isbn)

        mockMvc.perform(delete("/books/$isbn"))
                .andExpect(status().isOk)
    }


    private fun loadBookResponse() : List<BookResponse> {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-response-list.json").inputStream)
    }

    private fun loadBookRequest() : BookRequest {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-request.json").inputStream)
    }
}