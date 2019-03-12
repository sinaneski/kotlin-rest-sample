package com.swarts.kts.book.controller

import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(private val service: BookService) {

    @GetMapping(value = ["/books"])
    fun getBooks() : List<BookResponse> {

        return service.getBooks()
    }
}