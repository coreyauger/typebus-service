package $organization$.$name;format="Lower"$

import java.util.UUID
import io.surfkit.typebus._
import io.surfkit.typebus.event.DbAccessor
import io.surfkit.typebus.entity.EntityDb
import scala.concurrent.Future

package object data {


  sealed trait $entity;format="Camel"$Command
  case class Create$entity;format="Camel"$(data: String) extends $entity;format="Camel"$Command
  case class Get$entity;format="Camel"$(id: UUID) extends $entity;format="Camel"$Command
  case class Get$entity;format="Camel"$EntityState(id: String) extends $entity;format="Camel"$Command with DbAccessor

  sealed trait $entity;format="Camel"$Event
  case class $entity;format="Camel"$Created(entity: $entity;format="Camel"$) extends $entity;format="Camel"$Event
  case class $entity;format="Camel"$CompensatingActionPerformed(state: $entity;format="Camel"$State) extends $entity;format="Camel"$Event
  case class $entity;format="Camel"$State(entity: Option[$entity;format="Camel"$])
  case class $entity;format="Camel"$(id: UUID, data: String)

  object Implicits extends AvroByteStreams{
    implicit val create$entity;format="Camel"$RW = Typebus.declareType[Create$entity;format="Camel"$, AvroByteStreamReader[Create$entity;format="Camel"$], AvroByteStreamWriter[Create$entity;format="Camel"$]]
    implicit val $entity;format="Camel"$CreatedRW = Typebus.declareType[$entity;format="Camel"$Created, AvroByteStreamReader[$entity;format="Camel"$Created], AvroByteStreamWriter[$entity;format="Camel"$Created]]
    implicit val $entity;format="Camel"$RW = Typebus.declareType[$entity;format="Camel"$, AvroByteStreamReader[$entity;format="Camel"$], AvroByteStreamWriter[$entity;format="Camel"$]]
    implicit val get$entity;format="Camel"$RW = Typebus.declareType[Get$entity;format="Camel"$, AvroByteStreamReader[Get$entity;format="Camel"$], AvroByteStreamWriter[Get$entity;format="Camel"$]]
    implicit val get$entity;format="Camel"$EntityStateRW = Typebus.declareType[Get$entity;format="Camel"$EntityState, AvroByteStreamReader[Get$entity;format="Camel"$EntityState], AvroByteStreamWriter[Get$entity;format="Camel"$EntityState]]
    implicit val $entity;format="Camel"$StateRW = Typebus.declareType[$entity;format="Camel"$State, AvroByteStreamReader[$entity;format="Camel"$State], AvroByteStreamWriter[$entity;format="Camel"$State]]
  }

  trait $entity;format="Camel"$Database extends EntityDb[$entity;format="Camel"$State]{
    def create$entity;format="Camel"$(x: Create$entity;format="Camel"$): Future[$entity;format="Camel"$Created]
    def get$entity;format="Camel"$(x: Get$entity;format="Camel"$): Future[$entity;format="Camel"$]
  }
}



