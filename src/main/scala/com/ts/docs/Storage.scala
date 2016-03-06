package com.ts.docs

import com.google.inject.Inject
import com.ts.docs.IOUtils._
import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.redis.util.StringToChannelBuffer
import com.twitter.finagle.redis.{Redis, TransactionalClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.annotations.Flag
import com.twitter.io.Reader
import com.twitter.util.Future
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}

class RedisStorage @Inject()(statsReceiver: StatsReceiver, @Flag("redisHost")  host: String)  extends Storage {

  val uploaded = statsReceiver.counter("uploaded")

  val redisClient = TransactionalClient(ClientBuilder()
    .codec(new Redis())
    .name("Redis File Repository")
    .hosts(host)
    .hostConnectionLimit(100)
    .reportTo(statsReceiver)
    .keepAlive(true)
    .buildFactory())

  override def set(record: Record): Future[Boolean] = {
      for {
        buffer <- read(record.value)
        res    <- redisClient.set(StringToChannelBuffer(record.key), buffer)
      } yield {
        uploaded.incr()
        true
      }
  }

  override def add(record: Record): Future[Boolean] = {
    for {
      buffer <- read(record.value)
      res    <- redisClient.sAdd(StringToChannelBuffer(record.key), buffer :: Nil)
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
  def add(record: Record): Future[Boolean]
}

case class Record(key: String, value: Reader)
