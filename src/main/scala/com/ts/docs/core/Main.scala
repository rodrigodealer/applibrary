package com.ts.docs.core

import com.ts.docs.controller.{AppsController, PingController}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

object Finatra extends Main(new ElasticSearchStorage with RemoteStorage)

class Main(esStorage: ElasticSearchStorage) extends HttpServer {

  val redisHost = flag("redisHost", "localhost:6379", "Redis Host: localhost:6379")

  override val modules = Seq(esStorage.es)

  override def defaultFinatraHttpPort = ":8080"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
      .add[AppsController]
  }
}