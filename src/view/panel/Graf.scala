package view.panel

import java.util.Random;
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import scala.collection.mutable.Queue

object Graf {

  def apply(n : Int, p : Double) = new Graf(n, p)
  
}

class Graf private (n : Int, p : Double) {
    val r = new Random()
  
    val g = Map[Int, Set[Int]]()
  
    for (i <- 0 to n - 1) {
      g(i) = Set[Int]()
      
      for (j <- 0 to i - 1) {
        if (r.nextDouble <= p) {
          g(i) += j
          g(j) += i
           println("graf " + g(i) + g(j))
        }
      }
    }
   
    def size = n
    
    def hasEdge(i : Int, j : Int) = g(i).contains(j)
}