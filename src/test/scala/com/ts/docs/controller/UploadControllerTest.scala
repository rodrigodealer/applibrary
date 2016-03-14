package com.ts.docs.controller

import java.io.{InputStream, File}

import com.ts.docs.core.Main
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.app.FeatureTest

class UploadControllerTest extends FeatureTest {
  override val app = new EmbeddedHttpServer(new Main)

//  "/api/docs feature" should {
//
//    "respond accordingly" in {
//      app.httpGet(
//        path = "/api/docs",
//        andExpect = Status.Ok)
//    }
//  }

  "/docs feature" should {

    "respond unauthorized due to lack of header" in {
      app.httpPost(
        postBody = "'",
        path = "/docs",
        andExpect = Status.Unauthorized
      )
    }

    "respond accordingly" in {
      val source = scala.io.Source.fromInputStream(getClass.getResourceAsStream("/sample.xml")).mkString
      app.httpPost(
        headers = Map("X-Tradeshift-TenantId" -> "123123"),
        postBody = source,
        path = "/docs",
        andExpect = Status.Unauthorized
      )
    }
  }
}
