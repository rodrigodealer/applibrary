package com.ts.docs.models

import com.sksamuel.elastic4s.source.Indexable

case class Version(id: String, currentActive: Boolean)

object Version {
  def apply(version: String) : Version = Version(version, false)
}

case class App(id: String,
               name: String,
               creation: String,
               vendorId: String,
               var versions: Set[Version]) extends JsonModel {

  def hasVersionActivated(version: Version): Boolean = {
    versions.count(v => v.id.equals(version.id) && v.currentActive) > 0
  }

  def activateVersion(version: Version) = {
    this.versions = versions.dropWhile(v => v.id == version.id) + version.copy(currentActive = true)
    this
  }
}

object App {
  def deserialize(json: String) = Json.deserialize[App](json)

  def apply(id: String) : App = App(id, null, null, null, Set())
}

object AppIndexable extends Indexable[App] {
  override def json(app: App): String = app.toJson
}
