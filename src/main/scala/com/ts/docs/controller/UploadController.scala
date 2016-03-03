package com.ts.docs.controller

import com.ts.docs.{Record, RedisStorage}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class UploadController extends Controller {

  val storage = new RedisStorage

  post("/upload") { req: Request =>
    storage.set(Record("" + System.nanoTime(), req.reader)) map { x =>
      req.response
    }
  }
}