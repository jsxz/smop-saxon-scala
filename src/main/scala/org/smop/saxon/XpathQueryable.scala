package org.smop.saxon

import net.sf.saxon.s9api.XdmItem

/**
 * 
 */
trait XpathQueryable {
  def xdmItem: XdmItem
}

object XpathQueryable {
  implicit class XdmNodeIsXpathQueryable(val xdmItem: XdmItem) extends XpathQueryable
  implicit class TraversableXdmItemIsXpathQueryable(xdmItems: Traversable[XdmItem]) extends XpathQueryable {
    override def xdmItem: XdmItem = xdmItems.head
  }
}