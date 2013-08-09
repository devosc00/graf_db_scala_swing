object graf {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  import view.panel.Graf

  //Graf.apply(4, 1).hasEdge(2, 1)

  def next(l: Int, r: scala.util.Random) =
    { for (i <- 0 to l) r.nextInt(2).asInstanceOf[AnyRef] }
                                                  //> next: (l: Int, r: scala.util.Random)Unit
  val data = "aaabbbcccddddddeeefffhgijq".toList  //> data  : List[Char] = List(a, a, a, b, b, b, c, c, c, d, d, d, d, d, d, e, e,
                                                  //|  e, f, f, f, h, g, i, j, q)
 //data span (_ == 'a')
  def cluster[T](xs: List[T]): List[List[T]] = xs match{
  case Nil => Nil
  case y::ys => val (first, rest) = xs span (_ == y)
  first :: cluster(rest)
  }                                               //> cluster: [T](xs: List[T])List[List[T]]
  
 def encode[T](xs: List[T]): List[(T, Int)] =
 cluster(xs) map (ys => (ys.head , ys.length))    //> encode: [T](xs: List[T])List[(T, Int)]
 
 encode(data)                                     //> res0: List[(Char, Int)] = List((a,3), (b,3), (c,3), (d,6), (e,3), (f,3), (h,
                                                  //| 1), (g,1), (i,1), (j,1), (q,1))
}