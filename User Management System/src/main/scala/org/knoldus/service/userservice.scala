package org.knoldus.service

import org.knoldus.dao.DAO
import org.knoldus.models.User

import java.util.UUID



class userservice(dao:DAO[User]) {

  def addUser(user: User):Option[UUID] = {
    if(dao.readAll().contains(user)) {
      None
    }
    else
      dao.createUser(user)
  }


  def getUserList():List[User]  =  {
    dao.readAll()
  }

  def doDelete(id:Option[UUID]):Boolean = {
    if(id.isDefined) {
      dao.delete(id)
      true
    }
    else
      false


  }


  def doUpdate(id:Option[UUID] , newUser:User):Boolean = {
    if(id.isDefined) {
    dao.update(id , newUser)

  }
    true
  }


  def getUserId(id:Option[UUID]):User = if(id.isDefined)
    dao.readById(id)
  else
    throw new NoSuchElementException

}
