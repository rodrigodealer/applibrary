package com.ts.docs.core

import com.ts.docs.controller.{PingController, UploadController}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

/*
object Finagle {

  def createStorage(s: Option[String]) = s.filterNot(_.isEmpty) match {
    case Some("redis") =>
      println("Using Redis storage")
      new RedisStorage()
    case _ =>
      println("using Riak storage")
      new RiakStorage("mybucket")
  }

  def service(storage: Storage) = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      storage.set(Record("" + System.nanoTime(), req.reader)) map { x =>
        req.response
      }
    }
  }

  def writeBytes( data : Stream[Byte], file : File ) = {
    val target = new BufferedOutputStream( new FileOutputStream(file) )
    try data.foreach( target.write(_) ) finally target.close()
  }


  def main(args: Array[String]) {
    val storage = createStorage(args.toList.headOption)
    val server = Http.server.withStreaming(true).serve(":8080", service(storage))
    Await.ready(server)
  }

}
*/

object Finatra extends Main {}

class Main extends HttpServer {

  override def defaultFinatraHttpPort = ":8080"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
      .add[UploadController]
  }

}