package com.swarts.kts.book.transformer

import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.entity.BookEntity
import java.util.*

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

    fun transform(bookRequest: BookRequest, bookEntity: Optional<BookEntity> = Optional.empty()): BookEntity {

        return BookEntity(bookRequest.isbn,
                bookRequest.title,
                bookRequest.author,
                bookRequest.publisher,
                bookRequest.edition,
                if(bookEntity.isPresent) bookEntity.get().creationDate else bookRequest.operationDate,
                bookRequest.operationDate)
    }
}