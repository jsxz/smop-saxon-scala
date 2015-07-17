import net.sf.saxon.s9api.SaxonApiException
import org.specs2.mutable._
import org.smop.saxon._

class SaxonSpec extends Specification {
  implicit val saxonContext = SaxonContext()
  "The smop Saxon library" should {
    "parse XML" in {
      val node = xml"""<foo bar="baz"/>"""
      node.toStr === """<foo bar="baz"/>"""
    }
    "bomb on ill-formed xml" in {
      xml"<foo></bar>" should throwA[SaxonApiException]
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
    "Compare nodes" in {
      val n1 = xml"""<foo xmlns:foofoo="urn:foofoo" bar="1" baz="2"/>"""
      val n2 = xml"""<foo baz="2" bar='1'><!-- ignored --></foo>"""
      val n3 = xml"""<foo baz="2" bar='3'></foo>"""
      val testEq = XPath("deep-equal($a, $b)")
      val answer = testEq(n1, "a" -> n1, "b" -> n2)
      answer.head.isAtomicValue should beTrue
      answer.head.getStringValue === "true"
      val answer2 = testEq(n1, Map("a" -> n1, "b" -> n3))
      answer2.head.isAtomicValue should beTrue
      answer2.head.getStringValue === "false"
    }
    "Compare nodes Saxon-specific" in {
      implicit val someNSs = NameSpaces("saxon" -> "http://saxon.sf.net/")
      val n1 = xml"""<foo bar="1" baz="2"/>"""
      val n2 = xml"""<foo baz="2" bar='1'></foo>"""
      val n3 = xml"""<foo baz="2" bar='3'></foo>"""
      // http://www.saxonica.com/documentation/functions/saxon/deep-equal.html
      val testEq = XPath("saxon:deep-equal($a, $b, '?')")
      val answer = testEq(n1, Map("a" -> n1, "b" -> n2))
      answer.head.isAtomicValue should beTrue
      answer.head.getStringValue === "true"
      val answer2 = testEq(n1, Map("a" -> n1, "b" -> n3))
      answer2.head.isAtomicValue should beTrue
      answer2.head.getStringValue === "false"
    }.pendingUntilFixed("Needs Saxon PE or EE")
  }
}
