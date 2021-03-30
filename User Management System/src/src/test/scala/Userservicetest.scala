import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.knoldus.service.UserService
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AsyncFlatSpec
import java.util.UUID

class Userservicetest extends AsyncFlatSpec {
  val mockrepo = mock[DbRepo]
  val Userservice = new UserService(mockrepo)

  "addUser" should "create a user" in {
    when(mockrepo.readAll())thenReturn List.empty[User]
    val newuser = User(None , "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.createUser(newuser)) thenReturn Some(UUID.randomUUID())
    val user = Userservice.addUser(newuser)
    assert(user.isDefined)
  }

  it should "not create any user when id is empty" in {
    when(mockrepo.readAll())thenReturn List.empty[User]
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.createUser(newuser)) thenReturn None
    val user = Userservice.addUser(newuser)
    assert(user.isEmpty)
  }

  "it" should "delete any user by id" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.createUser(newuser)) thenReturn Some(UUID.randomUUID())
    val user = Userservice.addUser(newuser)
    when(mockrepo.delete(user)) thenReturn true
    val userDelete = Userservice.doDelete(user)
    assert(userDelete)
  }

  "it" should "get all user list by id" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.readAll) thenReturn List(newuser)
    assert(Userservice.getUserList().nonEmpty)
  }

  "it" should "not get all user list by id" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.readAll) thenReturn List.empty
    assert(Userservice.getUserList().isEmpty)
  }

  "it" should "update any user by id" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.createUser(newuser)) thenReturn Some(UUID.randomUUID())
    val user = Userservice.addUser(newuser)
    when(mockrepo.readById(user)) thenReturn newuser.copy(name = "Manisha")
    val updateuser = Userservice.getUserId(user).copy(name = "Manisha")
    when(mockrepo.update(user,updateuser))thenReturn true
    val updated = Userservice.doUpdate(user,updateuser)
    assert(updated)
  }
  it should "not update any user value if id is not their" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi", 21, Value.Admin)
    val id = Some(UUID.randomUUID())
    when(mockrepo.update(id, newuser)) thenThrow new NoSuchElementException()
    assertThrows[NoSuchElementException] {
      Userservice.doUpdate(id, newuser)
    }
  }
  it should "read any user by id" in {
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi" , 21 , Value.Admin)
    when(mockrepo.createUser(newuser)) thenReturn Some(UUID.randomUUID())
    val user = Userservice.addUser(newuser)
    when(mockrepo.readById(user)) thenReturn newuser.copy(id = user)
    val newuserid = Userservice.getUserId(user)
    assert(newuserid.id == user)
  }
}