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
    val activated = versions.filter(_.id ==  version.id).head.copy(currentActive = true)
    this.versions = versions.dropWhile(_.id == version.id) + activated
    this
  }
}

object AppIndexable extends Indexable[App] {
  override def json(t: App): String = t.toJson
}
