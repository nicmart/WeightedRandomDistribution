import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class HelloSpec extends FlatSpec with Matchers {
  "Hello" should "have tests" in {
    true should be === true
  }
}
