package ca.jrvs.trading.repository

import org.apache.http.conn.HttpClientConnectionManager
import org.json.JSONObject

abstract class BatchQueryHttpRepository<E, ID>(
        connectionManager: HttpClientConnectionManager,
        private val IEX_BATCH_URL: String)
    : HttpRepository<E, ID>(connectionManager) {

    abstract fun mapJsonToEntity(json: String): E

    abstract fun getFromBatchResultById(json: JSONObject, id: ID): String

    fun findAllById(ids: List<ID>): List<E?> {
        val url: String = String.format(IEX_BATCH_URL, ids.joinToString(","))
        val jsonString = executeHttpGet(url)
        return jsonString?.let {
            ids
                    .map { id -> getFromBatchResultById(JSONObject(it), id) }
                    .map(this::mapJsonToEntity)
        } ?: emptyList()
    }

    fun findById(id: ID): E? {
        return findAllById(listOf(id)).firstOrNull()
    }

    fun existById(id: ID): Boolean {
        return findById(id) != null
    }

}