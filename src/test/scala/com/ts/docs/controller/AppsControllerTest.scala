package com.ts.docs.controller

import java.util.UUID

import com.ts.docs.core.{ElasticSearchStorage, Main}
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

import scala.concurrent.Future

class AppsControllerTest extends FeatureTest {

  val es = new ElasticSearchStorage with LocalStorage

  val myApp = new Main(es)

  override val server = new EmbeddedHttpServer(myApp)

  "All Apps" should {

    "perform feature" in {
      server.httpGet(
        path = "/apps",
        andExpect = Status.Ok)
    }
  }

  "Apps by field" should  {
    "cannot find app with field and value" in {
      server.httpGet(
        path = "/apps/by/vendorid/myvendor",
        andExpect = Status.NotFound,
        withBody = "[]")
    }

    "create an app" in {
      server.httpPost("/apps", """{"id":"123","name":"123","creation":"123"}""", andExpect = Status.Created)
    }

    "can find an app with field and value" in {
      val uuid = UUID.randomUUID()
      server.httpPost("/apps", s"""{"id":"$uuid","name":"$uuid","creation":"1234"}""", andExpect = Status.Created)
      Thread.sleep(1000)
      server.httpGet(s"/apps/by/name/$uuid", andExpect = Status.Ok, withBody = s"""[{"id":"$uuid","name":"$uuid","creation":"1234"}]""")
    }
  }

}
