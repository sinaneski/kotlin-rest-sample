package com.swarts.kts.book.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "BOOK", schema = "BOOK_STORE")
data class BookEntity (

        @Id
        @Column(name = "ISBN", unique = true, nullable = false, length = 64)
        val isbn: String,

        @Column(name = "TITLE", nullable = false, length = 256)
        val title: String,

        @Column(name = "AUTHOR", nullable = false, length = 64)
        val author: String,

        @Column(name = "PUBLISHER", nullable = false, length = 64)
        val publisher: String,

        @Column(name = "EDITION", nullable = false)
        val edition: Int,

        @Column(name = "CREATION_DATE", nullable = false)
        val creationDate: LocalDate,

        @Column(name = "MODIFIED_DATE", nullable = false)
        val modifiedDate: LocalDate
)