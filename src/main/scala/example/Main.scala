package example.actor

import akka.actor.{ActorSystem, Props, Actor}
import akka.routing.{FromConfig, RoundRobinPool}

object global {

  val system = ActorSystem("mySystem")

  val pingActor = system.actorOf(Props[PingActor], "pingActor")

  // 1 Actor
  //val pongActor = system.actorOf(Props[PongActor], "pongActor")

  // 1 Router => 8 Actor ( default dispatcher )
  //val pongActor = system.actorOf( Props[PongActor]
  //  .withRouter( RoundRobinPool(8) ), "pongActor" ) // 1 Router => 8 Actor

  // 1 Router => 8 Actor ( use configured dispatcher )
  val pongActor = system.actorOf(Props[PongActor]
    .withRouter( RoundRobinPool(8) ) // 1 Router => 8 Actor
    .withDispatcher("my-dispatcher"), "pongActor")

  // conf から全部設定するパターン
  //val pongActor = system.actorOf(FromConfig.props(Props[PongActor]), "pongActor")

}

object Main {

  def main(args: Array[String]) = {
    val runner = new Main
    runner.run()
  }

}

class Main {

  lazy val pingActor = global.pingActor

  def run() = {

    // PingActor から Ping を 100 件発生させる
    for ( i <- 0 to 100) {
      pingActor ! s"message $i"
    }

    // 処理待機
    Thread.sleep(1000)
    println("finish")

  }

}


object Util {

  def elapsed[T](f: => T): T = {
    println("[BEGIN]")
    val begin = System.currentTimeMillis()
    val result = f
    val total = System.currentTimeMillis() - begin
    println(s"[END] $total ms.")
    result
  }

}

