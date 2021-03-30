package dbtest
import org.knoldus.db.UserTable
import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.scalatest.flatspec.AsyncFlatSpec

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class UserTabletest extends AsyncFlatSpec {
  val usertable = new UserTable

  "User" should " return a list " in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = Await.result(usertable.createUser(existuser) , 20 seconds)
    val result = Await.result(usertable.readAll , 20 seconds )
    assert(result.nonEmpty)
  }

  "Create user" should "create a user" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    val result = Await.result(usertable.createUser(user), 10 seconds )
    assert(result.isDefined)
  }
  "Delete" should "delete any user by id" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = Await.result(usertable.createUser(existuser) , 10 seconds)
    val result = Await.result(usertable.delete(id), 10 seconds)
    assert(result)
  }
  "Update " should "update their values" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = Await.result(usertable.createUser(existuser) , 10 seconds)
    val update = User(None,"Manisha" , 21 , Value.Admin)
    val result = Await.result(usertable.update(id, update),10 seconds)
    assert(result)
  }

  "User" should "return a list by id" in {
    val existuser = User(None, "Adi", 21, Value.Admin)
    val id = Await.result(usertable.createUser(existuser) , 10 seconds)
    val user = Await.result(usertable.readById(id) , 10 seconds)
    assert(user.id == id)
  }



}
