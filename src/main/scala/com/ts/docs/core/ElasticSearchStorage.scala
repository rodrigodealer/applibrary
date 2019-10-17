package com.ts.docs.core

import com.google.inject.Module

abstract class ElasticSearchStorage {
  val es : Module = null
}

trait RemoteStorage extends ElasticSearchStorage {
  override val es : Module = ElasticSearchProvider
}