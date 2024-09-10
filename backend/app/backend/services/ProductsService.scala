package backend.services

import com.lucidchart.relate._
import upickle.default._
import java.sql.Connection
import play.api.db.Database

import backend.models.Product

class ProductsService(dbApi: play.api.db.DBApi){
  private val database: Database = dbApi.database("default")

  implicit val connection: Connection = database.getConnection(false)

  def getAll(skip: Int, take: Int): List[Product] = {
    sql"SELECT * FROM product WHERE stock > 1 LIMIT $take OFFSET $skip".asList[Product]
  }

  def getById(id: Int): Product = {
    sql"SELECT * FROM product WHERE id = $id".asSingle(Product.parser)
  }

  def getByCategory(category: String, skip: Int, take: Int): List[Product] = {
    sql"""SELECT * FROM product a
         INNER JOIN category b ON a.category = b.id
         WHERE b.name = $category
         LIMIT $take OFFSET $skip
       """.asList[Product]
  }
}
