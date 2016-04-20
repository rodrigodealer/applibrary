package com.ts.docs.models

import com.sksamuel.elastic4s.source.Indexable

case class App(id: String, name: String, creation: String, vendorId: String) extends JsonModel

object AppIndexable extends Indexable[App] {
  override def json(t: App): String = t.toJson
}
