package ca.jrvs.trading.repository

import ca.jrvs.trading.domain.WithId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql("classpath:schema.sql")
abstract class BaseJpaRepositoryTest<R : JpaRepository<E, I>, E : WithId<I>, I : Any>(val repo: R) {

    abstract fun getTestEntity1(): E

    abstract fun getTestEntity2(): E

    abstract fun modifyOneField(e: E): E

    abstract fun getOneField(e: E): Any?

    abstract fun getNonExistId(): I

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var savedEntity1: E
    lateinit var savedEntity2: E

    lateinit var savedId1: I
    lateinit var savedId2: I

    @BeforeEach
    internal fun setUp() {
        savedEntity1 = repo.save(getTestEntity1())
        savedEntity2 = repo.save(getTestEntity2())
        savedId1 = savedEntity1.getId()
        savedId2 = savedEntity2.getId()
    }

    @Test
    fun find() {
        val all = repo.findAll()
        assertEquals(2, all.size)

        val entity1 = repo.findById(savedId1)
        assertTrue(entity1.isPresent)
        assertEquals(savedEntity1, entity1.get())
        logger.info(entity1.toString())

        val entity2 = repo.findById(savedId2)
        assertTrue(entity2.isPresent)
        assertEquals(savedEntity2, entity2.get())
        logger.info(entity2.toString())

        val entity3 = repo.findById(getNonExistId())
        assertFalse(entity3.isPresent)
    }

    @Test
    fun delete() {
        assertEquals(2, repo.count())
        assertTrue(repo.existsById(savedId1))
        assertTrue(repo.existsById(savedId2))

        repo.deleteById(savedId1)
        assertEquals(1, repo.count())
        assertFalse(repo.existsById(savedId1))
        assertTrue(repo.existsById(savedId2))

        repo.delete(savedEntity2)
        assertEquals(0, repo.count())
        assertFalse(repo.existsById(savedId2))

        repo.delete(savedEntity1)
        try {
            repo.deleteById(savedId2)
            fail("Repository should be empty, EmptyResultDataAccessException expected")
        } catch (ignored: EmptyResultDataAccessException) {
        }
    }

    @Test
    fun update() {
        val all = repo.findAll()
        val modifiedEntity = modifyOneField(all[0])
        all[0] = modifiedEntity
        repo.saveAll(all)

        val e1 = repo.findById(all[0].getId())
        assertTrue(e1.isPresent)
        assertEquals(getOneField(modifiedEntity), getOneField(e1.get()))

        val e2 = repo.findById(all[1].getId())
        assertTrue(e2.isPresent)
        assertEquals(savedEntity2, e2.get())
    }

}