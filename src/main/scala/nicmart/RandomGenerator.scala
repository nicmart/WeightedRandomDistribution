/*
 * (c) 2014 Nicolò Martini
 *
 * http://nicolo.martini.io
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package nicmart

import scala.util.Random

/**
 * Generate integers and doubles
 */
trait RandomGenerator {

  /**
   * Returns an integer between 0 and upTo -1
   */
  def nextInt(upTo: Int): Int

  /**
   * Returns a double between 0 and 1
   */
  def nextDouble: Double
}

object RandomGenerator {
  implicit val defaultGenerator: RandomGenerator = DefaultRandomGenerator
}

/**
 * Default implementation using scala.util.Random
 */
object DefaultRandomGenerator extends RandomGenerator {

  override def nextInt(upTo: Int): Int = Random.nextInt(upTo)

  override def nextDouble: Double = Random.nextDouble()
}
