package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class AppsController @Inject()(apps: Apps) extends Controller {

  get("/apps") { request: Request =>
    apps.findAll
  }

  get("/apps/by/:field/:value") { request : Request =>
    response.ok(s"${request.params("field")}=${request.params("value")}")
  }
}
