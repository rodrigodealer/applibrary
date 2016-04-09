package com.ts.docs.controller

import com.sksamuel.elastic4s.{ElasticsearchClientUri, ElasticClient}
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.analyzers.StopAnalyzer
import com.sksamuel.elastic4s.mappings.FieldType.{IntegerType, StringType}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global


class AppsController extends Controller {



  get("/apps") { request : Request =>

    val client = ElasticClient.remote("127.0.0.1", 9200)

    client.execute {
      create index "places" mappings (
        "cities" as (
          "id" typed IntegerType,
          "name" typed StringType boost 4,
          "content" typed StringType analyzer StopAnalyzer
          )
        )
    }

    val response = client execute { search in "apps" -> "external" query "MyApp" }

    response.recover {
      case ex => ex.printStackTrace()
    }
    response map (_ => "aaaa")
    "ok"
  }

}
