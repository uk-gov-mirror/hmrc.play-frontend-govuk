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

package uk.gov.hmrc.govukfrontend.views
package layouts

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.template.Template

import scala.util.Try

class govukTemplateSpec extends TemplateUnitSpec[Template]("govukTemplate") {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param template
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(template: Template): Try[HtmlFormat.Appendable] =
    Try(
      GovukTemplate.apply(
        htmlLang = template.htmlLang,
        pageTitleLang = template.pageTitleLang,
        mainLang = template.mainLang,
        htmlClasses = template.htmlClasses,
        themeColour = template.themeColor,
        bodyClasses = template.bodyClasses,
        pageTitle = template.pageTitle,
        headIcons = template.headIcons,
        headBlock = template.head,
        bodyStart = template.bodyStart,
        skipLinkBlock = template.skipLink,
        headerBlock = template.header.getOrElse(GovukHeader()),
        footerBlock = template.footer.getOrElse(GovukFooter()),
        bodyEndBlock = template.bodyEnd,
        mainClasses = template.mainClasses,
        beforeContentBlock = template.beforeContent
      )(template.content.getOrElse(HtmlFormat.empty))
    )

  "template rendered with default values" should {
    "not have whitespeace before the doctype" in {
      val templateHtml =
        GovukTemplate
          .apply(htmlLang = None, htmlClasses = None, themeColour = None, bodyClasses = None)(HtmlFormat.empty)
      val component    = templateHtml.body
      component.charAt(0) shouldBe '<'
    }
  }
}
