package queueManager

/**
 * @author fra
 */
class Message(rk:String, ind: String){
  
  val routingKey: String = rk
  val indata : String = ind
  var outdata : String = ""
  
  def setOutData(o:String)= outdata = o
}