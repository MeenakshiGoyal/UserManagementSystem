package org.knoldus.repository
import org.knoldus.dao.DAO
import org.knoldus.db.UserTable
import org.knoldus.models.User

import java.util.UUID
import scala.concurrent.Future

class DbRepo(userTable: UserTable) extends DAO[User]{

  override def createUser(user: User): Future[Option[UUID]] = {
    userTable.createUser(user)
  }

  override def delete(id: Option[UUID]): Future[Boolean] = {
    userTable.delete(id)
  }

  override def readById(id: Option[UUID]): Future[User] = {
    userTable.readById(id)
  }

  override def readAll :Future[List[User]]= {
    userTable.readAll
  }

  override def update(id:Option[UUID] , newUser: User):Future[Boolean] = {
   userTable.update(id,newUser)
  }
}
