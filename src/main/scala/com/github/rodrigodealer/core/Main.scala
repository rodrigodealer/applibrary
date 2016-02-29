package com.github.rodrigodealer.core

import com.github.rodrigodealer.controller.{UploadController, PingController}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

object Server extends Main

class Main extends HttpServer {

  override def defaultFinatraHttpPort = ":8888"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[CommonFilters]
      .add[PingController]
      .add[UploadController]
  }

}