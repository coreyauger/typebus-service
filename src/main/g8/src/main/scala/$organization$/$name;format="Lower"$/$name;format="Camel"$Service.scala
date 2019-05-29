package $organization$.$name;format="Lower"$

import akka.actor._
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import $organization$.$name;format="Lower"$.data._
import io.surfkit.typebus
import io.surfkit.typebus._
import io.surfkit.typebus.annotations.ServiceMethod
import io.surfkit.typebus.bus.{Publisher, RetryBackoff}
import io.surfkit.typebus.event.{EventMeta, ServiceIdentifier}
import io.surfkit.typebus.module.Service

import scala.concurrent.Future
import scala.concurrent.duration._

class $name;format="Camel"$Service(serviceIdentifier: ServiceIdentifier, publisher: Publisher, sys: ActorSystem, userDb: $entity;format="Camel"$Database) extends Service(serviceIdentifier,publisher) with AvroByteStreams{
  implicit val system = sys

  system.log.info("Starting service: " + serviceIdentifier.name)
  val bus = publisher.busActor
  import $organization$.$name;format="Lower"$.data.Implicits._

  @ServiceMethod
  def createUser(createUser: CreateUserCommand, meta: EventMeta): Future[User] = userDb.createUser(createUser)
  registerStream(createUser _)
    .withPartitionKey(_.id.toString)

  @ServiceMethod
  def getUser(getUser: GetUserCommand, meta: EventMeta): Future[User] = userDb.getUser(getUser)
  registerStream(getUser _)
    //.withPartitionKey(_.user.id.toString)
    .withRetryPolicy{
    case _ => typebus.bus.RetryPolicy(3, 1 second, RetryBackoff.Exponential)
  }

  system.log.info("Finished registering streams, trying to start service.")

}