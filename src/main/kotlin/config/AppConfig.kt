package ca.jrvs.trading.config

import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import javax.annotation.PostConstruct

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties(IexConfig::class)
class AppConfig(val environment: Environment) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun httpClientConnectionManager(): HttpClientConnectionManager {
        val manager = PoolingHttpClientConnectionManager()
        manager.maxTotal = environment.getProperty("httpClient.poolMaxTotal", Int::class.java, 50)
        manager.defaultMaxPerRoute = environment.getProperty("httpClient.maxPerRoute", Int::class.java, 50)
        return manager
    }

    @PostConstruct
    fun debug() {
        logger.info("AppConfig initialized.")
    }

}