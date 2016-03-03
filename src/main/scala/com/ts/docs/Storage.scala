package com.ts.docs

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Method.Post
import com.twitter.finagle.http.Version.Http11
import com.twitter.finagle.http.{Http, Request}
import com.twitter.finagle.redis.util.StringToChannelBuffer
import com.twitter.finagle.redis.{TransactionalClient, Redis}
import com.twitter.io.Reader
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}
import IOUtils._

class RedisStorage extends Storage {

  val redisClient = TransactionalClient(ClientBuilder()
    .codec(new Redis())
    .hosts("localhost:6379")
    .hostConnectionLimit(100)
    .keepAlive(true)
    .buildFactory())

  override def set(record: Record): Future[Boolean] = {
      for {
        buffer <- read(record.value)
        res    <- redisClient.set(StringToChannelBuffer(record.key), buffer)
      } yield true
  }
}

object IOUtils {

  def read(r: Reader): Future[ChannelBuffer] = {
    Reader.readAll(r) map { buf =>
      val bytes = new Array[Byte](buf.length)
      buf.write(bytes, 0)
      ChannelBuffers.wrappedBuffer(bytes)
    }
  }
}

trait Storage {
  def set(record: Record): Future[Boolean]
}

class RiakStorage(bucketName: String) extends Storage {

  // private val bucket = RiakClient("localhost", 8098).bucket(bucketName)

  // the default client is slow. So we use the raw http api so we can stream the file directly.
  val client = // Http.client.withStreaming(true).newService("localhost:8098")
    ClientBuilder()
      .codec(new Http())
      .hosts("localhost:8098")
      .hostConnectionLimit(100)
      .keepAlive(true)
      .build()

  def set(record: Record) = {
    val newReq = Request(Http11, Post, s"//buckets/$bucketName/keys", record.value)
    newReq.setContentType(mediaType = "text/plain")
    newReq.setChunked(true)
    client(newReq) map (_.getStatusCode() == 201)
  }

}

case class Record(key: String, value: Reader)
