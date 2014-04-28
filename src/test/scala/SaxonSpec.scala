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
    "handle namespaces" in {
      failure
    }.pendingUntilFixed
  }
}
