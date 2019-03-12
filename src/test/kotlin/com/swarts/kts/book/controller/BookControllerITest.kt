package com.swarts.kts.book.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import com.swarts.kts.book.transformer.objectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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


    private fun loadBookResponse() : List<BookResponse> {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-response-list.json").inputStream)
    }

}