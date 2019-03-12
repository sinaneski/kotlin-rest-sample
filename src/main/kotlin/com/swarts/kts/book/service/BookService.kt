package com.swarts.kts.book.service

import com.swarts.kts.book.dto.BookRequest
import com.swarts.kts.book.dto.BookResponse
import com.swarts.kts.book.excetption.BadRequestException
import com.swarts.kts.book.excetption.BookAlreadyExistException
import com.swarts.kts.book.excetption.BookNotFoundException
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

    @Throws(BookNotFoundException::class)
    fun getBookByIsbn(isbn: String) : BookResponse{

        val entity = repository.findByIsbn(isbn)

        if(!entity.isPresent) { throw BookNotFoundException("No book found for $isbn") }

        return transformer.transform(entity.get())
    }

    @Throws(BookAlreadyExistException::class)
    fun saveBook(book: BookRequest) {

        val entity = repository.findByIsbn(book.isbn)

        if(entity.isPresent) { throw BookAlreadyExistException("Book already exist: $book") }

        repository.save(transformer.transform(book))
    }

    @Throws(BookNotFoundException::class, BadRequestException::class)
    fun updateBook(isbn: String, book: BookRequest) {

        if(isbn != book.isbn) { throw BadRequestException("The isbn = $isbn and book information (book.isbn = ${book.isbn}) is not match") }

        val entity = repository.findByIsbn(isbn)

        if(!entity.isPresent) { throw BookNotFoundException("No book found for ${isbn}") }

        repository.save(transformer.transform(book, entity))
    }

    @Throws(BookNotFoundException::class)
    fun deleteBook(isbn: String) {

        val entity = repository.findByIsbn(isbn)

        if(!entity.isPresent) { throw BookNotFoundException("No book found for $isbn") }

        repository.deleteByIsbn(isbn)
    }
}
