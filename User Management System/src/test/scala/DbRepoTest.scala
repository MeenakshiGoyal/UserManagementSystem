import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.scalatest.flatspec.AsyncFlatSpec
import java.util.UUID

class DbRepoTest  extends AsyncFlatSpec {
   val dbrepo = new DbRepo

  "User" should " return a list " in {
    val existuser = User(None, "Adi", 21, Value.Admin)
    val id = dbrepo.createUser(existuser)
    val result = dbrepo.readAll
    assert(result == List(dbrepo.readById(id)))
  }


  "it" should "create a user" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    val result = dbrepo.createUser(user)
    assert(result.isDefined)
  }

  "User" should "delete the data from database" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = dbrepo.createUser(existuser)
    val result = dbrepo.delete(id)
    assert(result)
  }

  "User " should "update their values" in {
    val existuser = User(None, "Meenakshi", 21, Value.Admin)
    val id = dbrepo.createUser(existuser)
    val update = dbrepo.readById(id).copy(name = "Manisha")
    val result = dbrepo.update(id, update)
    assert(result)
  }
  "User" should "not update any values " in{
    val newUser = User(None,"Mayank", 22, Value.Customer)
    val userId = dbrepo.createUser(newUser)
    val UserUpdate= dbrepo.readById(userId).copy(name = "Himanshu")
    assertThrows[NoSuchElementException]{
      dbrepo.update(Some(UUID.randomUUID()),UserUpdate)
    }
  }
"User" should "return a list by id" in {
  val existuser = User(None, "Adi", 21, Value.Admin)
  val id = dbrepo.createUser(existuser)
  val user = dbrepo.readById(id)
  assert(user.id == id)
}



}
