package com.github.rodrigodealer

import com.scalapenos.riak.RiakClient


class RiakStorage(bucketName: String) {

  private val bucket = RiakClient("localhost", 8098).bucket(bucketName)

  def set(record: RiakRecord) = bucket.storeAndFetch(record.key, record.value)

}

case class RiakRecord(key: String, value: String)
