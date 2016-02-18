package com.github.rodrigodealer.s3

import java.io.File

import awscala.s3.{Bucket, S3}

class S3Uploader(bucketName: String) {

  def upload(file: File, name: String) = {
    val bucket: Bucket = S3().createBucket(bucketName)
    bucket.put(name, file)
  }
}
