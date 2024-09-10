package backend.controllers

import backend.services.ProductsService
import play.api.libs.json._
import play.api.mvc._
import upickle.default._

class ProductsController(val controllerComponents: ControllerComponents, val service: ProductsService) extends BaseController {


  def getAll: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val skip = request.getQueryString("page").map(_.toInt).getOrElse(0)
      val take = request.getQueryString("take").map(_.toInt).getOrElse(10)
      val products = service.getAll(skip, take)
      Ok(write(products)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def getById(id: Int): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val product = service.getById(id)
      Ok(write(product)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

  def getByCategory(category: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    try {
      val skip = request.getQueryString("page").map(_.toInt).getOrElse(0)
      val take = request.getQueryString("take").map(_.toInt).getOrElse(10)
      val products = service.getByCategory(category, skip, take)
      Ok(write(products)).as(JSON)
    } catch {
      case e: InternalError => InternalServerError
      case _: Throwable => BadRequest
    }
  }

}
