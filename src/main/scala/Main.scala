import java.io.File

import scala.io.Source
import net.sf.saxon.s9api.{SaxonApiException, XdmItem}
import org.specs2.mutable._
import org.smop.saxon._
object Main {
  def main(args: Array[String]): Unit = {
    implicit val saxonContext = SaxonContext()
    val xsd: XNode = SaxonContext().documentBuilder.build(new File("data/4.2/descript.xsd"))
    val xp1=xpath"//*"
    val value: Traversable[XdmItem] = xp1(xsd)
    println(value)

//    val doc = xml"""<root xmlns="urn:foo">42</root>"""
//    implicit val myNSs = NameSpaces("foo" -> "urn:foo")
//    val xp = xpath"/foo:root"
//    val result = xp(doc)
//    println(result)
  }
}
