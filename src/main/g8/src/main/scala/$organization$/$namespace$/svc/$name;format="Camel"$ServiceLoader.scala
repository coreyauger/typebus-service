package $organization$.$namespace$.svc

import akka.actor._
import io.surfkit.typebus.bus.TypebusApplication
import io.surfkit.typebus.bus.kafka.{TypebusKafkaConsumer, TypebusKafkaProducer}
import io.surfkit.typebus.event.ServiceIdentifier

class AwesomeServiceLoader() extends Actor with ActorLogging{
  implicit val system = context.system

  lazy val serviceIdentity = ServiceIdentifier("$name;format="normalize"$")

  // only want to activate and join cluster in certain cases
  lazy val producer = new TypebusKafkaProducer(serviceIdentity, system)
  lazy val service = new $name;format="Camel"$Service(serviceIdentity, producer, system)
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

