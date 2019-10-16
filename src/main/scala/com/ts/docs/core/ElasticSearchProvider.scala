package com.ts.docs.core

import com.google.inject.Provides
import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.{ElasticsearchClientUri, TcpClient}
import com.twitter.inject.TwitterModule


object ElasticSearchProvider extends TwitterModule {

  val clusterName = flag("es_cluster_name", "elasticsearch", "elasticsearch cluster name")
  val esHost = flag("es_cluster_host", "127.0.0.1", "elasticsearch host")
  val esPort = flag("es_cluster_port", 9300, "elasticsearch port")

  @Provides
  def client =  {
    val uri = ElasticsearchClientUri("elasticsearch://foo:1234,boo:9876?cluster.name=mycluster")

    val client = TcpClient.transport(uri)
  }
}