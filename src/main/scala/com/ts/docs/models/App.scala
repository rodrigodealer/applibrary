package com.ts.docs.models

import com.sksamuel.elastic4s.Indexable


case class Bundle(css: String, js: String)

case class Version(id: String, currentActive: Boolean, bundle: Option[Bundle])

object Version {
  def deserialize(json: String) = Json.deserialize[Version](json)

  def apply(version: String) : Version = Version(version, false, Option.empty)
}



case class App(id: String,
               name: String,
               creation: String,
               vendorId: String,
               var versions: Set[Version]) extends JsonModel



object App {
  def deserialize(json: String) = Json.deserialize[App](json)

  def apply(id: String) : App = App(id, null, null, null, Set())
}

object MyImplicits {
  implicit object AppIndexable extends Indexable[App] {
    override def json(t: App): String = s""" { "name" : "${t.name}" } """
  }
}