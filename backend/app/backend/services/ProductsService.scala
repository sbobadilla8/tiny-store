package backend.services

import com.lucidchart.relate._
import upickle.default._
import java.sql.Connection
import play.api.db.Database

import backend.models.Product

class ProductsService(dbApi: play.api.db.DBApi) {
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

  def create(product: Product): Long = {
    val productTuple = (product.name, product.price, product.imageUrl, product.category, product.artist, product.stock)
    val id =
      sql"""INSERT INTO product(name, price, imageUrl, category, artist, stock)
            VALUES ($productTuple)
         """.executeInsertLong()
    sql"COMMIT".execute()
    id
  }

  def update(id: Int, product: Product): Unit = {
    val name = product.name
    val price = product.price
    val imageUrl = product.imageUrl
    val category = product.category
    val artist = product.artist
    val stock = product.stock
    val status = product.status
    sql"""UPDATE product SET name = $name, price = $price, imageUrl = $imageUrl, category = $category,
                            artist = $artist, stock = $stock, status = $status
          WHERE id = $id
       """.executeUpdate()
    sql"COMMIT".execute()
  }

  def delete(id: Int): Unit = {
    sql"""DELETE FROM product WHERE id = $id""".execute()
    sql"""COMMIT""".execute()
  }
}
