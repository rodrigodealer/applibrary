package com.ts.docs.models

import org.scalatest.FunSuite

class AppTest extends FunSuite {

  test("should serialize as json") {
    val app = App("123", "123", "123", "123")
    assert(app.toJson.equals("{\"id\":\"123\",\"name\":\"123\",\"creation\":\"123\",\"vendorId\":\"123\"}"))
  }

  test("should parse json") {
    val json = "{\"id\":\"123\",\"name\":\"123\",\"creation\":\"123\"}"
    val app = Json.deserialize[App](json)
    assert(app.id.equals("123"))
    assert(app.name.equals("123"))
    assert(app.creation.equals("123"))
  }

}
