package controllers

import com.google.inject.Guice
import de.htwg.se.wizard.WizardModule
import de.htwg.se.wizard.aview.TUI
import de.htwg.se.wizard.aview.gui.SwingGUI
import de.htwg.se.wizard.control.controllerBaseImpl.{Controller, game_over, game_started}
import de.htwg.se.wizard.model.FileIO.JSON.Impl_JSON
import de.htwg.se.wizard.model.cardsComponent.Card
import de.htwg.se.wizard.model.cardsComponent.Cards
import de.htwg.se.wizard.model.gamestateComponent.GamestateBaseImpl.Gamestate
import de.htwg.wapp.wizard.Wizard
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

@Singleton
class WizardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  val controller = Controller(Gamestate(), Impl_JSON())
  val tui = new TUI(controller)
  val gui = new SwingGUI(controller)
  val thread = new Thread {
    override def run {
      controller.publish(new game_started)
      do {
        val input = scala.io.StdIn.readLine()
        tui.processInput(input)
      } while (!tui.state.isInstanceOf[game_over])
    }
  }


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def wizard() = Action { implicit request: Request[AnyContent] =>
    if (!thread.isAlive) {
      thread.start()
    } else {
      System.out.println("Laeuft schon")
    }
    Redirect("/playerCount")
  }
  def setNameView()=Action{
      Ok(views.html.enternameview(controller.active_player_idx()))
  }
  def setName(name: String) = Action {
        controller.create_player(name)
        // hier wenn letzter Spieler weiterleiten an generate Hands
    Ok(views.html.index())
  }

  def setPlayerCount(count: Int) = Action { implicit request: Request[AnyContent] =>
    controller.set_player_amount(Option(count))
    Ok(views.html.playerCount())
  }
  def getPlayerCountView()  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.playerCount())
  }

  def setTrump(color: String) = Action { implicit request: Request[AnyContent] =>
    controller.wish_trump(color)
    Redirect("/trump")
  }
  def getTrump() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trump(controller.get_player(controller.active_player_idx())))
  }

  def setTrickAmount(amount: Int) = Action { implicit request: Request[AnyContent] =>
    controller.set_guess(amount)
    Redirect("/setTrickAmount")
  }
  def getTrickAmount() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trickAmount(controller.get_player(controller.active_player_idx()), controller.getGamestate().getTrump_card))
  }

  def playCard(idx: Int) = Action { implicit request: Request[AnyContent] =>
    controller.play_card(controller.get_player(controller.active_player_idx()).hand(idx))
    //if last player redirect to tick over
    Redirect("/playCardView")

  }
  def playCardView() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.showCards(controller.get_player(controller.active_player_idx())))
  }

}