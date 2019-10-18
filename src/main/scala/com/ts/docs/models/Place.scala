package com.ts.docs.models

case class Place (name: String, location: Location, keywords: Option[List[Keywords]])

case class Location(name: String, country: String, continent: String)

case class Keywords(name: String)

