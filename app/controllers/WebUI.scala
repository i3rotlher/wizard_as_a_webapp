package de.htwg.se.wizard.aview
import de.htwg.se.wizard.control._
import de.htwg.se.wizard.control.controllerBaseImpl._

import scala.swing.Reactor
import scala.swing.event.Event
import scala.util.{Failure, Success, Try}

class WebUI(controller: ControllerInteface) extends Reactor {

  listenTo(controller)
  var state: Event = null

  // evtl. queue

  reactions+= {
    case event: game_started => state = event
    case event: get_Amount => state = event
    case event: start_round => state = event
    case event: Wizard_trump => state = event
    case event: set_Wizard_trump => state = event
    case event: player_create => state = event
    case event: name_ok => state = event
    case event: next_guess => state = event
    case event: next_player_card => state = event
    case event: card_not_playable => state = event
    case event: mini_over => state = event
    case event: round_over => state = event
    case event: game_over => state = event
    case _ => println("tui event unimplemented!")
  }

  def getState(): Event = state
}