package com.swarts.kts.book.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import com.swarts.kts.book.transformer.objectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.springframework.core.io.ClassPathResource

internal class BookControllerTest {

    private lateinit var service: BookService
    private lateinit var controller: BookController

    @Before
    fun setUp() {
        service = Mockito.mock(BookService::class.java)
        controller = BookController(service)
    }

    @Test
    fun `given books exist when books requested then return all the records` ()  {

        val books = loadBookResponseList()

        Mockito.`when`(service.getBooks()).thenReturn(books)

        val bookResponse = controller.getBooks();

        assertThat(bookResponse).isEqualTo(books)
    }

    @Test
    fun `given book is exist when book requested with isbn then return the book` ()  {

        val isbn = "1234"
        val book = loadBookResponseList().first { it.isbn == isbn }

        Mockito.`when`(service.getBookByIsbn(isbn)).thenReturn(book)

        val bookResponse = controller.getBooks(isbn);

        assertThat(bookResponse).isEqualTo(book)
    }


    private fun loadBookResponseList() : List<BookResponse> {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-response-list.json").inputStream)
    }
}