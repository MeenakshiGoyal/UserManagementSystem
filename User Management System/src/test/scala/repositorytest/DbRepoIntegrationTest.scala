package repositorytest
import org.knoldus.db.UserTable
import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class DbRepoIntegrationTest  extends AsyncFlatSpec {
  val usertable  = new UserTable
   val dbrepo = new DbRepo(usertable)

  "User" should " return a list " in {
    val existuser = User(None, "Adi", 21, Value.Admin)
    val id = Await.result(dbrepo.createUser(existuser) , 20 seconds)
    val result = Await.result(dbrepo.readAll , 20 seconds )
    assert(result.nonEmpty)
  }

  "Create user" should "create a user" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    val result = Await.result(dbrepo.createUser(user), 10 seconds )
    assert(result.isDefined)
  }

  "User" should "delete the data from database" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = Await.result(dbrepo.createUser(existuser) , 10 seconds)
    val result = Await.result(dbrepo.delete(id), 10 seconds)
    assert(result)
  }

  "User " should "update their values" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = Await.result(dbrepo.createUser(existuser) , 10 seconds)
    val update = User(None,"Manisha" , 21 , Value.Admin)
    val result = Await.result(dbrepo.update(id, update),10 seconds)
    assert(result)
  }

"User" should "return a list by id" in {
  val existuser = User(None, "Adi", 21, Value.Admin)
  val id = Await.result(dbrepo.createUser(existuser) , 10 seconds)
  val user = Await.result(dbrepo.readById(id) , 10 seconds)
  assert(user.id == id)
}




}
