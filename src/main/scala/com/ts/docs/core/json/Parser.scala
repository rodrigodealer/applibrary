package com.ts.docs.core.json

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.sksamuel.elastic4s.RichSearchResponse
import com.ts.docs.models.App
//import scala.reflect._


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