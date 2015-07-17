package org.smop

import net.sf.saxon.s9api.{XdmItem, XdmNode}
import javax.xml.transform.stream.StreamSource
import java.io.StringReader

import scala.reflect.runtime.universe._

package object saxon {

  type XNode = XdmNode

  trait Convert[A, B] {
    def convert(a: A): B
  }

  implicit class SaxonXmlString(stringContext: StringContext) {
    def xml(params: Any*)(implicit saxonContext: SaxonContext): XdmNode = {
      val sb = new StringBuffer
      buildString(stringContext.parts, params, sb)
      saxonContext.documentBuilder.build(new StreamSource(new StringReader(sb.toString)))
    }

    private def buildString(parts: Seq[String], params: Seq[Any], result: StringBuffer)(implicit saxonContext: SaxonContext): Unit = {
      result.append(parts.head)
      params.headOption.foreach {
        case node: XdmNode =>
          result.append(node.toStr)
        case s: String =>
          result.append(s)
        case param =>
          result.append(param.toString)
      }
      if (parts.lengthCompare(1) > 0)
        buildString(parts.tail, params.tail, result)
    }

    def xpath(params: Any*)(implicit saxonContext: SaxonContext, nss: NameSpaces): XPath = {
      val sb = new StringBuffer
      buildString(stringContext.parts, params, sb)
      new XPath(sb.toString)
    }

  }

  implicit class SaxonXMLNode(xdmNode: XdmNode) {
    /**
     * Minimal toString (no indenting, no xml decl).
     */
    def toStr(implicit saxonContext: SaxonContext) = saxonContext.serializer.serializeNodeToString(xdmNode)

    def deepEqual(other: XdmNode)(implicit saxonContext: SaxonContext): Boolean = {
      //implicit val someNSs = NameSpaces("saxon" -> "http://saxon.sf.net/")
      //val testEq = XPath("saxon:deep-equal($a, $b, (), '?')")
      val testEq = XPath("deep-equal($a, $b)")
      val answer = testEq(xdmNode, "a" -> xdmNode, "b" -> other)
      answer.head.getStringValue == "true"
    }
  }

  implicit class SaxonXMLItem(xdmItem: XdmItem) {
    /**
     * Minimal toString (no indenting, no xml decl).
     */
    def toStr(implicit saxonContext: SaxonContext) =
      if (xdmItem.isAtomicValue)
        xdmItem.getStringValue
      else
        saxonContext.serializer.serializeNodeToString(xdmItem.asInstanceOf[XdmNode])
  }

  implicit class SaxonXMLXdmItemTraversable(trav: Traversable[XdmItem]) {
    def as[T](implicit c: Convert[Traversable[XdmItem], T]) = c.convert(trav)
  }

  implicit val traversableToString = new Convert[Traversable[XdmItem], String] {
    override def convert(a: Traversable[XdmItem]): String = a.map(_.getStringValue).mkString
  }
  implicit val traversableToInt = new Convert[Traversable[XdmItem], Int] {
    override def convert(a: Traversable[XdmItem]): Int = a.head.getStringValue.toInt
  }
  implicit val traversableToXdmNode = new Convert[Traversable[XdmItem], XdmNode] {
    override def convert(a: Traversable[XdmItem]): XdmNode = a.head.asInstanceOf[XdmNode]
  }

}
