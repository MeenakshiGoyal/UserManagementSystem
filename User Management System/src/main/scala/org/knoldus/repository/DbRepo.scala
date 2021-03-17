package org.knoldus.repository
import org.knoldus.dao.DAO
import org.knoldus.models.User

import java.util.UUID
import scala.collection.mutable.ListBuffer

class DbRepo extends DAO[User]{
  val listBuffer: ListBuffer[User]  = ListBuffer.empty[User]

// to add new user
override def createUser(user: User):Option[UUID]={
  val u_id = UUID.randomUUID()
  user match {
    case User(None, _, _, _) => {
      listBuffer.append(user.copy(id=Some(u_id)))
       listBuffer.last.id
    }
    case User(Some(_), _, _, _) => null
  }
}

  // to delete any user by id
   override  def delete(id: Option[UUID]):Boolean = {
     val index = listBuffer.indexOf(readById(id))
     listBuffer.remove(index)
     true
   }
//to read by id
  override def readById(id:Option[UUID]):User = {
    val list = listBuffer.filter(user => {user.id == id})
    list.head
  }
// to get all user list
  override def readAll():List[User]= {
    listBuffer.toList
  }

 // to update an user by id
  override def update(id:Option[UUID] , newUser: User):Boolean={
    val index = listBuffer.indexOf(readById(id))
    listBuffer.update(index,newUser)
    true
  }
}
