package ca.jrvs.trading.service

import ca.jrvs.trading.domain.Quote
import ca.jrvs.trading.domain.iex.IexQuoteDto
import ca.jrvs.trading.repository.IexRepository
import ca.jrvs.trading.repository.QuoteRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class QuoteService(val quoteRepository: QuoteRepository,
                   val iexRepository: IexRepository) {


    fun findIexQuoteByTicker(ticker: String): IexQuoteDto? {
        return iexRepository.findById(ticker)
    }

    fun updateMarketData(): List<Quote> {
        val tickers = quoteRepository.findAll().map { it.getId() }
        return iexRepository.findAllById(tickers)
                .filterNotNull()
                .mapNotNull { mapToQuote(it) }
                .map { quoteRepository.save(it) }
    }

    fun saveQuote(quote: Quote): Quote {
        return quoteRepository.save(quote)
    }

    fun saveQuote(ticker: String): Quote? {
        return ticker.let { iexRepository.findById(it) }
                ?.let { mapToQuote(it) }
                ?.let { quoteRepository.save(it) }
    }

    fun saveQuotes(tickers: List<String>): List<Quote> {
        return tickers.mapNotNull { this.saveQuote(it) }
    }

    fun findAllQuotes(): List<Quote> {
        return quoteRepository.findAll()
    }

    private fun mapToQuote(dto: IexQuoteDto): Quote? {
        return dto.symbol?.let {
            Quote(it, dto.latestPrice ?: 0.0, dto.iexBidPrice, dto.iexBidSize ?: 0, dto.iexAskPrice, dto.iexAskSize ?: 0)
        }
    }
}

