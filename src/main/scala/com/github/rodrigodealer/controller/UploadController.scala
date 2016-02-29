package com.github.rodrigodealer.controller

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class UploadController extends Controller {

  post("/file") { request : Request =>
    println("===================")
    println(request.headerMap.map{ h => s"${h._1}=${h._2}" })
    println("===================")
    "OK"
  }

}
