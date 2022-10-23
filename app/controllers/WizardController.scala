package controllers

import com.google.inject.Guice
import de.htwg.se.wizard.WizardModule
import de.htwg.se.wizard.aview.{TUI, WebUI}
import de.htwg.se.wizard.aview.gui.SwingGUI
import de.htwg.se.wizard.control.controllerBaseImpl.{Controller, game_over, game_started, get_Amount, next_guess, next_player_card, player_create, round_over, start_round}
import de.htwg.se.wizard.model.FileIO.JSON.Impl_JSON
import de.htwg.se.wizard.model.cardsComponent.Card
import de.htwg.se.wizard.model.cardsComponent.Cards
import de.htwg.se.wizard.model.gamestateComponent.GamestateBaseImpl.Gamestate
import de.htwg.wapp.wizard.Wizard
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}
import de.htwg.se.wizard.control._

import scala.swing.Reactor
import scala.swing.event.Event
import scala.util.{Failure, Success, Try}

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
  val wui = new WebUI(controller)

//  @Singleton
//  val thread = new Thread {
//    override def run: Unit = {
//      controller.publish(new game_started)
//      do {
//        val input = scala.io.StdIn.readLine()
//        tui.processInput(input)
//      } while (!tui.state.isInstanceOf[game_over])
//    }
//  }

  def index() = Action { implicit request: Request[AnyContent] =>
//    System.out.println(thread.getName)
    Ok(views.html.index())
  }
  def wizard() = Action { implicit request: Request[AnyContent] =>
//    if (!thread.isAlive) {
//      thread.start()
//    } else {
//      System.out.println("Laeuft schon")
//    }
    if(wui.getState() == null) {
      controller.publish(new game_started)
    }
    Redirect("/playerCount")
  }
  def setNameView()=Action{
      Ok(views.html.playerName(controller.active_player_idx()+1))
  }
  def setName(name: String) = Action {
    controller.create_player(name)
    System.out.println("AAAAAAAHA: " + wui.getState())
    if (!wui.getState().isInstanceOf[player_create]) {
      System.out.println("AHA ???")
      Redirect("/setTrickAmount")
    } else {
      Ok(views.html.index())
    }
  }

  def setPlayerCount(count: Int) = Action { implicit request: Request[AnyContent] =>
    controller.set_player_amount(Option(count))
    Redirect("/playerName")
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
    System.out.println(wui.getState())
    if(!wui.getState().isInstanceOf[next_guess]) {
      Redirect("/playCard")
    } else {
      Ok("/setTrickAmount")
    }
  }
  def getTrickAmount() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trickAmount(controller.get_player(controller.active_player_idx()), controller.getGamestate().getTrump_card))
  }

  def playCard(idx: Int) = Action { implicit request: Request[AnyContent] =>
    val round = controller.getGamestate().getRound_number
    controller.play_card(controller.get_player(controller.active_player_idx()).hand(idx))
    if (!wui.getState().isInstanceOf[next_player_card]) {
      val next_round = controller.getGamestate().getRound_number
      if (round != next_round) {
        Redirect("/roundOver")
      } else {
        Redirect("/trickOver")
      }
    } else {
      Redirect("/playCard")
    }

  }
  def playCardView() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.showCards(controller.get_player(controller.active_player_idx())))
  }

  def howTo() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.howToPlay())
  }

  def trickOver() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trickOver(controller.get_mini_winner()))
  }

  def roundOver() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.roundOver(controller.getGamestate().getGame_table, controller.getGamestate().getRound_number))
  }

}