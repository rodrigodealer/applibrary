package com.ts.docs.models

import org.scalatest.FunSuite

class AppTest extends FunSuite {

  test("should serialize as json") {
    val app = App("123", "123", "123", "123", Set())
    assert(app.toJson == "{\"id\":\"123\",\"name\":\"123\",\"creation\":\"123\",\"vendorId\":\"123\",\"versions\":[]}")
  }

  test("should parse json") {
    val json = "{\"id\":\"123\",\"name\":\"123\",\"creation\":\"123\"}"
    val app = App.deserialize(json)
    assert(app.id.equals("123"))
    assert(app.name.equals("123"))
    assert(app.creation.equals("123"))
  }

  test("should have one activated version") {
    val app = App("123", "123", "123", "123", Set(Version("123", true)))
    assert(app.hasVersionActivated(Version("123", false)))
  }

  test("should activate one version") {
    val app = App("123", "123", "123", "123", Set(Version("123", false), Version("456", false)))
    val appActivated = app.activateVersion(Version("123", true))
    assert(appActivated.hasVersionActivated(Version("123", true)))
    assert(!appActivated.hasVersionActivated(Version("456", true)))
  }

  test("create an App just with id") {
    val app = App("123")
    assert(app.id == "123")
    assert(app.creation == null)
    assert(app.name == null)
    assert(app.vendorId == null)
    assert(app.versions.isEmpty)
  }
}
