package ca.jrvs.trading.controller

import ca.jrvs.trading.domain.Quote
import ca.jrvs.trading.domain.iex.IexQuoteDto
import ca.jrvs.trading.service.QuoteService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@Api(value = "quote", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/quote")
class QuoteController(val quoteService: QuoteService) {

    @ApiOperation(value = "Show the dailyList", notes = "Show dailyList for this trading system.")
    @GetMapping(path = ["/dailyList"])
    @ResponseStatus(HttpStatus.OK)
    fun getDailyList(): List<Quote> {
        return quoteService.findAllQuotes()
    }

    @ApiOperation(value = "Show IexQuote", notes = "Show IexQuote for a given ticker/symbol")
    @GetMapping(path = ["/iex/ticker/{ticker}"])
    @ResponseStatus(HttpStatus.OK)
    fun getIexQuote(@PathVariable ticker: String): IexQuoteDto? {
        return quoteService.findIexQuoteByTicker("RY")
    }

    @ApiOperation(value = "Update quote table using IEX data", notes = "Update all quotes in the quote table. Use IEX trading API as market data source.")
    @PutMapping(path = ["/updateAll"])
    @ResponseStatus(HttpStatus.OK)
    fun updateMarketData(): List<Quote> {
        return quoteService.updateMarketData()
    }

    @ApiOperation(value = "Update a given quote in the quote table", notes = "Manually update a quote in the quote table using IEX market data.")
    @PutMapping(path = ["/update"])
    @ResponseStatus(HttpStatus.OK)
    fun putQuote(@RequestBody quote: Quote): Quote {
        return quoteService.saveQuote(quote)
    }

    @ApiOperation(value = "Add a new ticker to the dailyList (quote table)", notes = "Add a new ticker/symbol to the quote table, so trader can trade this security.")
    @PostMapping(path = ["/tickerId/{tickerId}"])
    @ResponseStatus(HttpStatus.CREATED)
    fun addQuoteByTicker(tickerId: String): Quote? {
        return quoteService.saveQuote(tickerId)
    }
}