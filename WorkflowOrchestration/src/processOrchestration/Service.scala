package processOrchestration

import com.sun.webkit.dom.JSObject
import com.google.gson.JsonObject
import scala.collection.mutable.HashMap
import com.google.gson.JsonArray
import scala.annotation.tailrec
import serviceConsumer.serviceInventory.InventoryManager
import serviceConsumer.ServiceProvider


/**
 * @author fra
 */
class Service(s:JsonObject) {
  
  private val name:String = if(s.has("name")) s.get("name").getAsString else throw new IllegalArgumentException("the args 'name' is requiered in the service description")   
  val id:Int = if(s.has("serviceId"))  s.get("serviceId").getAsInt else  throw new IllegalArgumentException("the args 'serviceId' is requiered in the service description")   
  private val onFinish = if(s.has("onFinish"))  s.get("onFinish").getAsString else null
  private val onStart = if(s.has("onStart"))  s.get("onStart").getAsString else null
  private val order =  if(s.has("order"))  s.get("order").getAsInt else -1
  var args : HashMap[String, String] = HashMap()
  var output : String = ""
  if(s.has("args")) args = initArgs(0, s.get("args").getAsJsonArray, HashMap())
  

  
  @tailrec
  private def initArgs(i: Int, params: JsonArray, mappedParams : HashMap[String, String]): HashMap[String, String]={
    
    if(params.size()==i) return mappedParams
    mappedParams.+=( params.get(i).getAsJsonObject.get("key").getAsString -> params.get(i).getAsJsonObject().get("value").getAsString)
    initArgs(i.+(1), params, mappedParams)}
  
  
  
   def run(params: List[(String, String)]){
    
     
     val sp = new ServiceProvider() 
     sp.execute(InventoryManager.getService(name), params.map(f=> f._1 -> f._2).toMap)
     
     null
     
   }
  
  
  
  
}

