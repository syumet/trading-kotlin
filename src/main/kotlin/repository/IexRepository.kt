package ca.jrvs.trading.repository

import ca.jrvs.trading.config.IexConfig
import ca.jrvs.trading.domain.iex.IexQuoteDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.conn.HttpClientConnectionManager
import org.json.JSONObject
import org.springframework.stereotype.Repository

@Repository
class IexRepository(connectionManager: HttpClientConnectionManager, config: IexConfig)
    : BatchQueryHttpRepository<IexQuoteDto, String>(
        connectionManager,
        config.host + "/stock/market/batch?symbols=%s&types=quote&token=" + config.token) {

    override fun mapJsonToEntity(json: String): IexQuoteDto {
        return ObjectMapper().readValue(json, IexQuoteDto::class.java)
    }

    override fun getFromBatchResultById(json: JSONObject, id: String): String {
        return json
                .getJSONObject(id.toUpperCase())
                .getJSONObject("quote")
                .toString()
    }

}