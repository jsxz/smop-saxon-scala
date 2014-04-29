package org.smop.saxon

class NameSpaces(val bindings: Map[String, String]) {
  override def hashCode(): Int = bindings.hashCode()

  override def equals(p1: scala.Any): Boolean = p1 match {
    case nss: NameSpaces => bindings.equals(nss.bindings)
    case _ => false
  }

  override def toString: String = bindings.mkString("NameSpaces(", ", ", ")")
}

object NameSpaces {
  def apply(bindings: (String, String)*)(implicit inheritFrom: NameSpaces) = new NameSpaces(inheritFrom.bindings ++ bindings)
  implicit val noNameSpaceBindings = new NameSpaces(Map())
}
