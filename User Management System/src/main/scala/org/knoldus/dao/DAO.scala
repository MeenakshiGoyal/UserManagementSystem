package org.knoldus.dao
import java.util.UUID
import scala.concurrent.Future

trait DAO[User] {

  def createUser(user: User):Future[Option[UUID]]
  def readAll:Future[List[User]]
  def delete(id:Option[UUID]):Future[Boolean]
  def update( id:Option[UUID] , user: User  ):Future[Boolean]
  def readById(id:Option[UUID]):Future[User]

}
