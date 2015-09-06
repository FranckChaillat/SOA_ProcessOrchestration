package processOrchestration

/**
 * @author fra
 */
class Constraint(f: String, func : String, v: String) {
  private val _field = f
  private val _function = func
  private val _value = v
  private var _failed = false
  
  def isFailed(b: Boolean) = _failed = b
  def field = _field
  def function = _function
  def value = _value
  
  
}