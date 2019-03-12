package com.swarts.kts.book.controller

import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BookController(private val service: BookService) {

    @GetMapping(value = ["/books"])
    fun getBooks() : List<BookResponse> {

        return service.getBooks()
    }

    @GetMapping(value = ["/books/{isbn}"])
    fun getBooks(@PathVariable(name = "isbn") isbn: String) : BookResponse {

        return service.getBookByIsbn(isbn)
    }

    @PostMapping(value = ["books"])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveBook(@RequestBody book: BookRequest) {

        service.saveBook(book)
    }

}