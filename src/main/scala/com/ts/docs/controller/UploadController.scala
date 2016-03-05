package com.ts.docs.controller

import java.util.UUID

import com.ts.docs.{Record, RedisStorage}
import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.Controller
import com.twitter.util.Future

class UploadController extends Controller {

  val storage = new RedisStorage

  post("/docs") { req: Request =>
    valid(req) {
      id => store(Record(id + System.nanoTime(), req.reader))
    }
  }

  get("/api/docs") { request: Request =>
    response.ok.file("/docs.html")
  }


  private def store(record: Record) = storage.set(record) map { x =>
    response.status(201).header("ts-id", UUID.randomUUID())
  }

  private def valid(req: Request)(block: String => Future[Response]): Future[Response] = {
    req.headerMap.get("X-Tradeshift-TenantId") match {
      case None =>
        response.status(401).plain("Not Authorized!").toFuture
      case Some(id) => block(id)
    }
  }
}