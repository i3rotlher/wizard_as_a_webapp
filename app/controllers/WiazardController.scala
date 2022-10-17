package controllers

import de.htwg.se.wizard.WizardModule
import de.htwg.wapp.wizard.Wizard
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

@Singleton
class WiazardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  Wizard.main(null);
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def wizard() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def setName(name: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def setPlayerCount(count: Int) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def setTrump(color: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def setTrickAmount(amount: Int) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def playCard(idx: Int) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
}