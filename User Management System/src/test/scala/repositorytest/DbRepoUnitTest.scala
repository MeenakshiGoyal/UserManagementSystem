package repositorytest
import org.knoldus.db.UserTable
import org.knoldus.models.{User, Value}
import org.knoldus.repository.DbRepo
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AsyncFlatSpec
import java.util.UUID
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

class DbRepoUnitTest  extends AsyncFlatSpec {
  val mockusertable:UserTable  = mock[UserTable]
  val dbrepo: DbRepo = new DbRepo(mockusertable)

  "Create user " should "add a new user" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    when(mockusertable.createUser(user)) thenReturn Future.successful(Option(UUID.randomUUID()))
    val result = Await.result(dbrepo.createUser(user),10 seconds)
    assert(result.nonEmpty)
  }

  "Delete" should "delete a exist user" in {
    val uuid = UUID.randomUUID()
    when(mockusertable.delete(Some(uuid))) thenReturn Future.successful(true)
    val result = Await.result(dbrepo.delete(Some(uuid)),10 seconds)
    assert(result)
  }
 "Read by id " should "return a user list by id " in {
   val user = User(None, "Meenakshi", 21, Value.Admin)
   val uuid = UUID.randomUUID()
   when(mockusertable.readById(Some(uuid))) thenReturn(Future.successful(user))
   val result = Await.result(dbrepo.readById(Some(uuid)),10 seconds)
   assert(result==user)
 }

 "Read all users" should "return a userlist" in {
   val user = User(None, "Meenakshi", 21, Value.Admin)
   when(mockusertable.readAll) thenReturn Future.successful(List(user))
   val result = Await.result(dbrepo.readAll,10 seconds)
   assert(result.nonEmpty)
 }

  "Update" should "update a value by id" in {
    val user = User(None, "Meenakshi", 21, Value.Admin)
    when(mockusertable.update(user.id,user)) thenReturn(Future.successful(true))
    val result = Await.result(dbrepo.update(user.id ,user),10 seconds)
    assert(result)
  }
}
