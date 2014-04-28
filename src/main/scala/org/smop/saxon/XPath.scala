package org.smop.saxon

import collection.JavaConverters._
import net.sf.saxon.s9api.{XdmItem, XdmNode}

class XPath(stringForm: String)(implicit saxonContext: SaxonContext) {
  val executable = saxonContext.xpathCompiler.compile(stringForm)
  def apply(node: XdmNode): Traversable[XdmItem] = {
    val selector = executable.load
    selector.setContextItem(node)
    selector.iterator().asScala.toTraversable
  }
}
