package com.ts.docs.controller

import com.sksamuel.elastic4s.ElasticClient
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class AppsController extends Controller {

  val client = ElasticClient.remote("url.of.your.es.server", 9300)

  get("/apps") { request : Request =>
    println
  }

}
