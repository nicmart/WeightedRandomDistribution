WeightedRandomDistribution
==========================

A Scala implementation of the ALIAS method for Weighted Random Distributions.

A Weighted Random Distribution allows you to randomly select an element from a list of values
in which every value has an assigned weight.

The ALIAS method is an algorithm to achieve constant-time performance in element selection.

See http://www.keithschwarz.com/darts-dice-coins/ for a great insight of the topic.

# Example
Let's say you want to randomly select a color between red, green and blue, in such a way that red is selected
50% of the times, green the 30% and blue the 20%.

```scala
import nicmart._

val weightedValues = List(WeightedValue("red", 5), WeightedValue("green", 3), WeightedValue("blue", 2))
val distribution = new WeightedRandomDistribution(weightedValues)
```

You can then call distribution as a function of zero arguments:
```scala
val val1 = distribution()  // maybe red
val val2 = distribution()  // maybe green
```
