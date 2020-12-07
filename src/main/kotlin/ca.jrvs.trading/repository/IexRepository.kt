package ca.jrvs.trading.repository

import ca.jrvs.trading.config.IexConfig
import ca.jrvs.trading.domain.iex.IexQuoteDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.utils.URIBuilder
import org.apache.http.conn.HttpClientConnectionManager
import org.json.JSONObject
import org.springframework.stereotype.Repository

@Repository
class IexRepository(connectionManager: HttpClientConnectionManager, val config: IexConfig)
    : HttpRepository<IexQuoteDto, String>(connectionManager) {

    override fun findAllById(ids: List<String>): List<IexQuoteDto?> {
        val uriBuilder = URIBuilder(config.host + "/stock/market/batch")
                .addParameter("symbols", ids.joinToString(","))
                .addParameter("types", "quote")
                .addParameter("token", config.token)
//        val iexBatchUrl = config.host + "/stock/market/batch?symbols=%s&types=quote&token=" + config.token
//        val url = String.format(iexBatchUrl, ids.joinToString(","))
        val jsonString = executeHttpGet(uriBuilder)
        return jsonString?.let {
            ids
                    .map { id -> getFromBatchResultById(JSONObject(it), id) }
                    .map(this::mapJsonToEntity)
        } ?: emptyList()
    }

    private fun mapJsonToEntity(json: String): IexQuoteDto {
        return ObjectMapper().readValue(json, IexQuoteDto::class.java)
    }

    private fun getFromBatchResultById(json: JSONObject, id: String): String {
        return json
                .getJSONObject(id.toUpperCase())
                .getJSONObject("quote")
                .toString()
    }

}