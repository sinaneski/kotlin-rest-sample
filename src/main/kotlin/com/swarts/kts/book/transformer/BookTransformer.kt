package com.swarts.kts.book.transformer

import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.entity.BookEntity

class BookTransformer {
    fun transform(entity: BookEntity): BookResponse {
        return BookResponse(entity.isbn,
                entity.title,
                entity.author,
                entity.publisher,
                entity.edition,
                entity.creationDate,
                entity.modifiedDate)
    }
}