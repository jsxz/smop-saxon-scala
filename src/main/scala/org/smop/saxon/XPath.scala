package org.smop.saxon

import collection.JavaConverters._
import net.sf.saxon.s9api.{QName, XdmItem, XdmNode}

class XPath(stringForm: String)(implicit saxonContext: SaxonContext, nss: NameSpaces) {
  val xpathCompiler = saxonContext.processor.newXPathCompiler()
  nss.bindings.foreach {
    case (prefix, uri) => xpathCompiler.declareNamespace(prefix, uri)
  }

  def apply(contextItem: XpathQueryable, vars: (String, XdmItem)*): Traversable[XdmItem] = {
    apply(contextItem, vars.toMap)
  }

  def apply(contextItem: XpathQueryable, vars: Map[String, XdmItem] = Map.empty): Traversable[XdmItem] = {
    vars.foreach {
      case (name, value) => xpathCompiler.declareVariable(new QName(name))
    }
    val executable = xpathCompiler.compile(stringForm)
    val selector = executable.load
    selector.setContextItem(contextItem.xdmItem)
    vars.foreach {
      case (name, value) => selector.setVariable(new QName(name), value)
    }

    selector.evaluate().iterator().asScala.toTraversable
  }

}

object XPath {
  def apply(stringForm: String)(implicit saxonContext: SaxonContext, nss: NameSpaces) = new XPath(stringForm)
}
