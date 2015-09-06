package queueManager

import serviceContractCheckEngine.Watcher

/**
 * @author fra
 */
object test {
  
    def main(args: Array[String]) {
      
    Watcher
    new MsgConsumer().rcv("xa")
  }
}