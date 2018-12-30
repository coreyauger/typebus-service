package $organization$.$namespace$.svc

import akka.actor._
import io.surfkit.typebus._
import io.surfkit.typebus.event.EventMeta
import io.surfkit.typebus.module.Service
import scala.concurrent.ExecutionContext.Implicits.global
import io.surfkit.typebus.bus.$bus_type;format="lower"$.$bus_type;format="cap"$Bus
$if(cluster_sharding.truthy)$
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings}
import $organization$.$namespace$.cluster.$cluster_actor;format="Camel"$
$endif$

import $organization$.$namespace$.data._

import scala.concurrent.Future

class $name;format="Camel"$ extends Service[BaseType]("$name;format="normalize"$") with Actor with ActorLogging with $bus_type;format="cap"$Bus[BaseType] with AvroByteStreams{
  implicit val system = context.system
  import context.dispatcher

  log.info("Starting service: " + serviceName)

  $if(cluster_sharding.truthy)$
  val bus = busActor
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
  def getLibrary(getLibrary: GetLibrary, meta: EventMeta): Future[Library] = {
    numRequests = numRequests + 1
    Future.successful(Library(library))    // could be some database call
  }

  /***
    * This is an example that does not need the meta
    * @param order contains the food items that we wish to order
    * @return a receipt
    */
  def processOrder(order: OrderBook): Future[Receipt] = {
    numRequests = numRequests + 1
    val total = order.book.map(_.price).sum
    totalSales = totalSales + total
    val receipt = Future.successful(Receipt(total, total * 0.05) ) // perhaps some db update
    receipt
  }

  /***
    * This is a sync.  It will not show up as a method call in your service definition.  You can use this kind
    * of call to route to cluster actors or perform operations that don't return a value.
    * @param getStats request to get stats on sales
    * @return Unit
    */
  def getStatus(getStats: GetStats, meta: EventMeta): Future[Unit] = {
    replyToSender(meta, Stats(numRequests, if(getStats.withTotalSaves) Some(totalSales) else None ))
    Future.successful(Unit)
  }

  registerStream(getLibrary _)
  registerStream(processOrder _)
  registerStream(getStatus _)

  log.info("Finished registering streams, trying to start service.")
  startTypeBus


}

