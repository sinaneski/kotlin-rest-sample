package com.swarts.kts.book.service

import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.repository.BookRepository
import com.swarts.kts.book.transformer.BookTransformer
import org.springframework.stereotype.Service

@Service
class BookService (private val repository: BookRepository) {

    private val transformer = BookTransformer()

    fun getBooks() : List<BookResponse>{

        val entities =  repository.findAll()

        return entities.map { transformer.transform(it) }
    }
}
