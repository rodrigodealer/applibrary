package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.core.json.Parser
import com.ts.docs.models.App
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class AppsController @Inject()(apps: Apps) extends Controller with Parser {

  get("/apps") { request: Request =>
    apps.findAll
  }

  get("/apps/by/:field/:value") { request : Request =>
    apps.findBy(request.params("field"), request.params("value")) map {
      case result if result.nonEmpty => result
      case result if result.isEmpty => response.notFound(result)
    }
  }

  post("/apps") { request : Request =>
    apps.post(App.fromString(request.getContentString()))
    response.created
  }
}
