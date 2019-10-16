package com.ts.docs.services

import com.google.inject.Inject
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.ts.docs.models.App
import com.twitter.util.Future

import scala.concurrent.ExecutionContext.Implicits.global


class Apps @Inject()(implicit client: ElasticClient) {

  val appsIndex : (String, String) = "apps" -> "external"

  {
    client.execute {
      createIndex("apps").mappings(
        mapping("app").fields(
          textField("name") boost 4,
          keywordField("id")

        ))
    }
  }

  def post(app: App) = client.execute(indexInto("apps")id app.id source app)

  def findAll: Future[Seq[App]] = client.execute {
    search("apps") matchAllQuery() limit 100
  } map(a => a)

  def findBy(field: String, value: String) : Future[Seq[App]] = client.execute {
    search("apps") query { termQuery(field, value )}
  } map(a => a)

}
