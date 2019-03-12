package com.swarts.kts.book.repository

import com.swarts.kts.book.entity.BookEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<BookEntity, Long>
