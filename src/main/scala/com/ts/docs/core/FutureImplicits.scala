package com.ts.docs.core

import com.twitter.util.Promise
import scala.concurrent.ExecutionContext.Implicits.global

object FutureImplicits {
  implicit def ScalaToTwitter[T](scalaF: scala.concurrent.Future[T]): Promise[T] = {
    val p = new Promise[T]
    scalaF.onSuccess { case res => p.setValue(res) }
    scalaF.onFailure { case ex => p.setException(ex) }
    p
  }
}