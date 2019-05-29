package $organization$.$name;format="Lower"$.entity

import java.time.LocalDateTime

import com.lightbend.lagom.scaladsl.persistence._
import $organization$.$name;format="Lower"$.data._
import scala.concurrent.Future

class $entity;format="Camel"$Entity extends PersistentEntity {

  override type Command = $entity;format="Camel"$Command[_]
  override type Event = $entity;format="Camel"$Event
  override type State = $entity;format="Camel"$State

  override def initialState = $entity;format="Camel"$State(None, LocalDateTime.now().toString)

  override def behavior: ($entity;format="Camel"$State) => Actions = {
    case $entity;format="Camel"$State(_, _) => Actions()
      .onCommand[Create$entity;format="Camel"$Command, $entity;format="Camel"$] {
      case (Create$entity;format="Camel"$Command(x), ctx, _) ⇒
        ctx.thenPersist($entity;format="Camel"$Created(x))(_ ⇒ ctx.reply(x))
    }.onReadOnlyCommand[Get$entity;format="Camel"$Command, $entity;format="Camel"$] {
      case (Get$entity;format="Camel"$Command(id), ctx, state) =>
        ctx.reply(state.entity.getOrElse($entity;format="Camel"$(id, "not found")))
    }
      .onEvent {
        case ($entity;format="Camel"$Created(x), _) ⇒
          $entity;format="Camel"$State(Some(x), LocalDateTime.now().toString)
      }
  }
}

class $entity;format="Camel"$EntityDatabase(persistentEntityRegistry: PersistentEntityRegistry) extends $entity;format="Camel"$Database{
  def create$entity;format="Camel"$(x: Create$entity;format="Camel"$Command): Future[$entity;format="Camel"$] = persistentEntityRegistry.refFor[$entity;format="Camel"$Entity](x.entity.id.toString).ask(x)
  def get$entity;format="Camel"$(x: Get$entity;format="Camel"$Command): Future[$entity;format="Camel"$] = persistentEntityRegistry.refFor[$entity;format="Camel"$Entity](x.id.toString).ask(x)
}
