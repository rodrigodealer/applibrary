package com.ts.docs.models

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonMappingException, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.response.ResponseBuilder

import scala.util.{Failure, Success}


object Json {

  val mapper = new ObjectMapper with ScalaObjectMapper
  val module = new SimpleModule()
  mapper.registerModules(module, DefaultScalaModule)
  mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES)

  def serialize(obj: Any) = mapper.writeValueAsString(obj)

  def serializeAsBytes(obj: Any) = mapper.writeValueAsBytes(obj)

  def deserialize[T](json: String)(implicit m: Manifest[T]): T = mapper.readValue[T](json)

  def validate[T](json: String)(implicit m: Manifest[T]) = try {
    deserialize[T](json)
    Success
  } catch {
    case e : JsonMappingException => Failure
  }
}

trait JsonRequest {
  def withDeserialization[T](request: Request)
                            (block:(T) => ResponseBuilder#EnrichedResponse)
                            (errorBlock:(String) => ResponseBuilder#EnrichedResponse)
                            (implicit m: Manifest[T]) = {
    try {
      val result = Json.deserialize[T](request.getContentString())
      block(result)
    } catch {
      case e : JsonMappingException => errorBlock(e.getMessage)
    }
  }
}

trait JsonModel {
  def toJson = Json.serialize(this)
}
