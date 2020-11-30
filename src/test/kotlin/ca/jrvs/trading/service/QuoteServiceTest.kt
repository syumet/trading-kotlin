package ca.jrvs.trading.service

import ca.jrvs.trading.getMockQuoteRbc
import ca.jrvs.trading.getMockQuoteShop
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

@SpringBootTest
@Sql("classpath:schema.sql")
class QuoteServiceTest(@Autowired val quoteService: QuoteService) {

    @Test
    fun updateMarketData() {
        quoteService.saveQuote(getMockQuoteRbc())
        quoteService.saveQuote(getMockQuoteShop())

        val quotes = quoteService.findAllQuotes()
        assertEquals(2, quotes.size)

        val updatedQuotes = quoteService.updateMarketData()
        assertEquals(2, updatedQuotes.size)
    }
}