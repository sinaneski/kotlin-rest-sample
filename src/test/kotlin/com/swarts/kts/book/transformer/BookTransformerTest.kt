package com.swarts.kts.book.transformer

import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.entity.BookEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

internal class BookTransformerTest {

    @Test
    fun `given entity when transform to respose than return valid response`() {

        val entity = buildBookEntity()
        val response = buildBookResponse()
        val transformer = BookTransformer()

        assertThat(transformer.transform(entity)).isEqualTo(response)
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