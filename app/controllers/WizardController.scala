package controllers

import akka.actor._
import akka.stream.Materializer
import de.htwg.se.wizard.aview.{TUI, WebUI}
import de.htwg.se.wizard.control.controllerBaseImpl._
import de.htwg.se.wizard.model.FileIO.JSON.Impl_JSON
import de.htwg.se.wizard.model.cardsComponent.Card
import de.htwg.se.wizard.model.gamestateComponent.GamestateBaseImpl.Gamestate
import de.htwg.se.wizard.model.playerComponent.PlayerBaseImpl.Player
import play.api.libs.json._
import play.api.libs.streams.ActorFlow

import javax.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class WizardController @Inject()(val controllerComponents: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends BaseController {

  var controller: Controller = null
  // val tui = new TUI(controller)
  // val gui = new SwingGUI(controller)
  var wui: WebUI = null

  // JSON --------------------------------------------------------------------------------------------
  implicit val cardWrite = new Writes[Card] {
    override def writes(c: Card): JsValue = Json.obj(
      "url" -> getCardPath(c),
      "num" -> c.num,
      "colour" -> c.colour,
    )
  };

  implicit val cardList = new Writes[List[Card]] {
    override def writes(p: List[Card]): JsValue = Json.obj(
      "cards" -> p,
    )
  };

  implicit val playerWrite = new Writes[Player] {
    override def writes(p: Player): JsValue = Json.obj(
      "name" -> p.name,
      "hand" -> p.hand,
    )
  };

  def getCardPath(card: Card): String = {
    var result = "/assets//images/card-images/";
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
  // JSON --------------------------------------------------------------------------------------------

  def index() = Action { implicit request: Request[AnyContent] =>
    if (controller == null) {
      controller =  Controller(Gamestate(), Impl_JSON())
      wui = new WebUI(controller)
      Ok(views.html.index())
    } else {
      NotAcceptable("Game already running!")
    }

  }

  def wizard() = Action { implicit request: Request[AnyContent] =>
    if(wui.getState() == null) {
      controller.publish(new game_started)
    }
    Redirect("/playerCount")
  }
  def setNameView()=Action{
      Ok(views.html.playerName(connected_players + 1))
  }
//  def setName(name: String) = Action {
//    controller.create_player(name)
//    if (!wui.getState().isInstanceOf[player_create]) {
//      if(wui.getState().isInstanceOf[set_Wizard_trump]) {
//        Redirect("/trump")
//      } else {
//        Redirect("/setTrickAmount")
//      }
//    } else {
//      Ok(views.html.index())
//    }
//  }

  def setPlayerCount(count: Int) = Action { implicit request: Request[AnyContent] =>
    controller.set_player_amount(Option(count))
    Ok(views.html.playerName(controller.active_player_idx()+1))
  }
  def getPlayerCountView()  = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.playerCount())
  }

//  def setTrump(color: String) = Action { implicit request: Request[AnyContent] =>
//    controller.wish_trump(color)
//    controller.setGamestate(controller.getGamestate().set_active_player_idx((controller.active_player_idx()+1)%controller.player_amount()))
//    Redirect("/setTrickAmount")
//  }
  def getTrump() = Action { implicit request: Request[AnyContent] =>
    val player = controller.get_player((controller.active_player_idx()-1+controller.player_amount())%controller.player_amount())
    Ok(views.html.trump(player, GetCardPath))
  }
//
//  def setTrickAmount(amount: Int) = Action { implicit request: Request[AnyContent] =>
//    controller.set_guess(amount)
//    if(!wui.getState().isInstanceOf[next_guess]) {
//      Ok(Json.obj(("redirect", "/playCard")))
//    } else {
//      Ok(Json.toJson(controller.get_player(controller.active_player_idx())))
//    }
//  }
  def getTrickAmount() = Action { implicit request: Request[AnyContent] =>
    var list: List[String] = List()
    controller.get_player(controller.active_player_idx()).hand.foreach(card => {list = list.appended(GetCardPath.getTotalPath(card))})

    var res = "{cards: ["
    list.foreach(url => {res += "\"" + url + "\","})
    res = res.substring(0, res.length-1)
    res += "]}"

    Ok(views.html.trickAmount(controller.get_player(controller.active_player_idx()), controller.getGamestate().getTrump_card, GetCardPath, controller.getGamestate().getPlayers, controller.getGamestate().getGame_table, res))
  }
//
//  def playCard(idx: Int) = Action { implicit request: Request[AnyContent] =>
//
//    val round = controller.getGamestate().getRound_number
//    val playedCard = controller.get_player(controller.active_player_idx()).hand(idx)
//    controller.play_card(controller.get_player(controller.active_player_idx()).hand(idx))
//    if (!wui.getState().isInstanceOf[next_player_card]) {
//      val next_round = controller.getGamestate().getRound_number
//      if (round != next_round) {
//        Ok(Json.obj(("redirect", "/trickOver")))
//      } else if (wui.getState().isInstanceOf[card_not_playable]) {
//        NotAcceptable("CardNotPlayable")
//      } else {
//        Ok(Json.obj(("redirect", "/trickOver")))
//      }
//    } else {
//      if (controller.game.getPlayedCards.isEmpty) {
//        Ok(Json.obj(("redirect", "/trickOver")))
//        // Ok(Json.obj(("player", controller.get_player(controller.active_player_idx())), ("playedCard", "RESET")))
//      } else {
//        Ok(Json.obj(("player", controller.get_player(controller.active_player_idx())), ("playedCard", playedCard)))
//      }
//    }
//
//  }
  def playCardView() = Action { implicit request: Request[AnyContent] =>
    var list: List[String] = List()
    controller.get_player(controller.active_player_idx()).hand.foreach(card => {
      list = list.appended(GetCardPath.getTotalPath(card))
    })

    var res = "{cards: ["
    list.foreach(url => {
      res += "\"" + url + "\","
    })
    res = res.substring(0, res.length - 1)
    res += "]}"


    var playedList: List[String] = List()
    controller.getGamestate().getPlayedCards.foreach(card => {
      playedList = playedList.appended(GetCardPath.getTotalPath(card))
    })

    var playedRes = "{cards: ["
    playedList.foreach(url => {
      playedRes += "\"" + url + "\","
    })
    playedRes = playedRes.substring(0, playedRes.length - 1)
    playedRes += "]}"
    if (playedList.length == 0) playedRes = "{cards: []}"

    Ok(views.html.showCards(controller.get_player(controller.active_player_idx()), GetCardPath, controller.getGamestate().getTrump_card, playedRes, controller.getGamestate().getGame_table, controller.getGamestate().getRound_number,
      controller.getGamestate().getPlayers, res))
  }

  def howTo() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.howToPlay())
  }

  def trickOver() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.trickOver(controller.get_mini_winner(), !wui.getState().isInstanceOf[next_player_card]))
  }

  def roundOver() = Action { implicit request: Request[AnyContent] =>
    val trump = if (wui.getState().isInstanceOf[set_Wizard_trump]) true else false
    Ok(views.html.roundOver(controller.getGamestate().getGame_table, controller.getGamestate().getRound_number,
      controller.getGamestate().getPlayers,trump))
  }

  def getHi() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.hi("Test"))
  }

  var connected_players = 0
  def socket: Handler = {
    if(connected_players < controller.player_amount()) {
      connected_players = connected_players + 1
      println("Player: " + (connected_players - 1) + " connected!")
      WebSocket.accept[String, String] { request =>
        ActorFlow.actorRef { out =>
          WizardWebSocketActorFactory.create(out, controller, connected_players - 1)
        }
      }
    } else {
      Action { implicit request: Request[AnyContent] =>
        NotAcceptable("Too many players")
      }
    }
  }

  def reset = Action { implicit request: Request[AnyContent] => {
      controller = null
      connected_players = 0
      wui = null
      println("\n\n\n\n___________ Game Reset! ___________")
      Accepted("Game reset")
    }
  }

}

object WizardWebSocketActorFactory {
  def create(out: ActorRef, controller: Controller, playerIdx: Int): Props = {
    Props(new WizardWebSocketActor(out, controller, playerIdx))
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

  def getTotalPath(card: Card): String = {
    var result = "http://localhost:9000/assets//images/card-images/";
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

//case object wizardGame {
//  var controller: Controller = null
//  def getController() = controller
//  def startNewGame() = {
//    controller = Controller(Gamestate(), Impl_JSON())
//    controller
//  }
//  def reset() = {
//    controller = null
//    controller
//  }
//
//}