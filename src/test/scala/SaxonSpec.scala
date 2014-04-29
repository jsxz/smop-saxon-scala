import org.specs2.mutable._
import org.smop.saxon._

class SaxonSpec extends Specification {
  implicit val saxonContext = SaxonContext()
  "The smop Saxon library" should {
    "parse XML" in {
      val node = xml"""<foo bar="baz"/>"""
      node.toStr === """<foo bar="baz"/>"""
    }
    "interpolate" in {
      val i = 5
      val node = xml"""<foo bar="$i"/>"""
      node.toStr === """<foo bar="5"/>"""
      val node2 = xml"<baz>$node</baz>"
      node2.toStr === """<baz><foo bar="5"/></baz>"""
    }
    "have xpath" in {
      val doc = xml"""<doc><el at="2"/></doc>"""
      val i = 2
      val xp = xpath"/doc/el[@at='$i']"
      xp(doc).head.toStr === xml"""<el at="2"/>""".toStr
    }
    "support namespace registration" in {
      implicitly[NameSpaces] === new NameSpaces(Map())
      implicit val someNSs = NameSpaces("foo" -> "urn:foo", "bar" -> "http://bar.com/")
      implicitly[NameSpaces].toString === "NameSpaces(foo -> urn:foo, bar -> http://bar.com/)"

      {
        implicit val moreNSs = NameSpaces("baz" -> "urn:baz")
        moreNSs.toString === "NameSpaces(foo -> urn:foo, bar -> http://bar.com/, baz -> urn:baz)"
      }
    }
    "Use registered namespaces in xpath" in {
      val doc = xml"""<root xmlns="urn:foo">42</root>"""
      val xp1 = xpath"/root"
      xp1(doc) should beEmpty
      implicit val myNSs = NameSpaces("foo" -> "urn:foo")
      val xp2 = xpath"/foo:root"
      val result = xp2(doc)
      result.head.toStr === """<root xmlns="urn:foo">42</root>"""
      val xp3 = xpath"number(/foo:root)"
      val answer = xp3(doc)
      answer.head.isAtomicValue should beTrue
      answer.head.getStringValue === "42"
    }
  }
}
