package com.ts.docs.services

import com.google.inject.Inject
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl.{search, _}
import com.sksamuel.elastic4s.mappings.FieldType.StringType
import com.ts.docs.core.FutureImplicits.ScalaToTwitter
import com.ts.docs.core.json.Parser
import com.ts.docs.models.App
import com.twitter.util.Future

import scala.concurrent.ExecutionContext.Implicits.global


class Apps @Inject()(implicit client: ElasticClient) extends Parser {

  {
    client.execute {
      create index "apps" mappings (
        "external" as(
          "id" typed StringType,
          "name" typed StringType boost 4,
          "creation" typed StringType
          )
        )
    }
  }

  def post(app: App) = {
    client.execute {
      index into "apps" -> "external" id app.id fields (
        "name" -> app.name,
        "id" -> app.id,
        "creation" -> app.creation
        )
    }
  }

  def findAll: Future[Seq[App]] = client.execute {
    search in "apps" -> "external" query matchAllQuery
  } map parse[App]

  def findBy(field: String, value: String) : Future[Seq[App]] = client.execute {
    search in "apps" -> "external" query { matchQuery(field, value) }
  } map parse[App]

}
