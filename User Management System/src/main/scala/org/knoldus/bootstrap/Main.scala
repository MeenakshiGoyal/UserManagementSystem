package org.knoldus.bootstrap

import org.knoldus.dao.DAO
import org.knoldus.repository.DbRepo
import org.knoldus.models.{User, Value}
import org.knoldus.service.userservice

import java.util.UUID
import java.util.UUID.randomUUID

object Main extends App{
   val dbrepo: DAO[User] = new DbRepo()
   val service = new userservice(dbrepo)

  // for adding users
   val userOne = User(None, "Meenakshi" , 21 , Value.Admin)
   val userTwo =  User(None, "Gaurav" , 22 , Value.Customer)
   val userThree =  User(None, "Aman" , 32 , Value.Customer)


  //read the values

  val user1 = service.addUser(userOne)
  val user2 = service.addUser(userTwo)
  val user3 = service.addUser(userThree)
  println("Users are :" + service.getUserList())

  //update the user values
  val update = service.getUserId(user1).copy(name = "Manisha")
  service.doUpdate(user1 , update)
  println("After updation: " + service.getUserList())
  println()

  // to delete the user
  service.doDelete(user2)
  println("After deletion " + service.getUserList())


}


