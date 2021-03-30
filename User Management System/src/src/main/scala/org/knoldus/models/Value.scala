package org.knoldus.models
sealed trait  Value

object Value{
  case object Admin extends Value

  case object Customer extends Value
}