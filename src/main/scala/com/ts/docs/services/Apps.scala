package com.ts.docs.services

import com.google.inject.Inject
import com.sksamuel.elastic4s.RefreshPolicy
import com.sksamuel.elastic4s.http.{ElasticClient, RequestFailure, RequestSuccess}
import com.sksamuel.elastic4s.http.ElasticDsl._
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.twitter.util.Future


class Apps @Inject()(implicit client: ElasticClient) {

  def index()= {
    client.execute {
      bulk(
        indexInto("myindex" / "mytype").fields("country" -> "Mongolia", "capital" -> "Ulaanbaatar"),
        indexInto("myindex" / "mytype").fields("country" -> "Namibia", "capital" -> "Windhoek")
      ).refresh(RefreshPolicy.WaitFor)
    }.await
  }

  def get() = {
    val response = client.execute {
      search("myindex" / "mytype") query "Namibia"
    }.await

    response match {
      case failure: RequestFailure => println("We failed " + failure.error)
      case results: RequestSuccess[SearchResponse] => Future(results.body)
      case results: RequestSuccess[_] => Future(results.result)
    }
  }
}
