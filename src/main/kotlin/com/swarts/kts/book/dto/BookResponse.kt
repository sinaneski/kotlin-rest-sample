package com.swarts.kts.book.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class BookResponse (

        val isbn: String,
        val title: String,
        val author: String,
        val publisher: String,
        val edition: Int,

        @get:JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Europe/London")
        val addedDate: LocalDate,

        @get:JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Europe/London")
        val updatedDate: LocalDate
)