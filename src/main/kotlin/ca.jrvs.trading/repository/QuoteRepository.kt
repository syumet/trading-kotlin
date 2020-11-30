package ca.jrvs.trading.repository

import ca.jrvs.trading.domain.Quote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuoteRepository: JpaRepository<Quote, String> {
}