package nicmart

import scala.collection.mutable
import scala.util.Random

/**
 * This class implements the Alias method for weighted distributions.
 * You can get a great insight into the topic in http://www.keithschwarz.com/darts-dice-coins/
 */
class WeightedRandomDistribution[T]
    (val weightedValues: Iterable[WeightedValue[T]])
    (implicit val random: RandomGenerator)
    extends (() => T) {

  type IndexedWeightedVal = (Int, WeightedValue[T])

  // First cycle
  private val weightsSum = weightedValues.foldLeft(0.0)(_ + _.weight)

  // Second cycle @todo remove as much cycles as possible
  private val values: IndexedSeq[T] = weightedValues.map(_.value)(collection.breakOut)
  private val total = values.length

  private val probabilities: mutable.IndexedSeq[Double] = mutable.IndexedSeq.fill(total)(0.0)
  private val aliases: mutable.IndexedSeq[Int] = mutable.IndexedSeq.fill(total)(-1)

  implicit val defaultRandom: scala.util.Random = Random

  // Here third cycle, but inside it we need weightsSum
  initialize

  /**
   * Returns an element chosen accordingly to the distribution
   * @return T
   */
  override def apply(): T = {
    val index = random.nextInt(probabilities.length)
    val prob = probabilities(index)
    if (random.nextDouble < prob) values(index) else values(aliases(index))
  }

  /**
   * Builds the underlying data structure
   */
  private def initialize = {
    val smallStack = new mutable.Stack[IndexedWeightedVal]
    val largeStack = new mutable.Stack[IndexedWeightedVal]

    for ((weightedValue, i) <- weightedValues.view.zipWithIndex) {
      getStack(weightedValue.weight).push((i, weightedValue))
    }

    while (smallStack.nonEmpty && largeStack.nonEmpty) {
      val (smallIndex, smallWv) = smallStack.pop()
      val (largeIndex, largeWv) = largeStack.pop()

      probabilities(smallIndex) = normalizedWeight(smallWv.weight)
      aliases(smallIndex) = largeIndex

      val newWeightedValue = WeightedValue(largeWv.value, largeWv.weight - (1 - smallWv.weight))

      getStack(newWeightedValue.weight).push((largeIndex, newWeightedValue))
    }

    while (largeStack.nonEmpty) {
      val (largeIndex, largeWv) = largeStack.pop()
      probabilities(largeIndex) = normalizedWeight(largeWv.weight)
    }

    while (largeStack.nonEmpty) {
      val (smallIndex, smallWv) = smallStack.pop()
      probabilities(smallIndex) = normalizedWeight(smallWv.weight)
    }

    def getStack(weight: Double) = if (weight * total < weightsSum) smallStack else largeStack
  }

  private def normalizedWeight(weight: Double) = weight * total / weightsSum
}