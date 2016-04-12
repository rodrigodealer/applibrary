package com.ts.docs.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.google.inject.Inject
import com.sksamuel.elastic4s.ElasticDsl.{search, _}
import com.sksamuel.elastic4s.mappings.FieldType.StringType
import com.sksamuel.elastic4s.{ElasticClient, RichSearchResponse}
import com.ts.docs.services.FutureImplicits.ScalaToTwitter
import com.twitter.util.{Future, Promise}

import scala.concurrent.ExecutionContext.Implicits.global


object FutureImplicits {
  implicit def ScalaToTwitter[T](scalaF: scala.concurrent.Future[T]): Promise[T] = {
    val p = new Promise[T]
    scalaF.onSuccess { case res => p.setValue(res) }
    scalaF.onFailure { case ex => p.setException(ex) }
    p
  }
}


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

  def findAll: Future[Seq[App]] = client.execute {
    search in "apps" -> "external" query matchAllQuery
  } map parse[App]

}

case class App(id: String, name: String, creation: String)

trait Parser {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def parse[T](implicit m: Manifest[T]): RichSearchResponse => Seq[T] = {
    (res: RichSearchResponse) => {
      res.getHits.hits().toSeq map { app =>
        mapper.readValue(app.getSourceAsString, m.runtimeClass.asInstanceOf[Class[T]])
      }
    }
  }
}
