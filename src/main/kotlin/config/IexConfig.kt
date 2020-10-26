package ca.jrvs.trading.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("iex")
data class IexConfig(val host: String, val token: String)