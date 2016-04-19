package com.ts.docs.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.sksamuel.elastic4s.source.Indexable

case class App(id: String, name: String, creation: String)

object AppIndexable extends Indexable[App] {
  override def json(t: App): String = s""" { "name" : "${t.name}", "id" : "${t.id}", "creation": "${t.creation}" } """
}

object App {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def fromString(value: String) = {
    if (value.isEmpty)
      App(null, null, null)
    else
      mapper.readValue(value, classOf[App])
  }
}

