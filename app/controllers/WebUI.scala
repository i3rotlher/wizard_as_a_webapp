import de.htwg.se.wizard.control._
import de.htwg.se.wizard.control.controllerBaseImpl._

import scala.swing.Reactor
import scala.swing.event.Event
import scala.util.{Failure, Success, Try}

class WebUI(controller: ControllerInteface) extends Reactor {

  listenTo(controller)
  var state: Event = new game_started

  reactions+= {
    case event: game_started => game_start()
    case event: get_Amount => state = event
    case event: start_round => start_round()
    case event: Wizard_trump => wizard_trump()
    case event: set_Wizard_trump => state = event
    case event: player_create =>
      println("Player " + (controller.active_player_idx()+1) + " whats your name ? / press r to undo previous name / press y to redo change")
      state = event
    case event: name_ok => state = event
    case event: next_guess =>
      guess()
      state = event
    case event: next_player_card =>
      play_card()
      state = event
    case event: card_not_playable =>
      println("This card is not playable right now!\n Choose a different number!")
    case event: mini_over =>
      println("Trick won by " + controller.get_mini_winner().getName + "!")
    case event: round_over =>
      println(controller.getGamestate().getGame_table)
    case event: game_over =>
      println("Game Over!")
      println(controller.getGamestate().calc_total())
      state = event
    case _ => println("tui event unimplemented!")
  }

  def processInput(input: String) : Unit = {
    state match {
      case event: get_Amount => check_Amount(input)
      case event: player_create => create_player(input)
      case event: set_Wizard_trump => check_trump_wish(input)
      case event: next_guess => get_guess(input)
      case event: next_player_card => get_card(input)
      case _ => println("state unimplemented: " + state)
    }
  }

  def game_start(): Unit = {
    println("Welcome to Wizard!")
    println("How many players want to play ? [3,4,5 or 6]")
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
    input match {
      case "y" => controller.redo_player()
      case "r" => controller.undo_player()
      case _ => controller.create_player(input)
    }
  }

  def start_round() : Unit = {
    println("- - - - - Round " + (controller.getGamestate().getRound_number+1) + " started - - - - -")
    println("Generating hands . . .")
    println("Generating trumpcard . . .\n\n\n")
  }

  def wizard_trump(): Unit = {
    println("A wizard has been drawn as the trump card!")
    val player = controller.get_player((controller.active_player_idx()-1+controller.player_amount())%controller.player_amount())
    println(player.getName + " which color do you want to be trump? [red,blue,yellow,green]")
    println("Your cards: " + player.showHand())
  }

  def check_trump_wish(input: String): Boolean = {
    if(!List("red","green","blue","yellow").contains(input)) {
      println("You may only choose one of these colors red,blue,yellow,green")
      return false
    }
    controller.wish_trump(input)
    true
  }

  def guess(): Unit = {
    val active_player = controller.get_player(controller.active_player_idx())
    println("Trump card: " + controller.getGamestate().getTrump_card)
    println(active_player.getName + " how many tricks are you going to make?")
    println(active_player.showHand()+"\n\n\n\n\n")
  }

  def toInt(s: String): Try[Int] = Try(Integer.parseInt(s.trim))

  def get_guess(input: String):Boolean = {
    val integer = toInt(input)
    integer match {
      case Failure(_) => println("Insert a correct number!");false
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
    val active_player = controller.get_player(controller.active_player_idx())
    println("Trump card: " + controller.getGamestate().getTrump_card)
    println(active_player.getName + " which card do you want to play ?")
    println("Your cards: " + active_player.showHand()+"\n\n\n\n\n")
  }

  def get_card(input: String): Unit = {
    val active_player = controller.get_player(controller.active_player_idx())
    var list = List[String]()
    for (x <- 1 to active_player.getHand.size) {
      list = list :+ x.toString
    }
    if (!list.contains(input)) {
      println("Insert a valid card number!")
    } else {
      controller.play_card(active_player.getHand(input.toInt-1))
    }
  }
}