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
    val app = App("123", "123", "123", "123", Set(Version("123", true, Option.empty)))
    assert(app.hasVersionActivated(Version("123", false, Option.empty)))
  }

  test("should activate one version") {
    val app = App("123", "123", "123", "123", Set(Version("123", false, Option.empty), Version("456", false, Option.empty)))
    val appActivated = app.activateVersion(Version("123", true, Option.empty))
    assert(appActivated.hasVersionActivated(Version("123", true, Option.empty)))
    assert(!appActivated.hasVersionActivated(Version("456", true, Option.empty)))
  }

  test("create an App just with id") {
    val app = App("123")
    assert(app.id == "123")
    assert(app.creation == null)
    assert(app.name == null)
    assert(app.vendorId == null)
    assert(app.versions.isEmpty)
  }

  test("add a version to an app") {
    var app = App("123", "123", "123", "123", Set(Version("123", true, Option.empty)))
    val version = Version("123456", false, Option.empty)

    app = app.add(version)
    assert(app.versions.size == 2)
  }

  test("add the same version to an app") {
    var app = App("123", "123", "123", "123", Set(Version("123", true, Option.empty)))
    val version = Version("123456", false, Option.empty)

    app = app.add(version).add(version)
    assert(app.versions.size == 2)
  }
}
