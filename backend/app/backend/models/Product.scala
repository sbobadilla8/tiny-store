package backend.models

import com.lucidchart.relate.{RowParser, SqlRow}
import upickle.default._

case class Product(
                    id: Int = 0,
                    name: String,
                    price: Float,
                    imageUrl: String,
                    category: Int,
                    artist: Int,
                    stock: Int,
                    status: String
                  )

object Product{

  implicit val productRw: ReadWriter[Product] = macroRW

  implicit val parser: RowParser[Product] = (row: SqlRow) => {
    Product(
      row.int("id"),
      row.string("name"),
      row.strictFloat("price"),
      row.string("imageUrl"),
      row.int("category"),
      row.int("artist"),
      row.int("stock"),
      row.string("status")
    )
  }

}

