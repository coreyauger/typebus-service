package $organization$.$namespace$

import java.util.UUID

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.InvalidCommandException
import $organization$.$namespace$.data._
import $organization$.$namespace$.entity.$entity;format="Camel"$Entity
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class $entity;format="Camel"$EntitySpec extends WordSpec with Matchers with BeforeAndAfterAll {

  private val system = ActorSystem("$name;format="normalize"$")

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
    system.terminate
  }

  val userId = UUID.randomUUID()
  val driver = new PersistentEntityTestDriver(system, new $entity;format="Camel"$Entity, userId.toString)

  val userNotFound = UUID.randomUUID()
  val notFoundDrive = new PersistentEntityTestDriver(system, new $entity;format="Camel"$Entity, userNotFound.toString)


  val someUser = User(
    id = userId,
    name = "Some Name"
  )

  "user entity" should {

    "create a user" in  {
      val outcome = driver.run(CreateUserCommand(someUser))
      val retUser = outcome.replies.head.asInstanceOf[User]
      //driver.getAllIssues should have size 0                // fixme: complains about java serialization being used
      assert( retUser == someUser )
    }

    "be able to get the new user user" in  {
      val outcome = driver.run(GetUserCommand(userId))
      val user = outcome.replies.head.asInstanceOf[User]
      assert( user == someUser )
    }

  }
}
