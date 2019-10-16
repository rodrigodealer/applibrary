package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.models.{App, JsonRequest, Version}
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam


class AppsController @Inject()(apps: Apps) extends Controller with JsonRequest {

  get("/apps") { request: Request =>
    apps.findAll
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
    response.ok
  }

  post("/apps/:id/versions") { request : Request =>
    val app = App(request.getParam("id"))
    val version = Version.deserialize(request.getContentString())
  }
}

case class AppByIdRequest(@QueryParam id: String)

case class SearchRequest(@QueryParam field: String, @QueryParam value: String)

case class ActivationRequest(@QueryParam id: String, @QueryParam version: String)
