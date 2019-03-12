package com.swarts.kts.book.controller

import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.service.BookService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class BookController(private val service: BookService) {

    private val log = KotlinLogging.logger {}

    @GetMapping(value = ["/books"])
    fun getBooks() : List<BookResponse> {

        log.info { "Request all books" }

        val books = service.getBooks()

        log.info { "$books.size books returned" }

        return books
    }

    @GetMapping(value = ["/books/{isbn}"])
    fun getBooks(@PathVariable(name = "isbn") isbn: String) : BookResponse {

        log.info { "Request book with isbn = $isbn" }

        val book = service.getBookByIsbn(isbn)

        log.info { "Returned book info: $book" }

        return book
    }

    @PostMapping(value = ["books"])
    @ResponseStatus(HttpStatus.CREATED)
    fun saveBook(@RequestBody book: BookRequest) {

        log.info { "Request to add book = $book" }

        service.saveBook(book)

        log.info { "Book successfully added." }
    }

    @PutMapping(value = ["books/{isbn}"])
    fun updateBook(
            @PathVariable(name = "isbn") isbn: String,
            @RequestBody book: BookRequest) {

        log.info { "Request to add book = $book" }

        service.updateBook(isbn, book)

        log.info { "Book successfully updated." }
    }
}