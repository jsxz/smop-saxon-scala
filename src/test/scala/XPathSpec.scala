import org.specs2.mutable._
import org.smop.saxon._

class XPathSpec extends Specification {
  implicit val saxonContext = SaxonContext()

  "XPath results" should {
    "be usable as String" in {
      val doc = xml"<doc><sub>foo</sub></doc>"
      val path = xpath"/doc/sub"
      path(doc).as[String] === "foo"
    }
    "be usable as XML" in {
      val doc = xml"<doc><sub>foo</sub></doc>"
      val path = xpath"/doc/sub"
      val subdoc = path(doc).as[XNode]
      subdoc.toStr === "<sub>foo</sub>"
      //subdoc.deepEqual(xml"<sub>foo</sub>") should beTrue
    }
    "be usable as number" in {
      val doc = xml"<doc><sub>foo</sub></doc>"
      val path = xpath"count(/doc/sub)"
      path(doc).as[Int] === 1
    }
  }
}
