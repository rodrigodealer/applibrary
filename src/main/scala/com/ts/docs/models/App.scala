package com.ts.docs.models

import com.sksamuel.elastic4s.source.Indexable

case class Version(id: String, currentActive: Boolean)

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

object AppIndexable extends Indexable[App] {
  override def json(t: App): String = t.toJson
}
