package $organization$.$name;format="Lower"$

import java.io.File
import akka.actor._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import $organization$.$name;format="Lower"$.entity._
import io.surfkit.typebus.bus.TypebusApplication
import io.surfkit.typebus.bus.kafka.{TypebusKafkaConsumer, TypebusKafkaProducer}
import io.surfkit.typebus.event.ServiceIdentifier

class $name;format="Camel"$ServiceLoader()
  extends Actor
    with CassandraPersistenceComponents
    with ActorLogging{

  implicit val system = context.system
  implicit val actorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))


  override def serviceLocator: ServiceLocator = ???

  // Members declared in com.lightbend.lagom.scaladsl.server.AkkaManagementComponents
  def coordinatedShutdown: akka.actor.CoordinatedShutdown = akka.actor.CoordinatedShutdown(context.system)

  // Members declared in com.lightbend.lagom.scaladsl.cluster.ClusterComponents
  def environment: play.api.Environment = play.api.Environment(new File("."), this.getClass.getClassLoader, play.api.Mode.Dev)

  // Members declared in com.lightbend.lagom.scaladsl.persistence.ReadSidePersistenceComponents
  def actorSystem: akka.actor.ActorSystem = context.system
  def configuration: play.api.Configuration = play.api.Configuration(ConfigFactory.load)
  def executionContext: scala.concurrent.ExecutionContext = context.system.dispatcher
  def materializer: akka.stream.Materializer = actorMaterializer


  // Members declared in com.lightbend.lagom.scaladsl.playjson.RequiresJsonSerializerRegistry
  def jsonSerializerRegistry: com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry = ???

  val $entity;format="Camel"$Entity = new $entity;format="Camel"$Entity
  persistentEntityRegistry.register(wire[$entity;format="Camel"$Entity])


  lazy val serviceIdentity = ServiceIdentifier("$name;format="normalize"$")

  // only want to activate and join cluster in certain cases
  //ZookeeperClusterSeed(system).join()
  lazy val producer = new TypebusKafkaProducer(serviceIdentity, system)
  lazy val service = new $name;format="Camel"$Service(serviceIdentity, producer, system, new $entity;format="Camel"$EntityDatabase(persistentEntityRegistry) )
  lazy val consumer = new TypebusKafkaConsumer(service, producer, system)

  TypebusApplication
  (
    system,
    serviceIdentity,
    producer,
    service,
    consumer
  )


  override def receive = {
    case _ =>
  }

}

object $name;format="Camel"$ServiceLoader extends App{
  val system = ActorSystem("$name;format="normalize"$")
  system.actorOf(Props(new $name;format="Camel"$ServiceLoader))

  Thread.currentThread().join()
}

