package org.knoldus.db
import org.knoldus.models.User
import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserTable {
  val listBuffer: ListBuffer[User] = ListBuffer.empty[User]

  def createUser(user: User): Future[Option[UUID]] = Future {
    val u_id = UUID.randomUUID()
    user match {
      case User(None, _, _, _) => {
        listBuffer.append(user.copy(id = Some(u_id)))
        listBuffer.last.id
      }
     case User(Some(_), _, _, _) => null
    }
  }

   def delete(id: Option[UUID]):Future[Boolean] = Future{
    val index = listBuffer.filterNot(_.id == id)
    listBuffer.clear()
     listBuffer++= index
    true
  }

  def readById(id:Option[UUID]):Future[User] = Future {
    val list = listBuffer.filter(user => {user.id == id})
    list.head
  }

  def readAll : Future[List[User]]= Future{
    listBuffer.toList
  }
  def update(id:Option[UUID] , newUser: User):Future[Boolean] = Future{
    val index = listBuffer.filterNot(_.id == id)
    index += newUser
    listBuffer.clear()
    listBuffer ++= index
    true
  }


}