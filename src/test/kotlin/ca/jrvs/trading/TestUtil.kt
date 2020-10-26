package ca.jrvs.trading

import ca.jrvs.trading.domain.Quote

class TestUtil

val NOT_A_TICKER = "FB2";

fun getMockQuoteShop(): Quote {
    return Quote("SHOP", 1000.1, 1000.2, 10, 1000.0, 10)
}

fun getMockQuoteRbc(): Quote {
    return Quote("RY", 100.1, 100.2, 100, 100.0, 100)
}