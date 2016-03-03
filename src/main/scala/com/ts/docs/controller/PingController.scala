package com.ts.docs.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller


class PingController extends Controller {

  get("/ping") { request: Request =>
    "pong"
  }
}

