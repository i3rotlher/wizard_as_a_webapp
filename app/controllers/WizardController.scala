package controllers

import de.htwg.se.wizard.aview.gui.SwingGUI
import de.htwg.se.wizard.aview.{TUI, WebUI}
import de.htwg.se.wizard.control.controllerBaseImpl._
import de.htwg.se.wizard.model.FileIO.JSON.Impl_JSON
import de.htwg.se.wizard.model.cardsComponent.Card
import de.htwg.se.wizard.model.gamestateComponent.GamestateBaseImpl.Gamestate
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

@Singleton
class WizardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val controller = Controller(Gamestate(), Impl_JSON())
  val tui = new TUI(controller)
  // val gui = new SwingGUI(controller)
  val wui = new WebUI(controller)

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def wizard() = Action { implicit request: Request[AnyContent] =>
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
    if (!wui.getState().isInstanceOf[player_create]) {
      if(wui.getState().isInstanceOf[set_Wizard_trump]) {
        Redirect("/trump")
      } else {
        Redirect("/setTrickAmount")
      }
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
    controller.setGamestate(controller.getGamestate().set_active_player_idx(controller.active_player_idx()+1))
    controller.wish_trump(color)
    Redirect("/setTrickAmount")
  }
  def getTrump() = Action { implicit request: Request[AnyContent] =>
    val player = controller.get_player((controller.active_player_idx()-1+controller.player_amount())%controller.player_amount())
    Ok(views.html.trump(player))
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
    Ok(views.html.trickAmount(controller.get_player(controller.active_player_idx()), controller.getGamestate().getTrump_card, GetCardPath))
  }

  def playCard(idx: Int) = Action { implicit request: Request[AnyContent] =>
    val round = controller.getGamestate().getRound_number
    controller.play_card(controller.get_player(controller.active_player_idx()).hand(idx))
    if (!wui.getState().isInstanceOf[next_player_card]) {
      val next_round = controller.getGamestate().getRound_number
      if (round != next_round) {
        Redirect("/roundOver")
      } else if (wui.getState().isInstanceOf[card_not_playable]) {
        Redirect("/playCard")
      } else {
        Redirect("/trickOver")
      }
    } else {
      Redirect("/playCard")
    }

  }
  def playCardView() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.showCards(controller.get_player(controller.active_player_idx()), GetCardPath))
  }

  def howTo() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.howToPlay())
  }

  def trickOver() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trickOver(controller.get_mini_winner()))
  }

  def roundOver() = Action { implicit request: Request[AnyContent] =>
    val trump = if (wui.getState().isInstanceOf[set_Wizard_trump]) true else false
    Ok(views.html.roundOver(controller.getGamestate().getGame_table, controller.getGamestate().getRound_number,
      controller.getGamestate().getPlayers,trump))
  }

  def getHi() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.hi("Test"))
  }
}

object GetCardPath {
  def getCardPath(card: Card): String = {
    var result = "/images/card-images/";
    if (card.num == 0) {
      result += card.colour.substring(card.colour.indexOf('(') + 1, card.colour.indexOf(')'))
      return result + "-fool.png"
    }
    if (card.num == 14) {
      result += card.colour.substring(card.colour.indexOf('(') + 1, card.colour.indexOf(')'))
      return result + "-wizard.png"
    }
    result += card.colour
    result + card.num + ".png"
  }
}