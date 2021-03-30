package org.knoldus.service
import org.knoldus.dao.DAO
import org.knoldus.models.User
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserService(dao:DAO[User]) {

  def addUser(user: User): Future[Option[UUID]] = {
    val retrievelist = for {
      users <- dao.readAll
    } yield {
      if (users.contains(user)) {
        val isUser = Future(None)
        isUser
      }
      else {
         val newUser =  dao.createUser(user)
        newUser
      }
    }
   retrievelist.flatMap(x=>x)
  }

  def getUserList():Future[List[User]]  =  {
    dao.readAll
  }

  def doDelete(id:Option[UUID]):Future[Boolean] = Future{
    if(id.isDefined) {
      dao.delete(id)
      true
    }
    else
      false
  }

  def doUpdate(id:Option[UUID] , newUser:User): Future[Boolean] = {
    if(id.isDefined) {
    dao.update(id , newUser)
  }
    else {
      throw new NoSuchElementException
    }
  }

  def getUserId(id:Option[UUID]):Future[User] = if(id.isDefined)
    dao.readById(id)
  else
    throw new NoSuchElementException

}
