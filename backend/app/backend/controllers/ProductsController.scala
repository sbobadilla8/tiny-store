package backend.controllers

import backend.services.ProductsService
import play.api.mvc._
import upickle.default._

import backend.models.Product

class ProductsController(val controllerComponents: ControllerComponents, val service: ProductsService) extends BaseController {

  def getAll: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val skip: Int = request.getQueryString("page").map(_.toInt).getOrElse(0)
      val take: Int = request.getQueryString("take").map(_.toInt).getOrElse(10)
      val products: List[Product] = service.getAll(skip, take)
      Ok(write(products)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def getById(id: Int): Action[AnyContent] = Action {
    try {
      val product: Product = service.getById(id)
      Ok(write(product)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def getByCategory(category: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val skip: Int = request.getQueryString("page").map(_.toInt).getOrElse(0)
      val take: Int = request.getQueryString("take").map(_.toInt).getOrElse(10)
      val products: List[Product] = service.getByCategory(category, skip, take)
      Ok(write(products)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def create: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val product: Product = read[Product](request.body.asJson.get.toString)
      val resultId: Long = service.create(product)
      Ok(write(resultId)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def update(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val product: Product = read[Product](request.body.asJson.get.toString)
      service.update(id, product)
      Ok
    } catch {
      case e: InternalError => InternalServerError
      case x: Throwable => BadRequest
    }
  }

  def delete(id: Int): Action[AnyContent] = Action {
    try {
      service.delete(id)
      Ok
    }catch {
      case e: InternalError => InternalServerError
      case x: Throwable => BadRequest
    }
  }
}
