package com.ts.docs.controller

import com.google.inject.Inject
import com.ts.docs.core.json.Parser
import com.ts.docs.models.{App, JsonRequest, Version}
import com.ts.docs.services.Apps
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam

import scala.concurrent.ExecutionContext.Implicits.global

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

  get("/apps/:id/active") { request : AppByIdRequest =>
    apps.active(request.id) map {
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

  post("/apps/:id/versions") { request : Request =>
    val app = App(request.getParam("id"))
    val version = Version.deserialize(request.getContentString())
    apps.addVersion(app, version) map {
      case Some(result) => response.created(result)
      case _ => response.notFound
    }
  }
}

case class AppByIdRequest(@QueryParam id: String)

case class SearchRequest(@QueryParam field: String, @QueryParam value: String)

case class ActivationRequest(@QueryParam id: String, @QueryParam version: String)
