smop-saxon-scala
================

Convenience library for working with XML in Scala, based on Saxon.

- XML and XPath literals using String Interpolation
- Apply XPath to a node, get a Traversable of items back
- Register namespace mappings using implicits

Example:

```scala
val doc = xml"""<root xmlns="urn:foo">42</root>"""
implicit val myNSs = NameSpaces("foo" -> "urn:foo")
val xp = xpath"/foo:root"
val result = xp(doc)
result.head.toStr === """<root xmlns="urn:foo">42</root>"""
```
