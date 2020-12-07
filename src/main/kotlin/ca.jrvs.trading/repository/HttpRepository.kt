package ca.jrvs.trading.repository

import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriUtils

@NoRepositoryBean
abstract class HttpRepository<E, ID>(private val connectionManager: HttpClientConnectionManager)
    : Repository<E, ID> {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    abstract fun findAllById(ids: List<ID>): List<E?>

    fun findById(id: ID): E? {
        return findAllById(listOf(id)).firstOrNull()
    }

    fun existById(id: ID): Boolean {
        return findById(id) != null
    }

    private fun getHttpClient(): CloseableHttpClient {
        return HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setConnectionManagerShared(true)
                .build()
    }

    protected fun executeHttpGet(uriBuilder: URIBuilder): String? {
        val url = uriBuilder.toString()
        val response = getHttpClient().execute(HttpGet(url))
        val bodyString = EntityUtils.toString(response.entity)
        return when (val status = response.statusLine.statusCode) {
            HttpStatus.SC_OK -> bodyString
            HttpStatus.SC_NOT_FOUND -> {
                logger.warn("Required resource not found, Optional.empty will be returned.")
                null
            }
            else -> {
                logger.info("Request : $url")
                logger.info("Request : $bodyString")
                throw DataRetrievalFailureException(
                        "Unexpected HTTP status: $status See https://iexcloud.io/docs/api/#error-codes"
                )
            }
        }
    }


}