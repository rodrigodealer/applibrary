package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.core.json.Parser
import com.ts.docs.models.{App, Json, JsonRequest, Version}
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam

class AppsController @Inject()(apps: Apps) extends Controller with Parser with JsonRequest {

  get("/apps") { request: Request =>
    apps.findAll
  }

  get("/apps/by/:field/:value") { request : SearchRequest =>
    apps.findBy(request.field, request.value) map {
      case result if result.nonEmpty => result
      case _ => response.notFound(Set())
    }
  }

  post("/apps") { request : Request =>
    withDeserialization[App](request) {
      apps.post(App.deserialize(request.getContentString()))
      response.created
    } { error =>
      response.internalServerError()
    }
  }

  post("/apps/:id/activate/:version") { request : ActivationRequest =>
    val app = App(request.id)
    val version = Version(request.version)
    apps.activate(app, version)
    response.ok
  }
}


case class SearchRequest(@QueryParam field: String, @QueryParam value: String)

case class ActivationRequest(@QueryParam id: String, @QueryParam version: String)
