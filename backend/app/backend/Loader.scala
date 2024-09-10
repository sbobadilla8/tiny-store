package backend

import backend.controllers.ProductsController
import backend.services.ProductsService
import play.api.ApplicationLoader.Context
import play.api._
import play.api.routing.Router
import router.Routes
import play.api.db.{DBComponents, Database, HikariCPComponents}


class Loader extends ApplicationLoader {
  def load(context: Context): Application = {
    new Components(context).application
  }
}

class Components(context: Context) extends BuiltInComponentsFromContext(context) with DBComponents with HikariCPComponents {
  override lazy val httpFilters = Nil

  // Services
  lazy val productsService = new ProductsService(dbApi)

  // Controllers
  lazy val productsController = new ProductsController(controllerComponents, productsService)

  lazy val router: Router = new Routes(httpErrorHandler, productsController)
}
