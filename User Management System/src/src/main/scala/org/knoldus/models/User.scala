package org.knoldus.models

import java.util.UUID

case class User(id: Option[UUID], name:String , age:Int ,userRole:Value)
