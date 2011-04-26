import akka._
import actor._
import Actor._

object TestActor {
  def main(args: Array[String]) {
    val actor = actorOf[TestActor]
    actor ! "test"
  }
}

class TestActor extends Actor {
  def receive = { 
    case _ => self.stop()
  }
}
