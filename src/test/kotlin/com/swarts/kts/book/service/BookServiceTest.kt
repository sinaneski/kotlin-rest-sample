package com.swarts.kts.book.service

import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.entity.BookEntity
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.repository.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.time.LocalDate
import java.util.*

internal class BookServiceTest {


    private lateinit var service: BookService
    private lateinit var repository: BookRepository

    @Before
    fun setUp() {
        repository = Mockito.mock(BookRepository::class.java)
        service = BookService(repository)
    }

    @Test
    fun `given books exist when books requested then return all the records` ()  {

        val bookEntities = listOf(buildBookEntity())
        val expectedBookResponse = listOf(buildBookResponse())

        Mockito.`when`(repository.findAll()).thenReturn(bookEntities)

        val bookResponse = service.getBooks();

        assertThat(bookResponse).isEqualTo(expectedBookResponse)
    }

    @Test
    fun `given books exist when book requested with isbn then return the book` ()  {

        val isbn = "1234"
        val bookEntity = buildBookEntity()
        val expectedBookResponse = buildBookResponse()

        Mockito.`when`(repository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntity))

        val bookResponse = service.getBookByIsbn(isbn);

        assertThat(bookResponse).isEqualTo(expectedBookResponse)
    }

    @Test
    fun `given book is not exist in the db when add a new book then successfully store the book` ()  {

        val book = buildBookRequest()
        val bookEntity = buildBookEntity()

        Mockito.`when`(repository.save(bookEntity)).thenReturn(bookEntity)

        service.saveBook(book)

        Mockito.verify(repository).save(bookEntity)
    }


    @Test(expected = BookAlreadyExistException::class)
    fun `given book is exist in the db when add a book then throw already exist exception` ()  {

        val bookRequest = buildBookRequest()
        val bookEntity = buildBookEntity()

        Mockito.`when`(repository.findByIsbn(bookRequest.isbn)).thenReturn(Optional.of(bookEntity))
        Mockito.`when`(repository.save(bookEntity)).thenReturn(bookEntity)

        service.saveBook(bookRequest)

        Mockito.verify(repository).save(bookEntity)
    }

    private fun buildBookRequest() : BookRequest {
        return BookRequest (
                "1234",
                "Book Title 1",
                "Author Test",
                "PUBS1",
                1,
                LocalDate.parse("2019-01-29")
        )
    }

    private fun buildBookResponse() : BookResponse {
        return BookResponse (
                "1234",
                "Book Title 1",
                "Author Test",
                "PUBS1",
                1,
                LocalDate.parse("2019-01-29"),
                LocalDate.parse("2019-01-29")
        )
    }

    private fun buildBookEntity() : BookEntity {
        return BookEntity (
                "1234",
                "Book Title 1",
                "Author Test",
                "PUBS1",
                1,
                LocalDate.parse("2019-01-29"),
                LocalDate.parse("2019-01-29")
        )
    }
}
