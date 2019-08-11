/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.views.components

import org.jsoup.Jsoup
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.components.backLinkSpec.reads
import uk.gov.hmrc.govukfrontend.views.html.components._

class backLinkSpec
    extends RenderHtmlSpec(
      Seq(
        "back-link-default",
        "back-link-with-custom-text"
      )
    ) {

  "backLink" should {
    "fail to render if the required fields are not included" in {
      val caught =
        intercept[IllegalArgumentException] {
          Backlink.apply(href = "")(Text("Example"))
        }

      caught.getMessage shouldBe "requirement failed"
    }

    "render the default example with an anchor, href and text correctly" in {
      val rendered  = Backlink.apply(href = "#")(Empty).body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.first.tagName shouldBe "a"
      component.attr("href")  shouldBe "#"
      component.text          shouldBe "Back"
    }

    "render classes correctly" in {
      val rendered = Backlink
        .apply(href = "#", classes = "app-back-link--custom-class")(HtmlContent("<b>Back</b>"))
        .body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      assert(component.hasClass("app-back-link--custom-class"))
    }

    "render custom text correctly" in {
      val rendered  = Backlink.apply(href = "#")(Text("Home")).body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.html shouldBe "Home"
    }

    "render escaped html when passed as text" in {
      val rendered  = Backlink.apply(href = "#")(Text("<b>Home</b>")).body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.html shouldBe "&lt;b&gt;Home&lt;/b&gt;"
    }

    "render html correctly" in {
      val rendered  = Backlink.apply(href = "#")(HtmlContent("<b>Back</b>")).body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.html shouldBe "<b>Back</b>"
    }

    "render default text correctly" in {
      val rendered  = Backlink.apply(href = "#")(Empty).body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.html shouldBe "Back"
    }

    "render attributes correctly" in {
      val rendered = Backlink
        .apply(href = "#", attributes = Map("data-test" -> "attribute", "aria-label" -> "Back to home"))(
          HtmlContent("Back"))
        .body
      val component = Jsoup.parse(rendered).select(".govuk-back-link")

      component.attr("data-test")  shouldBe "attribute"
      component.attr("aria-label") shouldBe "Back to home"
    }
  }
}

object backLinkSpec {

  case class Params(
    contents: Contents = Empty,
    href: String,
    classes: String                 = "",
    attributes: Map[String, String] = Map.empty
  )

  import RenderHtmlSpec._

  implicit val reads: Reads[HtmlString] = (
    readsContents and
      (__ \ "href").read[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(
    (contents, href, classes, attributes) =>
      tagger[HtmlStringTag][String](
        Backlink
          .apply(href, classes, attributes)(contents)
          .body))
}