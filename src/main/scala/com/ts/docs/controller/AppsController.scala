package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.core.json.Parser
import com.ts.docs.models.{Version, JsonRequest, Json, App}
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class AppsController @Inject()(apps: Apps) extends Controller with Parser with JsonRequest {

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
    withDeserialization[App](request) {
      apps.post(Json.deserialize[App](request.getContentString()))
      response.created
    } { error =>
      response.internalServerError()
    }
  }

  post("/apps/:id/activate/:version") { request : Request =>
    val app = App(request.getParam("id"), null, null, null, Set())
    val version = Version(request.getParam("version"), false)
    apps.activate(app, version)
    response.ok
  }
}
