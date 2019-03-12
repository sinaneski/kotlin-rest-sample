package com.swarts.kts.book.controller

import com.fasterxml.jackson.module.kotlin.readValue
import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.excetption.BadRequestException
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.excetption.BookNotFoundException
import com.swarts.kts.book.service.BookService
import com.swarts.kts.book.transformer.objectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
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

    @Test
    fun `given book is not exist in the db when add a new book then successfully store the book` ()  {

        val book = loadBookRequest()

        Mockito.doNothing().`when`(service).saveBook(book)

        controller.saveBook(book)

        verify(service).saveBook(book)
    }

    @Test(expected = BookAlreadyExistException::class)
    fun `given book is exist in the db when add a new book then throw a book already exist exception` ()  {

        val book = loadBookRequest()

        doThrow(BookAlreadyExistException("Book already exist: $book")).`when`(service).saveBook(book)

        controller.saveBook(book)

        verify(service).saveBook(book)
    }


    @Test
    fun `given book is exist in the db when update a book then update the record` ()  {

        val book = loadBookRequest()
        val isbn = book.isbn

        doNothing().`when`(service).updateBook(isbn, book)

        controller.updateBook(isbn, book)

        verify(service).updateBook(isbn, book)
    }

    @Test(expected = BookNotFoundException::class)
    fun `given book is not exist in the db when update a book then ex the record` ()  {

        val book = loadBookRequest()
        val isbn = book.isbn

        doThrow(BookNotFoundException("Book not found: ${book.isbn}")).`when`(service).updateBook(isbn, book)

        controller.updateBook(isbn, book)

        verify(service).updateBook(isbn, book)
    }

    @Test(expected = BadRequestException::class)
    fun `given book is not match with the isbn in the db when update a book then throw bad request exception` ()  {

        val isbn = "5678"

        val book = loadBookRequest()

        doThrow(BadRequestException("Isbn mismatch isbn = $isbn and book.isbn = ${book.isbn}")).`when`(service).updateBook(isbn, book)

        controller.updateBook(isbn, book)

        verify(service).updateBook(isbn, book)
    }


    @Test
    fun `given the book exist in database when request delete then delete it successfully`() {

        val isbn = "1234"

        doNothing().`when`(service).deleteBook(isbn)

        controller.deleteBook(isbn)

        verify(service).deleteBook(isbn)
    }

    @Test(expected = BookNotFoundException::class)
    fun `given the book not exist in database when request delete then throw not found exception`() {

        val isbn = "1234"

        doThrow(BookNotFoundException("Book not found: ${isbn}")).`when`(service).deleteBook(isbn)

        controller.deleteBook(isbn)

        verify(service).deleteBook(isbn)
    }

    private fun loadBookResponseList() : List<BookResponse> {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-response-list.json").inputStream)
    }

    private fun loadBookRequest() : BookRequest {

        val objectMapper = objectMapper()
        return objectMapper.readValue(ClassPathResource("book-request.json").inputStream)
    }

}