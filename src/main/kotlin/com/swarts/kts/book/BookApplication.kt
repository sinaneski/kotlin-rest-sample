package com.swarts.kts.book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookEntityServiceApplication

fun main(args: Array<String>) {
	runApplication<BookEntityServiceApplication>(*args)
}
