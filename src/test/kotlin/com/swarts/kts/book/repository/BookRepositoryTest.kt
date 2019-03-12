package com.swarts.kts.book.repository

import com.swarts.kts.book.entity.BookEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate


@RunWith(SpringRunner::class)
@DataJpaTest
internal class BookRepositoryTest {

    @Autowired
    private val entityManager: TestEntityManager? = null


    @Autowired
    private val repository: BookRepository? = null

    @Test
    fun `given books exist in database when request all books then return all record`() {
        val expectedBookEntity = bookEntity()
        entityManager!!.persist(expectedBookEntity)

        val entity = repository!!.findAll()

        assertThat(entity.first()).isEqualTo(expectedBookEntity)
    }

    private fun bookEntity(): BookEntity {
        return BookEntity(
                "1234",
                "Book Title 1",
                "Author Test",
                "PUBS1",
                1,
                LocalDate.parse("2019-01-29"),
                LocalDate.parse("2019-01-29")
        )
    }

    @Test
    fun `given book exist in database when request with isbn then return the correct record`() {
        val expectedBookEntity = bookEntity()
        entityManager!!.persist(expectedBookEntity)

        val entity = repository!!.findByIsbn("1234")

        assertThat(entity.get()).isEqualTo(expectedBookEntity)
    }

    @Test
    fun `given book not exist in database when request with isbn then return null`() {

        val entity = repository!!.findByIsbn("1234")

        assertThat(entity.isPresent).isFalse()
    }

    @Test
    fun `given no book exist in database when request all then return empty`() {

        val entity = repository!!.findAll()

        assertThat(entity).isEmpty()
    }

}