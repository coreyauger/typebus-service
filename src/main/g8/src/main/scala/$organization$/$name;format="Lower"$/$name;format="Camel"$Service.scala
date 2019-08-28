package $organization$.$name;format="Lower"$

import akka.actor._
import $organization$.$name;format="Lower"$.data._
import io.surfkit.typebus
import io.surfkit.typebus._
import io.surfkit.typebus.annotations.ServiceMethod
import io.surfkit.typebus.bus.{Publisher, RetryBackoff}
import io.surfkit.typebus.event.{EventMeta, ServiceIdentifier}
import io.surfkit.typebus.module.Service

import scala.concurrent.Future
import scala.concurrent.duration._

class $name;format="Camel"$Service(serviceIdentifier: ServiceIdentifier, publisher: Publisher, sys: ActorSystem, $entity;format="lower"$Db: $entity;format="Camel"$Database) extends Service(serviceIdentifier,publisher) with AvroByteStreams{
  implicit val system = sys

  system.log.info("Starting service: " + serviceIdentifier.name)
  val bus = publisher.busActor
  import $organization$.$name;format="Lower"$.data.Implicits._

  @ServiceMethod
  def create$entity;format="Camel"$(create$entity;format="Camel"$: Create$entity;format="Camel"$, meta: EventMeta): Future[$entity;format="Camel"$Created] = $entity;format="lower"$Db.create$entity;format="Camel"$(create$entity;format="Camel"$)
  registerStream(create$entity;format="Camel"$ _)
    .withPartitionKey(_.entity.id.toString)

  @ServiceMethod
  def get$entity;format="Camel"$(get$entity;format="Camel"$: Get$entity;format="Camel"$, meta: EventMeta): Future[$entity;format="Camel"$] = $entity;format="lower"$Db.get$entity;format="Camel"$(get$entity;format="Camel"$)
  registerStream(get$entity;format="Camel"$ _)
    .withRetryPolicy{
    case _ => typebus.bus.RetryPolicy(3, 1 second, RetryBackoff.Exponential)
  }

  system.log.info("Finished registering streams, trying to start service.")

}