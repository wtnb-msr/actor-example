package example.actor

import akka.actor.{ActorSystem, Props, Actor}
import akka.routing.{FromConfig, RoundRobinPool}
import example.message.{Ping, Pong}

class PongActor extends Actor {

  def receive: Receive = {
    case Ping(s: String) => {
      println(s"[Recieve] $s")
      Thread.sleep(100)
      println(s"[Process] $s")
    }
  }

}

