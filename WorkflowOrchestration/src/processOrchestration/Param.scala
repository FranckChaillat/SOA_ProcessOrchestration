package processOrchestration

import scala.collection.mutable.ListBuffer

/**
 * @author fra
 */
class Param(n: String, mapType: String, c: String, o:Int, constr : Seq[Constraint]) {
  private val _name = n
  private val _mappingType = mapType
  private val _capability = c
  private val _order = o
  var value: Any =None
  private val _constraints = constr.to[ListBuffer]
  
  def name = _name
  def mappingType = _mappingType
  def capability = _capability
  def order = _order
  def constraint = _constraints
}
