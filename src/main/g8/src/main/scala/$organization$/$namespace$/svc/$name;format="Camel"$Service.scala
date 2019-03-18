package $organization$.$namespace$.svc

import akka.actor._
import io.surfkit.typebus._
import io.surfkit.typebus
import io.surfkit.typebus.event.{EventMeta, ServiceIdentifier}
import io.surfkit.typebus.module.Service
import scala.concurrent.ExecutionContext.Implicits.global
$if(cluster_sharding.truthy)$
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import $organization$.$namespace$.cluster.$cluster_actor;format="Camel"$
$endif$
import scala.concurrent.Future
import io.surfkit.typebus.annotations.ServiceMethod
import io.surfkit.typebus.bus.{Publisher, RetryBackoff}
import scala.concurrent.duration._
import $organization$.$namespace$.data._

import scala.concurrent.Future

class $name;format="Camel"$Service(serviceIdentifier: ServiceIdentifier, publisher: Publisher, sys: ActorSystem) extends Service(serviceIdentifier,publisher) with AvroByteStreams{
  implicit val system = sys

  system.log.info("Starting service: " + serviceIdentifier.name)

  val bus = publisher.busActor
  $if(cluster_sharding.truthy)$
  val $cluster_actor;format="camel"$Region = ClusterSharding(system).start(
    typeName = "$cluster_actor;format="Camel"$",
    entityProps = $cluster_actor;format="Camel"$.props(bus),
    settings = ClusterShardingSettings(system),
    extractEntityId = $cluster_actor;format="Camel"$.idExtractor,
    extractShardId = $cluster_actor;format="Camel"$.shardResolver
  )
  $endif$

  import $organization$.$namespace$.data.Implicits._

  var numRequests = 0
  var totalSales = 0.0

  /***
    * This is an example service method, that includes the event meta.  You also have the option
    * of writing a signature that does not require the meta.  Your return type will be auto broadcast
    * to the event bus.  If this is not what you desire you can make a Sink function that returns Future[Unit]
    * @param getLibrary tells our service to return the list of books we server
    * @param meta event meta contains details about the event to help with more advanced routing
    * @return A new type that will be broadcast onto the bus
    */
  @ServiceMethod
  def getLibrary(getLibrary: GetLibrary, meta: EventMeta): Future[Library] = {
    numRequests = numRequests + 1
    Future.successful(Library(library))    // could be some database call
  }
  registerStream(getLibrary _)
    .withRetryPolicy{
    case _ => typebus.bus.RetryPolicy(3, 1 second, RetryBackoff.Exponential)
  }

  /***
    * This is an example that does not need the meta
    * @param order contains the food items that we wish to order
    * @return a receipt
    */
  @ServiceMethod
  def orderBook(order: OrderBook, meta: EventMeta): Future[BookOrdered] = {
    numRequests = numRequests + 1
    val total = library.find(_.id == order.book).map(_.price).getOrElse(0.0)
    totalSales = totalSales + total
    val bookOrdered = Future.successful(BookOrdered(book = order.book, Receipt(total, total * 0.05)) ) // perhaps some db update
    bookOrdered
  }
  registerStream(orderBook _)
    .withPartitionKey(_.book.toString)


  system.log.info("Finished registering streams, trying to start service.")

}

