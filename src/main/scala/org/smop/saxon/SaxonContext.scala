package org.smop.saxon

import net.sf.saxon.s9api._


class SaxonContext(processor: Processor) {
  val documentBuilder: DocumentBuilder = processor.newDocumentBuilder()
  val xpathCompiler: XPathCompiler = processor.newXPathCompiler()
  val serializer: Serializer = processor.newSerializer()
  serializer.setOutputProperty(Serializer.Property.INDENT, "no")
  serializer.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes")
}

object SaxonContext {
  def apply(): SaxonContext = {
    val proc = new Processor(false)
    new SaxonContext(proc)
  }
}