package com.ts.docs.controller

import java.util.UUID

import com.ts.docs.core.{ElasticSearchStorage, Main}
import com.twitter.finagle.http.{Response, Status}
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

import scala.concurrent.Future

class AppsControllerTest extends FeatureTest with ElasticSearchTest {

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
      withSleep(server.httpPost("/apps", s"""{"id":"$uuid","name":"$uuid","creation":"1234"}""", andExpect = Status.Created), 2000)

      server.httpGet(s"/apps/by/name/$uuid", andExpect = Status.Ok, withBody = s"""[{"id":"$uuid","name":"$uuid","creation":"1234"}]""")
    }
  }

  "App activation" should {
    "should create an app and activate a version" in {
      val uuid = UUID.randomUUID()
      val jsonBody = s"""{"id":"$uuid","name":"$uuid","creation":"1234","versions":[{"id":"123123123","current_active":false}]}"""
      withSleep(server.httpPost("/apps", jsonBody, andExpect = Status.Created), 2000)
      withSleep(server.httpGet(s"/apps/by/name/$uuid", andExpect = Status.Ok, withBody = s"""[$jsonBody]"""), 2000)
      withSleep(server.httpPost(s"/apps/$uuid/activate/123123123", postBody = "", andExpect = Status.Ok), 2000)

      server.httpGet(s"/apps/by/name/$uuid", andExpect = Status.Ok,
        withBody = s"""[{"id":"$uuid","name":"$uuid","creation":"1234","versions":[{"id":"123123123","current_active":true}]}]""")
    }

    "should create an app and add a version" in {
      val uuid = UUID.randomUUID()
      val versionBody = """{"id":"567567567","current_active": false,"bundle":{"css":"file.css","js":"file.js"}}"""
      val jsonBody = s"""{"id":"$uuid","name":"$uuid","creation":"1234","versions":[]}"""
      withSleep(server.httpPost("/apps", jsonBody, andExpect = Status.Created), 2000)
      withSleep(server.httpPost(s"/apps/$uuid/versions", postBody = versionBody, andExpect = Status.Created,
        withBody = s"""{"id":"$uuid","name":"$uuid","creation":"1234","versions":[{"id":"567567567","current_active":false,"bundle":{"css":"file.css","js":"file.js"}}]}"""), 2000)
    }

    "should try to add a version in an unknown app" in {
      val uuid = UUID.randomUUID()
      val versionBody = """{"id":"567567567","current_active": false}"""
      withSleep(server.httpPost(s"/apps/$uuid/versions", postBody = versionBody, andExpect = Status.NotFound, withBody = ""), 2000)
    }
  }
}

trait ElasticSearchTest {
  def withSleep(block: => (Response), sleepTime: Int) = {
    block
    Thread.sleep(sleepTime)
  }
}
