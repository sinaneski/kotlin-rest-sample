package com.swarts.kts.book.dto

import com.swarts.kts.book.transformer.objectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import java.time.LocalDate


internal class BookRequestTest {

    private val objectMapper = objectMapper()

    @Test
    fun `given book when write to json than return valid json` () {

        val actualBook = buildBookRequest()

        val expectedBook = readBookFromFile("book-request.json")

        assertThat(objectMapper.writeValueAsString(actualBook)).isEqualTo(objectMapper.writeValueAsString(expectedBook))
    }

    @Test
    fun `given valid json file when read from json than return valid book` () {
        val expectedBook = buildBookRequest()

        val actualBook = readBookFromFile("book-request.json")

        assertThat(actualBook).isEqualTo(expectedBook)
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

    private fun readBookFromFile(filename: String) : BookRequest {
        return objectMapper.readValue(ClassPathResource(filename).inputStream, BookRequest::class.java)
    }
}