package controllers

import akka.actor.{Actor, ActorRef}
import de.htwg.se.wizard.control.controllerBaseImpl.{player_create, _}
import play.api.libs.json._

import scala.swing.Reactor
import scala.swing.event.Event
import scala.util.{Failure, Success, Try}

class WizardWebSocketActor(out: ActorRef, controller: Controller, playerIdx: Int) extends Actor with Reactor {

  listenTo(controller)
  var state: Event = new player_create

  reactions += {
    case event: game_started => game_start()
    case event: get_Amount => state = event
    case event: start_round => start_round()
    case event: Wizard_trump => wizard_trump()
    case event: set_Wizard_trump => state = event
    case event: player_create =>
      showView("playerName")
      state = event
    case event: name_ok => state = event
    case event: next_guess =>
      guess()
      state = event
    case event: next_player_card =>
      play_card()
      state = event
    case event: card_not_playable =>
      out ! Json.obj("event" -> "card_not_playable").toString()
    case event: mini_over =>
      showView("trickOver")
    case event: round_over =>
      println(controller.getGamestate().getGame_table)
    case event: game_over =>
      println("Game Over!")
      println(controller.getGamestate().calc_total())
      state = event
    case _ => println("tui event unimplemented!")
  }

  def processInput(input: String): Unit = {
    println("State: " + state)
    state match {
      case null => game_start()
      case event: get_Amount => check_Amount(input)
      case event: player_create => create_player(input)
      case event: set_Wizard_trump => check_trump_wish(input)
      case event: next_guess => get_guess(input)
      case event: next_player_card => get_card(input)
      case _ => println("state unimplemented: " + state)
    }
  }

  def game_start(): Unit = {
    controller.publish(new get_Amount)
  }

  def check_Amount(input: String): Int = {
    if (!List("3", "4", "5", "6").contains(input)) {
      println("There may only be 3,4,5 or 6 players!")
      return -1
    }
    controller.set_player_amount(Some(input.toInt))
    input.toInt
  }

  def create_player(input: String): Unit = {
    controller.create_player(input)
  }

  def start_round(): Unit = {
  }

  def wizard_trump(): Unit = {
    showView("trump")
  }

  def check_trump_wish(input: String): Boolean = {
    if (!List("red", "green", "blue", "yellow").contains(input)) {
      println("You may only choose one of these colors red,blue,yellow,green")
      return false
    }
    controller.setGamestate(controller.getGamestate().set_active_player_idx((controller.active_player_idx()+1)%controller.player_amount()))
    controller.wish_trump(input)
    true
  }

  def guess(): Unit = {
    showView("setTrickAmount")
  }

  def toInt(s: String): Try[Int] = Try(Integer.parseInt(s.trim))

  def get_guess(input: String): Boolean = {
    val integer = toInt(input)
    integer match {
      case Failure(_) => println("Insert a correct number!"); false
      case Success(_) =>
        if (integer.get < 0) {
          println("Insert a correct number (>= 0)!")
          false
        } else {
          controller.set_guess(integer.get)
          true
        }
    }
  }

  def play_card(): Unit = {
    showView("playCard")
  }

  def get_card(input: String): Unit = {
    val active_player = controller.get_player(controller.active_player_idx())
    controller.play_card(active_player.getHand(input.toInt))
  }

  def showView(viewName: String): Unit = {
    val url = "http://localhost:9000/" + viewName
    if (playerIdx == controller.active_player_idx()) {
      out ! Json.obj("fetch" -> url).toString()
    } else {
      out ! Json.obj("event" -> "NotYourTurn", "activePlayer" -> controller.get_player(controller.active_player_idx()).name).toString()
    }
  }


  def receive = {
    case msg: String =>
      println(controller)
      if (msg == "whoAmI") {
        out ! Json.obj("whoAmI" -> playerIdx).toString()
      } else {
        println("Player: " + playerIdx + " send: " + msg)
        println("Active Player: " + controller.active_player_idx())
        if (playerIdx == controller.active_player_idx()) {
          if (msg != "MyTurn?") processInput(msg)
        }
        else out ! Json.obj("event" -> "NotYourTurn", "activePlayer" -> controller.get_player(controller.active_player_idx()).name).toString()
      }
  }
}