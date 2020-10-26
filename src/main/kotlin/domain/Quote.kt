package ca.jrvs.trading.domain

import javax.persistence.*

@Entity
data class Quote(
        @Id
        val ticker: String,

        @Column(name = "last_price")
        val lastPrice: Double,

        @Column(name = "bid_price")
        val bidPrice: Double,

        @Column(name = "bid_size")
        val bidSize: Int,

        @Column(name = "ask_price")
        val askPrice: Double,

        @Column(name = "ask_size")
        val askSize: Int
//        val orderList: List<SecurityOrder>
) : WithId<String> {
    override fun getId(): String {
        return ticker
    }
}