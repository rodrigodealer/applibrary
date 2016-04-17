package com.ts.docs.controller

import com.google.inject.{Module, Provides}
import com.sksamuel.elastic4s.ElasticClient
import com.ts.docs.core.ElasticSearchStorage
import com.twitter.inject.TwitterModule
import org.elasticsearch.common.settings.Settings

object ElasticSearchLocalProvider extends TwitterModule {

  val clusterName = flag("es_cluster_name", "elasticsearch", "elasticsearch cluster name")
  val esHost = flag("es_cluster_host", "127.0.0.1", "elasticsearch host")
  val esPort = flag("es_cluster_port", 9300, "elasticsearch port")

  @Provides
  def client =  {
    val settings = Settings.settingsBuilder().put("cluster.name", clusterName())
      .put("path.home", System.getProperty("user.dir")).build()
    ElasticClient.local(settings)
  }
}

trait LocalStorage extends ElasticSearchStorage {
  override val es : Module = ElasticSearchLocalProvider
}
