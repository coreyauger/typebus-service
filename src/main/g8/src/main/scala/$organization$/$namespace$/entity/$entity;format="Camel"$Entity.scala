package $organization$.$namespace$.entity

import java.time.LocalDateTime

import com.lightbend.lagom.scaladsl.persistence._
import $organization$.$namespace$.data._
import scala.concurrent.Future

class $entity;format="Camel"$Entity extends PersistentEntity {

  override type Command = UserCommand[_]
  override type Event = UserEvent
  override type State = UserState

  override def initialState = UserState(None, LocalDateTime.now().toString)

  override def behavior: (UserState) => Actions = {
    case UserState(_, _) => Actions()
      .onCommand[CreateUserCommand, User] {
      case (CreateUserCommand(user), ctx, _) ⇒
        ctx.thenPersist(UserCreated(user))(_ ⇒ ctx.reply(user))
    }
      .onReadOnlyCommand[GetUserCommand, User] {
      case (GetUserCommand(id), ctx, state) =>
        ctx.reply(state.user.getOrElse(User(id, "not found")))
    }
      .onEvent {
        case (UserCreated(user), _) ⇒
          UserState(Some(user), LocalDateTime.now().toString)
      }
  }
}

class $entity;format="Camel"$EntityDatabase(persistentEntityRegistry: PersistentEntityRegistry) extends $entity;format="Camel"$Database{
  def createUser(x: CreateUserCommand): Future[User] = persistentEntityRegistry.refFor[$entity;format="Camel"$Entity](x.user.id.toString).ask(x)
  def getUser(x: GetUserCommand): Future[User] = persistentEntityRegistry.refFor[$entity;format="Camel"$Entity](x.id.toString).ask(x)
}
