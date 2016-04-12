package com.ts.docs

import com.google.inject.Provides
import com.sksamuel.elastic4s.{ElasticsearchClientUri, ElasticClient}
import com.twitter.inject.TwitterModule
import org.elasticsearch.common.settings.Settings


object ElasticSearchProvider extends TwitterModule {

  val clusterName = flag("es_cluster_name", "elasticsearch", "elasticsearch cluster name")
  val esHost = flag("es_cluster_host", "127.0.0.1", "elasticsearch host")
  val esPort = flag("es_cluster_port", 9300, "elasticsearch port")

  @Provides
  def client =  {
    val settings = Settings.settingsBuilder()
      .put("cluster.name", clusterName()).build()
    ElasticClient.transport(
      settings,
      ElasticsearchClientUri("es", List(esHost() -> esPort()))
    )
  }
}