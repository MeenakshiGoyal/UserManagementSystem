package ServiceTest
import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.knoldus.service.UserService
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AsyncFlatSpec
import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

class Userservicetest extends AsyncFlatSpec {
  val mockRepo: DbRepo = mock[DbRepo]
  val userService = new UserService(mockRepo)

  "addUser" should "create a user" in {
    when(mockRepo.readAll) thenReturn Future.successful(List())
    val newuser = User(None, "Meenakshi", 21, Value.Admin)
    when(mockRepo.createUser(newuser)) thenReturn Future.successful(Some(UUID.randomUUID()))
    val result = Await.result(userService.addUser(newuser), 10 seconds)
    assert(Some(result).nonEmpty)
  }

  it should "not create any user when id is empty" in {
    when(mockRepo.readAll) thenReturn Future.successful(List.empty)
    val newuser = User(Some(UUID.randomUUID()), "Meenakshi", 21, Value.Admin)
    when(mockRepo.createUser(newuser)) thenReturn Future.successful(None)
    val result = Await.result(userService.addUser(newuser), 10 seconds)
    assert(result.isEmpty)
  }

  "Delete" should "delete any user by id" in {
    val uuid = UUID.randomUUID()
    when(mockRepo.delete(Some(uuid))) thenReturn Future.successful(true)
    val result = Await.result(userService.doDelete(Some(uuid)), 10 seconds)
    assert(result)
  }

  it should " not delete any user by id" in {
    val uuid = UUID.randomUUID()
    when(mockRepo.delete(Some(uuid))) thenReturn Future.successful(false)
    val result = Await.result(userService.doDelete(Some(uuid)), 10 seconds)
    assert(result)
  }

  "Read all" should "get all user list by id" in {
    val user = User(Some(UUID.randomUUID()), "Meenakshi", 21, Value.Admin)
    when(mockRepo.readAll) thenReturn Future.successful(List(user))
    val result = Await.result(userService.getUserList(), 10 seconds)
    assert(result.nonEmpty)
  }

  "it" should "not get all user list by id" in {
    val user = User(Some(UUID.randomUUID()), "Meenakshi", 21, Value.Admin)
    when(mockRepo.readAll) thenReturn Future.successful(List.empty)
    val result = Await.result(userService.getUserList(), 10 seconds)
    assert(result.isEmpty)
  }

  it should "not update any user value if id is not their" in {
    val user = User(Some(UUID.randomUUID()), "Meenakshi", 21, Value.Admin)
    val id = Some(UUID.randomUUID())
    when(mockRepo.update(id, user)) thenThrow new NoSuchElementException()
    assertThrows[NoSuchElementException](Await.result(userService.doUpdate(id, user),10 seconds))
  }

  "Read any user" should "return a user by id" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    val uuid = UUID.randomUUID()
    when(mockRepo.readById(Some(uuid))) thenReturn (Future.successful(user))
    val result = Await.result(userService.getUserId(Some(uuid)), 10 seconds)
    assert(result == user)
  }
   "Read any user" should " not return a user by id" in {
      val uuid = Some(UUID.randomUUID())
      val user = User(uuid, "Meenakshi", 21, Value.Admin)
      when(mockRepo.readById(uuid)) thenThrow new NoSuchElementException()
      assertThrows[NoSuchElementException](Await.result(userService.getUserId(user.id), 10 seconds))
    }
  }



