package com.ts.docs.controller

import com.ts.docs.core.{RemoteStorage, ElasticSearchStorage, ElasticSearchProvider, Main}
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.{FeatureTest}

class PingControllerTest extends FeatureTest {

  val es = new ElasticSearchStorage with LocalStorage

  val myApp = new Main(es)

  override val server = new EmbeddedHttpServer(myApp)

  "MyTest" should  {

    "perform feature" in {
      server.httpGet(
        path = "/ping",
        andExpect = Status.Ok)
    }
  }

}
