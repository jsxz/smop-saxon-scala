import scala.annotation.tailrec
import scala.io.Source
import scala.xml.{Elem, Node, NodeSeq, XML}

object GenPaths {
  def main(args: Array[String]): Unit = {
    val nodes = Source.fromFile("data/descript.txt").getLines().map {
      case line => {
        val strings = line.split(",").toList
        (strings.head.substring(0, strings.head.length - 8), strings.tail)
      }
    }.toMap
    genParentOne("para", nodes)

  }

  def genParent(li: List[String], nodes: Map[String, List[String]]): List[String] = {
    li.foreach(s => {
      if (s != "levelledPara" && s != "content") {
        println(s)
        val parentList: List[String] = nodes.filter(n => n._2.contains(s)).map(n => n._1).toList
        println(parentList)
        genParent(parentList, nodes)
      }
    })
    List()
  }

  def genParentOne(s: String, nodes: Map[String, List[String]]): List[String] = {
    println(s)
    val parentList: List[String] = nodes.filter(n => n._2.contains(s)).map(n => n._1).toList
    println(parentList)

    if (parentList.size>0 && s != parentList.head) {
      parentList.map(s=>genParentOne(s,nodes))
    }

    List()
  }

  def genChild(): Unit = {
    val xsd = XML.loadFile("data/4.2/descript.xsd")
    (xsd \\ "complexType").map {
      case e => {
        print((e \@ "name"))
        getChild(e, xsd)
        println()
      }
    }
  }

  def getChild(e: Node, xsd: Elem): String = {
    val ref = (e \@ "ref").toString
    if (e.label == "element") {
      print(s",$ref")
    }
    else {
      if (ref != "") {
        (xsd \\ "group").filter(t => (t \@ "name").toString == ref).map {
          case e1 => {
            getChild(e1, xsd)
          }
        }
      }
      (e \ "_").map {
        case e1 => getChild(e1, xsd)
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
