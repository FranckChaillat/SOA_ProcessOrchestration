package serviceConsumer.serviceInventory

import java.sql.DriverManager
import java.sql.Connection

/**
 * @author fra
 */
object DataAccessor {
  private val url = "jdbc:mysql://localhost:3306/serviceInventory"
  private val user = "root"
  private val pwd = "root"
  var conn = loadConn()
  
  
  
  def loadConn() : Connection = {
    
    try {
       Class.forName("com.mysql.jdbc.Driver")
       DriverManager.getConnection(url, user, pwd)
       
    } catch {
      case t: Throwable => {println("unable to loadDriver")
                              t.printStackTrace()} // TODO: handle error
                              throw t
       }
    
  }

  
   def closeConnection(){
    if(!conn.isClosed())
      conn.close()
   }
    
  
}