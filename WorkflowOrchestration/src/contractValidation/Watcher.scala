package contractValidation

import java.util.TimerTask
import serviceConsumer.serviceInventory.InventoryManager


/**
 * @author fra
 */
object Watcher {
    new Thread(new Runnable{def run= discover}).start
  
   def discover(){
     new java.util.Timer().scheduleAtFixedRate(new TimerTask{ 
       
       def run(){
         val res = InventoryManager.getSilos()
         System.out.print("check inventory...")
        // new ServiceProvider().execute(res, Map("header"->"application.wadl"))
       }}, 0, 1000*60 *5)
   }
}