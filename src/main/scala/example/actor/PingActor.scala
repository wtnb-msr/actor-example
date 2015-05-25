package example.actor

import akka.actor.{ActorSystem, Props, Actor}
import akka.routing.{FromConfig, RoundRobinPool}
import example.message.{Ping, Pong}

class PingActor extends Actor {

  val pongActor = global.pongActor

  // String から Ping を生成し PongActor に送る
  def receive = {
    case s: String => pongActor ! Ping(s)
  }

}

