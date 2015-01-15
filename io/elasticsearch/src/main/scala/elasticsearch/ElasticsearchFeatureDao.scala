package elasticsearch

import spray.json._
import feature._
import geojson.FeatureJsonProtocol._
import org.elasticsearch.client.Client
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.action.search.SearchType
import org.elasticsearch.index.query.FilterBuilders._
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryBuilders._

class ElasticsearchFeatureDao(client: Client) {

  def create(f: Feature, index: String, indexType: String): Boolean = {
    val response = client
      .prepareIndex(index, indexType)
      .setSource(f.toJson.toString)
      .execute()
      .actionGet()
    response.isCreated
  }

  def findById(index: String, indexType: String, id: String): Feature = {
    val qb = QueryBuilders.idsQuery(indexType).addIds(id)
    val response = client
      .prepareSearch(index)
      .setTypes(indexType)
      .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
      .setQuery(qb)
      .execute()
      .actionGet()
      .toString()
    response.parseJson.convertTo[Feature]
  }

  def update(f: Feature, index: String, indexType: String): Unit = {

  }

  def delete(index: String, indexType: String, id: String): Unit = {
    client
      .prepareDelete(index, indexType, id)
      .execute()
      .actionGet()
  }

}
