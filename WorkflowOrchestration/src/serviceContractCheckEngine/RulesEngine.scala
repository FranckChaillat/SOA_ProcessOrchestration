package serviceContractCheckEngine

import scala.collection.GenSeq
import java.util.Date
import java.text.SimpleDateFormat

/**
 * @author fra
 */
object RulesEngine {
  
  def isSup[T](a:T, b:T): Boolean = a match {
    case a:String  => a.length > b.asInstanceOf[String].length
    case c:Date => c.after(b.asInstanceOf[Date])
  }
  
  def isLenSup[A](a:A, len: Int):Boolean = a match {
      case b:String => b.length>len
      case c : Iterable[Any] => c.size >len
  }
 
}