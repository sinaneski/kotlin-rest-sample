package com.swarts.kts.book.service

import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.entity.BookEntity
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
