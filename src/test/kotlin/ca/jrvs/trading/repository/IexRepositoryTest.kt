package ca.jrvs.trading.repository

import ca.jrvs.trading.config.IexConfig
import org.apache.http.conn.HttpClientConnectionManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IexRepositoryTest(@Autowired connectionManager: HttpClientConnectionManager, @Autowired config: IexConfig) {

    val repository = IexRepository(connectionManager, config)

    @Test
    fun findById() {
        val ticker1 = "FB"
        assertTrue(repository.existById(ticker1))

        val quote1 = repository.findById(ticker1)
        assertEquals(ticker1, quote1?.symbol)

        val ticker2 = "FB2"
        assertFalse(repository.existById(ticker2))
        assertNull(repository.findById(ticker2))
    }

    @Test
    fun findAllById() {
        val quoteList = repository.findAllById(listOf("FB", "AMZN"))
        assertEquals(2, quoteList.size)
    }

}