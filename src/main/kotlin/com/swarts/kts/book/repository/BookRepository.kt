package com.swarts.kts.book.repository

import com.swarts.kts.book.entity.BookEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BookRepository : CrudRepository<BookEntity, Long> {
    fun findByIsbn(isbn: String) : Optional<BookEntity>
    fun deleteByIsbn(isbn: String)
}
