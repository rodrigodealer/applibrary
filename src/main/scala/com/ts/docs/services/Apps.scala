package com.ts.docs.services

import com.google.inject.Inject
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{search, _}
import com.sksamuel.elastic4s.mappings.FieldType.{BooleanType, StringType}
import com.ts.docs.core.FutureImplicits.ScalaToTwitter
import com.ts.docs.core.json.Parser
import com.ts.docs.models.App
import com.twitter.util.Future

import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._
import scala.concurrent.ExecutionContext.Implicits.global


class Apps @Inject()(implicit client: ElasticClient) extends Parser {

  val appsIndex : (String, String) = "apps" -> "external"

  {
    client.execute {
      create index "apps" mappings (
        "external" fields (
          "id" typed StringType,
          "name" typed StringType boost 4,
          "creation" typed StringType,
          "vendorId" typed StringType,
          nestedField("versions") as (
            field("id") typed StringType,
            field("currentActive") typed BooleanType
          )
        )
      )
    }
  }

  def post(app: App) = client.execute(index into appsIndex id app.id source app)

  def findAll: Future[Seq[App]] = client.execute {
    search in appsIndex query matchAllQuery
  } map parse[App]

  def findBy(field: String, value: String) : Future[Seq[App]] = client.execute {
    search in appsIndex query { matchQuery(field, value) }
  } map parse[App]
}
