package ca.jrvs.trading.repository

import ca.jrvs.trading.NOT_A_TICKER
import ca.jrvs.trading.domain.Quote
import ca.jrvs.trading.getMockQuoteRbc
import ca.jrvs.trading.getMockQuoteShop
import org.springframework.beans.factory.annotation.Autowired

class QuoteRepositoryTest(@Autowired repo: QuoteRepository)
    : BaseJpaRepositoryTest<QuoteRepository, Quote, String>(repo) {

    override fun getTestEntity1(): Quote {
        return getMockQuoteRbc()
    }

    override fun getTestEntity2(): Quote {
        return getMockQuoteShop()
    }

    override fun modifyOneField(e: Quote): Quote {
        return e.copy(askSize = e.askSize + 1)
    }

    override fun getOneField(e: Quote): Any {
        return e.bidSize
    }

    override fun getNonExistId(): String {
        return NOT_A_TICKER
    }
}