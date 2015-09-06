package serviceContractCheckEngine

import java.util.TimerTask
import serviceConsumer.serviceInventory.InventoryManager
import serviceConsumer.ServiceProvider
import scala.collection.mutable.ListBuffer
import processOrchestration.Silos
import scala.xml.XML
import scala.xml.{NodeSeq, Node}
import scala.annotation.tailrec
import serviceConsumer.serviceInventory.DBAccessor


/**
 * @author fra
 */
object Watcher {
    new Thread(new Runnable{def run= discover}).start
  
   def discover(){
     new java.util.Timer().scheduleAtFixedRate(new TimerTask{ 
       
       def run(){
         
         def watch(silos : Seq[Silos])   = {
            if (silos.length>0){
              val props = InventoryManager.getDiscoveryProps(silos(0).name)
              val res = new ServiceProvider().execute(Map("host" -> props("host"),
                                                "silosUri"-> props("baseUri"),
                                                "uri" -> props("uri"),
                                                "httpMethod" -> "GET",
                                                "dataType"-> "application/xml"), Map())
             
              checkUpdates(res, silos(0))
              
            }
         }
         
         System.out.print("check inventory...")
         val res = InventoryManager.getSilos
         watch(res)
         
       }}, 0, 1000*60 *5)
   }
    
    
   private def checkUpdates(wadlContent: String, silos: Silos){
      
     
      val resources= XML.loadString(wadlContent).\\("resources")(0)
      val db=InventoryManager.getService().map((f)=>{f("uri")})
      val wadl = resources.child.map { x => x.\("@path") text }
      
      val missServ = wadl.filterNot { x => db.contains(x) }.filterNot { x => x.isEmpty }
      var m=  missServ.map { x => Map("uri" -> x,
                                 "serviceName" -> x.substring(1),
                                 "host" -> resources.\("@base").text.split("/")(2) .split(":")(0),
                                 "silosId" -> silos.id.toInt)}
      m.foreach((x)=>InventoryManager.insertService(x))

      
    //  xml.\\("resources")(0).child.foreach { x => checkRessources(x) }
      
      @tailrec
      def checkResources(s: Seq[Node], silosId: Int , path: String ,i : Int =0) {
        if(i>=s.length || s.isEmpty) Unit
        if(s(i).label.equals("method") && !InventoryManager.capabilityExists(s(i).\("@id") text, path, silosId)){
          
        }
        else checkResources(s, silosId, "",i)
        
      }
   
      resources.child.foreach { x => checkResources(x.child, silos.id.toInt, x.\("@path").text) }
      
      print()

      
    
    }
    
}