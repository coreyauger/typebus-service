package $organization$.$name;format="Lower"$.entity

import java.time.LocalDateTime
import java.util.UUID
import $organization$.$name;format="Lower"$.data._
import scala.concurrent.Future

import scala.concurrent.{ExecutionContext, Future}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.cluster.sharding.typed.{ClusterShardingSettings, ShardingEnvelope}
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, Entity, EntityTypeKey}
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}

object $entity;format="Camel"$Entity {

  sealed trait Command
  // command
  final case class EntityCreate$entity;format="Camel"$(id: UUID, create: Create$entity;format="Camel"$)(val replyTo: ActorRef[$entity;format="Camel"$Created]) extends Command
  // query
  final case class EntityGet$entity;format="Camel"$(get: Get$entity;format="Camel"$)(val replyTo: ActorRef[$entity;format="Camel"$]) extends Command
  final case class EntityGetState(id: UUID)(val replyTo: ActorRef[$entity;format="Camel"$State]) extends Command

  val entityTypeKey: EntityTypeKey[Command] =
    EntityTypeKey[Command]("$entity;format="Camel"$Entity")

  def behavior(entityId: String): Behavior[Command] =
    EventSourcedBehavior[Command, $entity;format="Camel"$Event, $entity;format="Camel"$State](
    persistenceId = PersistenceId(entityId),
    emptyState =  $entity;format="Camel"$State(None),
    commandHandler,
    eventHandler)

  private val commandHandler: ($entity;format="Camel"$State, Command) => Effect[$entity;format="Camel"$Event, $entity;format="Camel"$State] = { (state, command) =>
    command match {
      case x: EntityCreate$entity;format="Camel"$ =>
        val id = x.id
        val entity = $entity;format="Camel"$(id, x.create.data)
        val created = $entity;format="Camel"$Created(entity)
        Effect.persist(created).thenRun(_ => x.replyTo.tell(created))

      case x: EntityGet$entity;format="Camel"$ =>
        state.entity.map(x.replyTo.tell)
        Effect.none

      case x: EntityGetState =>
        x.replyTo.tell(state)
        Effect.none

      case _ => Effect.unhandled
    }
  }

  private val eventHandler: ($entity;format="Camel"$State, $entity;format="Camel"$Event) => $entity;format="Camel"$State = { (state, event) =>
    state match {
      case state: $entity;format="Camel"$State =>
        event match {
        case $entity;format="Camel"$Created(module) =>
          $entity;format="Camel"$State(Some(module))
        case _ => throw new IllegalStateException(s"unexpected event [\$event] in state [\$state]")
      }
      case _ => throw new IllegalStateException(s"unexpected event [\$event] in state [\$state]")
    }
  }

}


class $entity;format="Camel"$EntityDatabase(system: ActorSystem[_])(implicit val ex: ExecutionContext)
  extends $entity;format="Camel"$Database with CQRSDatabase[$entity;format="Camel"$State]{
  import akka.util.Timeout
  import scala.concurrent.duration._
  private implicit val askTimeout: Timeout = Timeout(5.seconds)

  val TypeKey = $entity;format="Camel"$Entity.entityTypeKey
  val sharding =  ClusterSharding(system)
  val psEntities: ActorRef[ShardingEnvelope[$entity;format="Camel"$Entity.Command]] =
    sharding.init(Entity(typeKey = TypeKey,
      createBehavior = ctx => $entity;format="Camel"$Entity.behavior(ctx.entityId))
      .withSettings(ClusterShardingSettings(system)))

  def entity(id: String) =
    sharding.entityRefFor($entity;format="Camel"$Entity.entityTypeKey, id)

  override def create$entity;format="Camel"$(x: Create$entity;format="Camel"$): Future[$entity;format="Camel"$Created] = {
    val id = UUID.randomUUID()
    entity(id.toString) ? $entity;format="Camel"$Entity.EntityCreate$entity;format="Camel"$(id, x)
  }

  override def get$entity;format="Camel"$(x: Get$entity;format="Camel"$): Future[$entity;format="Camel"$] =
    entity(x.id.toString) ? $entity;format="Camel"$Entity.EntityGet$entity;format="Camel"$(x)

  override def getState(id: String): Future[$entity;format="Camel"$State] =
    entity(id) ? $entity;format="Camel"$Entity.EntityGetState(UUID.fromString(id))
}
