package ca.jrvs.trading.domain

interface WithId<Id: Any> {
    fun getId(): Id
}