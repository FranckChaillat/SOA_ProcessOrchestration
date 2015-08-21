package serviceConsumer.serviceInventory

import scala.collection.immutable.HashMap
import workflowOrchestration.exceptions.InvalidServiceException
import java.util.Currency
import java.sql.ResultSet
import java.sql.CallableStatement

/**
 * @author fra
 */
object InventoryManager {
  


  def getSilos() : Map [String, String] = {
    
       if(DataAccessor.conn.isClosed)
         DataAccessor.loadConn
         
         val stm = DataAccessor.conn.prepareCall("call getSilos()")
         val rs = stm.executeQuery
         
         var p : Map[String, String]= Map()
         
         rs.next match{
         case true => {p.+("id"-> rs.getInt(1).toString)
                       p.+("name"-> rs.getString(2))
                       p.+("baseUri"-> rs.getString(3))}
         
         case false => throw new InvalidServiceException("le service ne semble pas exister dans l'inventaire de service")
       }
          p
  }
  
  def getService(name: String) : Map [String, String] = {
    
      def exec(params : Seq[String]): ResultSet = {
      
        var r = "call getCapability"
        params.length match { case 1 => r=r.+("(?)") case 2 => r=r.+("WithService(?,?)") case 3 => r=r.+("WithServiceAndSilos(?,?,?)") case _ => }
        val callStm = DataAccessor.conn.prepareCall(r) 
        
        for((j,i)<-params.view.zipWithIndex)  callStm.setString(i+1, j.asInstanceOf[String])
         callStm.executeQuery
    }
    
    if(DataAccessor.conn.isClosed()) 
      DataAccessor.loadConn()
      
    var p : HashMap[String, String] = HashMap()
   val rs= exec(split('/', name))
    
    rs.next() match {
      case true => { List("serviceName","host","uri", "httpMethod", "dataType", "silosUri").foreach {k=> p+= k -> rs.getString(k)}}
      case false => { throw new InvalidServiceException("le service "+name+" ne semble pas exister dans l'inventaire de service")}
    }
    p
  }
  
   private def split(sep:Char, source :String):Array[String] = {
     if(source.contains(sep)) 
       return source.split(sep.toString)
     else 
       return Array(source)
  }
  
}