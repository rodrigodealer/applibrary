package com.ts.docs.controller

import com.ts.docs.core.{ElasticSearchStorage, Main}
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

class AppsControllerTest extends FeatureTest {

  val es = new ElasticSearchStorage with LocalStorage

  val myApp = new Main(es)

  override val server = new EmbeddedHttpServer(myApp)

  "Apps by field" should  {

    "perform feature" in {
      server.httpGet(
        path = "/apps/by/vendorid/myvendor",
        andExpect = Status.Ok,
        withBody = "vendorid=myvendor")
    }
  }

}
