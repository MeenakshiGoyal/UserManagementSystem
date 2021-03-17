package org.knoldus.dao
import org.knoldus.models.User
import java.util.UUID
//DAO operations
trait DAO[T] {
  def createUser(t:T):Option[UUID]  // for add user
  def readAll():List[T]  // get list of all users
  def delete(id:Option[UUID]):Boolean  // to delete any user
  def update( id:Option[UUID] , user: User  ):Boolean  // to update details of any user

  def readById(id:Option[UUID]):User

}
