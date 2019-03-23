package $organization$.$namespace$

import java.util.UUID

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}

import io.surfkit.typebus._
import scala.concurrent.Future

package object data {

  trait UserCommand[R] extends ReplyType[R]

  case class CreateUserCommand(user: User) extends UserCommand[User]
  case class GetUserCommand(id: UUID) extends UserCommand[User]

  sealed trait UserEvent extends AggregateEvent[UserEvent] {
    override def aggregateTag: AggregateEventTagger[UserEvent] = UserEvent.Tag
  }
  object UserEvent {
    val Tag: AggregateEventTag[UserEvent] = AggregateEventTag[UserEvent]
  }
  case class UserCreated(user: User) extends UserEvent

  case class UserState(user: Option[User], timeStamp: String)

  case class User(id: UUID, name: String)

  object Implicits extends AvroByteStreams{
    implicit val createUserRW = Typebus.declareType[CreateUserCommand, AvroByteStreamReader[CreateUserCommand], AvroByteStreamWriter[CreateUserCommand]]
    implicit val userRW = Typebus.declareType[User, AvroByteStreamReader[User], AvroByteStreamWriter[User]]
    implicit val getUserRW = Typebus.declareType[GetUserCommand, AvroByteStreamReader[GetUserCommand], AvroByteStreamWriter[GetUserCommand]]
  }

  trait $entity;format="Camel"$Database{
    def createUser(x: CreateUserCommand): Future[User]
    def getUser(x: GetUserCommand): Future[User]
  }
}



