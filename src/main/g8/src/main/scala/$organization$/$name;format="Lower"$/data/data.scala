package $organization$.$name;format="Lower"$

import java.util.UUID

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}

import io.surfkit.typebus._
import scala.concurrent.Future

package object data {

  trait $entity;format="Camel"$Command[R] extends ReplyType[R]

  case class Create$entity;format="Camel"$Command(entity: $entity;format="Camel"$) extends $entity;format="Camel"$Command[$entity;format="Camel"$]
  case class Get$entity;format="Camel"$Command(id: UUID) extends $entity;format="Camel"$Command[$entity;format="Camel"$]

  sealed trait $entity;format="Camel"$Event

  case class $entity;format="Camel"$Created(entity: $entity;format="Camel"$) extends $entity;format="Camel"$Event
  case class $entity;format="Camel"$State(entity: Option[$entity;format="Camel"$], timeStamp: String)
  case class $entity;format="Camel"$(id: UUID, data: String)

  object Implicits extends AvroByteStreams{
    implicit val create$entity;format="Camel"$RW = Typebus.declareType[Create$entity;format="Camel"$Command, AvroByteStreamReader[Create$entity;format="Camel"$Command], AvroByteStreamWriter[Create$entity;format="Camel"$Command]]
    implicit val $entity;format="Camel"$RW = Typebus.declareType[$entity;format="Camel"$, AvroByteStreamReader[$entity;format="Camel"$], AvroByteStreamWriter[$entity;format="Camel"$]]
    implicit val get$entity;format="Camel"$RW = Typebus.declareType[Get$entity;format="Camel"$Command, AvroByteStreamReader[Get$entity;format="Camel"$Command], AvroByteStreamWriter[Get$entity;format="Camel"$Command]]
  }

  trait $entity;format="Camel"$Database{
    def create$entity;format="Camel"$(x: Create$entity;format="Camel"$Command): Future[$entity;format="Camel"$]
    def get$entity;format="Camel"$(x: Get$entity;format="Camel"$Command): Future[$entity;format="Camel"$]
  }
}



