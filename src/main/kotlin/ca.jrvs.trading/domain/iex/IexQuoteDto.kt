package ca.jrvs.trading.domain.iex

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IexQuoteDto(
        /*
         * Refers to the stock ticker.
         */
        val symbol: String? = null,

        /*
         * Refers to the company name.
         */
        val companyName: String? = null,

        /*
         * Refers to the source of the latest price. Possible values are "tops", "sip", "previousclose" or
         * "close"
         */
        val calculationPrice: String? = null,

        /*
         * Refers to the official open price from the SIP. 15 minute delayed (can be null, after 00:00 ET,
         * before 9:45 and weekends)
         */
        val open: Double? = null,

        /*
         * Refers to the official listing exchange time for the open from the SIP. 15 minute delayed
         */
        val openTime: Long? = null,

        /*
         * Refers to the official close price from the SIP. 15 minute delayed
         */
        val close: Double? = null,

        /*
         * Refers to the official listing exchange time for the close from the SIP. 15 minute delayed
         */
        val closeTime: Long? = null,

        /*
         * Refers to the market-wide highest price from the SIP. 15 minute delayed during normal market
         * hours 9:30 - 16:00 (null, before 9:45 and weekends).
         */
        val high: Double? = null,

        /*
         * Refers to the market-wide lowest price from the SIP. 15 minute delayed during normal market
         * hours 9:30 - 16:00 (null, before 9:45 and weekends).
         */
        val low: Double? = null,

        /*
         * Use this to get the latest price
         */
        val latestPrice: Double? = null,

        /*
         * This will represent a human readable description of the source of latestPrice.
         *
         * @see IexQuote.latestPrice
         */
        val latestSource: String? = null,

        /*
         * Refers to a human readable time/date of when latestPrice was last updated. The format will vary
         * based on latestSource is intended to be displayed to a user. Use latestUpdate for machine
         * readable timestamp.
         *
         * @see IexQuote.latestSource
         *
         * @see IexQuote.latestUpdate
         */
        val latestTime: String? = null,

        /*
         * Refers to the machine readable epoch timestamp of when latestPrice was last updated.
         * Represented in milliseconds since midnight Jan 1, 1970.
         */
        val latestUpdate: Long? = null,

        /*
         * Use this to get the latest volume
         */
        val latestVolume: Long? = null,

        /*
         * Total volume for the stock, but only updated after market open. To get pre-market volume, use
         * latestVolume
         *
         * @see IexQuote.latestVolume
         */
        val volume: Long? = null,

        /*
         * Refers to the price of the last trade on IEX.
         */
        val iexRealtimePrice: Double? = null,

        /*
         * Refers to the size of the last trade on IEX.
         */
        val iexRealtimeSize: Int? = null,

        /*
         * Refers to the last update time of iexRealtimePrice in milliseconds since midnight Jan 1, 1970
         * UTC or -1 or 0. If the value is -1 or 0, IEX has not quoted the symbol in the trading day.
         */
        val iexLastUpdated: Long? = null,

        /*
         * Refers to the 15 minute delayed market price from the SIP during normal market hours 9:30 -
         * 16:00 ET.
         */
        val delayedPrice: Double? = null,

        /*
         * Refers to the last update time of the delayed market price during normal market hours 9:30 -
         * 16:00 ET.
         */
        val delayedPriceTime: Long? = null,

        /*
         * Refers to the 15 minute delayed odd Lot trade price from the SIP during normal market hours
         * 9:30 - 16:00 ET.
         */
        val oddLotDelayedPrice: Double? = null,

        /*
         * Refers to the last update time of the odd Lot trade price during normal market hours 9:30 -
         * 16:00 ET.
         */
        val oddLotDelayedPriceTime: Long? = null,

        /*
         * Refers to the 15 minute delayed price outside normal market hours 0400 - 0930 ET and 1600 -
         * 2000 ET. This provides pre market and post market price. This is purposefully separate from
         * latestPrice so users can display the two prices separately.
         *
         * @see IexQuote.latestPrice
         */
        val extendedPrice: Double? = null,

        /*
         * Refers to the price change between extendedPrice and latestPrice.
         */
        val extendedChange: Double? = null,

        /*
         * Refers to the price change percent between extendedPrice and latestPrice.
         */
        val extendedChangePercent: Double? = null,

        /*
         * Refers to the last update time of extendedPrice.
         *
         * @see IexQuote.extendedPrice
         */
        val extendedPriceTime: Long? = null,

        /*
         * Refers to the previous trading day closing price.
         */
        val previousClose: Double? = null,

        /*
         * Refers to the previous trading day volume.
         */
        val previousVolume: Long? = null,

        /*
         * Refers to the change in price between latestPrice and previousClose.
         */
        val change: Double? = null,

        /*
         * Refers to the percent change in price between latestPrice and previousClose. For example, a 5%
         * change would be represented as 0.05.
         */
        val changePercent: Double? = null,

        /*
         * Refers to IEX’s percentage of the market in the stock.
         */
        val iexMarketPercent: Double? = null,

        /*
         * Refers to shares traded in the stock on IEX.
         */
        val iexVolume: Long? = null,

        /*
         * Refers to the 30 day average volume.
         */
        val avgTotalVolume: Long? = null,

        /*
         * Refers to the best bid price on IEX.
         */
        val iexBidPrice: Double? = null,

        /*
         * Refers to amount of shares on the bid on IEX.
         */
        val iexBidSize: Int? = null,

        /*
         * Refers to the best ask price on IEX.
         */
        val iexAskPrice: Double? = null,

        /*
         * Refers to amount of shares on the ask on IEX.
         */
        val iexAskSize: Int? = null,

        /*
         * is calculated in real time using latestPrice.
         */
        val marketCap: Long? = null,

        /*
         * Refers to the adjusted 52 week high.
         */
        val week52High: Double? = null,

        /*
         * Refers to the adjusted 52 week low.
         */
        val week52Low: Double? = null,

        /*
         * Refers to the price change percentage from start of year to previous close.
         */
        val ytdChange: Double? = null,

        /*
         * Refers to the price-to-earnings ratio for the company.
         */
        val peRatio: Double? = null,

        /*
         * Epoch timestamp in milliseconds of the last market hours trade excluding the closing auction
         * trade.
         */
        val lastTradeTime: Long? = null,

        /*
         * For US stocks, indicates if the market is in normal market hours. Will be false during extended
         * hours trading.
         */
        val isUSMarketOpen: Boolean? = null

)