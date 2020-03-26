import scala.annotation.tailrec
import scala.xml.{Elem, Node, NodeSeq, XML}

object GenPaths {
  def main(args: Array[String]): Unit = {
    val xsd = XML.loadFile("data/4.2/descript.xsd")
    (xsd \\ "complexType").map {
      case e => {
        print((e \@ "name"))
        getChild(e,xsd)
        println()
      }
    }
  }

  def getChild(e: Node,xsd:Elem): String = {
    val ref = (e \@ "ref").toString
    if (e.label == "element") {
      print(s",$ref")
    }
    else {
      if(ref !=""){
        (xsd \\ "group").filter(t=>(t \@ "name").toString==ref).map{
          case e1=>{
            getChild(e1,xsd)
          }
        }
      }
      (e \ "_").map {
        case e1 => getChild(e1,xsd)
      }
    }
    ""
  }

  def quickSort(xs: List[Int]): List[Int] = {
    if (xs.isEmpty) xs
    else
      quickSort(xs.filter(x => x < xs.head)) ::: xs.head :: quickSort(xs.filter(x => x > xs.head))
  }

  def factorial(n: Int): Int = {
    @tailrec
    def loop(acc: Int, n: Int): Int =
      if (n == 0) acc else loop(n * acc, n - 1)

    loop(1, n)
  }
}
