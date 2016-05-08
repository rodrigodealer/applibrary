package com.ts.docs.models

import org.scalatest.FunSuite

class VersionTest extends FunSuite {

  test("create a version just version") {
    val version = Version("123.1")
    assert(version.id == "123.1")
    assert(!version.currentActive)
  }
}
