package org.smop

import net.sf.saxon.s9api.{XdmItem, XdmNode}
import javax.xml.transform.stream.StreamSource
import java.io.StringReader

package object saxon {

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

    def xpath(params: Any*)(implicit saxonContext: SaxonContext): XPath = {
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

}
