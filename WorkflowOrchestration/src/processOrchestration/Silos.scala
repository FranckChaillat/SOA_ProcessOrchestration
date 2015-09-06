package processOrchestration

/**
 * @author fra
 */
class Silos(n: String, u : String, identifier :String) {
  
  private val _id = identifier
  private val _name = n
  private val _uri = u
  
  def name = _name
  def uri = _uri
  def id = _id
  
}