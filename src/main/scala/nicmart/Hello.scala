package nicmart

object Hello {
  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("Usage: val1 weight 1 val2 weight2 val3 weight3 ...")
      println("Example (biased coin): head 1 tail 2")
      return
    }

    val (valuesIndexes, weightsIndexes) = args.indices.partition(_ % 2 == 0)
    val values = valuesIndexes.map(args(_))
    val weights = weightsIndexes.map(args(_).toDouble)

    val weightedValues = values.zip(weights).map(pair => WeightedValue(pair._1, pair._2))

    val distribution = new WeightedRandomDistribution[String](weightedValues)

    println("Generating 10 values...")
    (1 to 10).map(x => println(s"$x) ${distribution()}"))
  }
}
