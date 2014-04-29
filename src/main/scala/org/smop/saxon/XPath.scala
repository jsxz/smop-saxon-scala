package org.smop.saxon

import collection.JavaConverters._
import net.sf.saxon.s9api.{XdmItem, XdmNode}

class XPath(stringForm: String)(implicit saxonContext: SaxonContext, nss: NameSpaces) {
  val xpathCompiler = saxonContext.processor.newXPathCompiler()
  nss.bindings.foreach {
    case (prefix, uri) => xpathCompiler.declareNamespace(prefix, uri)
  }
  val executable = xpathCompiler.compile(stringForm)
  def apply(node: XdmNode): Traversable[XdmItem] = {
    val selector = executable.load
    selector.setContextItem(node)
    selector.iterator().asScala.toTraversable
  }
}
