package $organization$.$namespace$

import io.surfkit.typebus._
import io.surfkit.typebus.Implicits._

package object data {

  sealed trait BaseType{}

  final case class Author(name: String, twitter: Option[String] = None) extends BaseType
  final case class Book(name: String, imgUrl: String, price: Double, author: Author, languages: Seq[String], isbn: String, description: String) extends BaseType

  final case class GetLibrary(filter: String) extends BaseType
  final case class Library(books: Seq[Book]) extends BaseType

  final case class OrderBook(book: Seq[Book]) extends BaseType
  final case class Receipt(total: Double, tax: Double) extends BaseType

  final case class GetStats(withTotalSaves: Boolean = true) extends BaseType
  final case class Stats(numRequests: Int, totalSales: Option[Double] = None) extends BaseType


  object Implicits extends AvroByteStreams{
    implicit val GetLibraryReader = new AvroByteStreamReader[GetLibrary]
    implicit val GetLibraryWriter = new AvroByteStreamWriter[GetLibrary]
    implicit val LibraryReader = new AvroByteStreamReader[Library]
    implicit val LibraryWriter = new AvroByteStreamWriter[Library]
    implicit val OrderBookReader = new AvroByteStreamReader[OrderBook]
    implicit val OrderBookWriter = new AvroByteStreamWriter[OrderBook]
    implicit val RecieptReader = new AvroByteStreamReader[Receipt]
    implicit val RecieptWriter = new AvroByteStreamWriter[Receipt]
    implicit val GetStatsReader = new AvroByteStreamReader[GetStats]
    implicit val GetStatsWriter = new AvroByteStreamWriter[GetStats]
    implicit val StatsReader = new AvroByteStreamReader[Stats]
    implicit val StatsWriter = new AvroByteStreamWriter[Stats]
  }

  // exmaple database of foods.
  val russelBrand = Author(
    name = "Russell Brand",
    twitter = Some("rustyrockets")
  )
  val yuval = Author(
    name = "Yuval Noah Harari",
    twitter = Some("harari_yuval")
  )
  val brene = Author(
    name = "Brené Brown",
    twitter = Some("BreneBrown")
  )

  val library = Seq(
    Book(name = "Recovery: Freedom from Our Addictions", imgUrl="https://images-na.ssl-images-amazon.com/images/I/51VK-ZBstiL._SX324_BO1,204,203,200_.jpg", price=29.50, author=russelBrand, languages=Seq("eng", "fr"), isbn = "125018245X", description="This manual for self-realization comes not from a mountain but from the mud...My qualification is not that I am better than you but I am worse. ―Russell Brand"),
    Book(name = "Revolution", imgUrl="https://images-na.ssl-images-amazon.com/images/I/51Yq0Sj%2B4aL._SX327_BO1,204,203,200_.jpg", price=22.50, author=russelBrand, languages=Seq("eng", "fr"), isbn = "125018245X", description="We all know the system isn’t working. Our governments are corrupt and the opposing parties pointlessly similar. Our culture is filled with vacuity and pap, and we are told there’s nothing we can do: It’s just the way things are."),
    Book(name = "Sapiens: A Brief History of Humankind", imgUrl="https://images-na.ssl-images-amazon.com/images/I/51a9YweAk5L._SX332_BO1,204,203,200_.jpg", price=24.50, author=yuval, languages=Seq("eng", "fr" ,"gr"), isbn = "0771038518", description="Destined to become a modern classic in the vein of Guns, Germs, and Steel, Sapiens is a lively, groundbreaking history of humankind told from a unique perspective. \n     100,000 years ago, at least six species of human inhabited the earth. Today there is just one. \n     Us. \nHomo Sapiens. "),
    Book(name = "Dare to Lead: Brave Work. Tough Conversations. Whole Hearts", imgUrl="https://images-na.ssl-images-amazon.com/images/I/41TiKQ235HL._SX327_BO1,204,203,200_.jpg", price=24.50, author=brene, languages=Seq("eng", "fr" ,"gr"), isbn = "8925598914", description="#1 NEW YORK TIMES BESTSELLER • Brené Brown has taught us what it means to dare greatly, rise strong, and brave the wilderness. Now, based on new research conducted with leaders, change makers, and culture shifters, she’s showing us how to put those ideas into practice so we can step up and lead."),
    Book(name = "The Power of Vulnerability: Teachings of Authenticity, Connection, and Courage", imgUrl="https://images-na.ssl-images-amazon.com/images/I/51jK8rnRLrL._AA300_.jpg", price=27.50, author=brene, languages=Seq("eng", "fr"), isbn = "B072BQ4PV6", description="Is vulnerability the same as weakness? \"In our culture,\" teaches Dr. Brené Brown, \"we associate vulnerability with emotions we want to avoid such as fear, shame, and uncertainty. Yet we too often lose sight of the fact that vulnerability is also the birthplace of joy, belonging, creativity, authenticity, and love.\" On The Power of Vulnerability, Dr. Brown offers an invitation and a promise - that when we dare to drop the armor that protects us from feeling vulnerable, we open ourselves to the experiences that bring purpose and meaning to our lives. Here she dispels the cultural myth that vulnerability is weakness and reveals that it is, in truth, our most accurate measure of courage.")
  )
}






