/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.layouts

import play.api.i18n.Lang
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.{MessagesHelpers, TemplateUnitSpec}
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.layout.Layout

import scala.util.Try

class govukLayoutSpec extends TemplateUnitSpec[Layout]("govukLayout") with MessagesHelpers {
  implicit lazy val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param layout
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(layout: Layout): Try[HtmlFormat.Appendable] =
    Try(
      GovukLayout.apply(
        pageTitle = layout.pageTitle,
        headBlock = layout.head,
        headerBlock = layout.header,
        footerBlock = layout.footer,
        footerItems = layout.footerItems.getOrElse(Seq.empty),
        bodyEndBlock = layout.bodyEnd,
        scriptsBlock = layout.scripts,
        beforeContentBlock = layout.beforeContent
      )(layout.content.getOrElse(HtmlFormat.empty))
    )

  "govukLayout" should {
    "render the html lang as en by default" in {
      val layoutHtml =
        GovukLayout.apply()(HtmlFormat.empty)

      val html = layoutHtml.select("html")
      html.attr("lang") shouldBe "en"
    }

    "render the html lang as cy if language toggle is set to Welsh" in {
      val messages = messagesApi.preferred(Seq(Lang("cy")))

      val layoutHtml =
        GovukLayout.apply()(HtmlFormat.empty)(messages)

      val html = layoutHtml.select("html")
      html.attr("lang") shouldBe "cy"
    }
  }
}
