package ca.jrvs.trading.repository

import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
abstract class HttpRepository<E, ID>(private val connectionManager: HttpClientConnectionManager)
    : Repository<E, ID> {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private fun getHttpClient(): CloseableHttpClient {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setConnectionManagerShared(true)
                .build()
    }

    protected fun executeHttpGet(url: String): String? {
        val response = getHttpClient().execute(HttpGet(url))
        val bodyString: String = EntityUtils.toString(response.entity)
        return when (val status = response.statusLine.statusCode) {
            HttpStatus.SC_OK -> bodyString
            HttpStatus.SC_NOT_FOUND -> {
                logger.warn("Required resource not found, Optional.empty will be returned.")
                null
            }
            else -> {
                logger.info(bodyString)
                throw DataRetrievalFailureException(
                        "Unexpected HTTP status: $status See https://iexcloud.io/docs/api/#error-codes"
                )
            }
        }
    }
}