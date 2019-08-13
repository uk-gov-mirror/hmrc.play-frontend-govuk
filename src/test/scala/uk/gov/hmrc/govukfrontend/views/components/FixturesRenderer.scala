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

import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.twirl.api.HtmlFormat
import scala.io.Source

trait HtmlStringTag

trait FixturesRenderer extends ReadsHelpers with JsoupHelpers {
  type HtmlString = String @@ HtmlStringTag

  implicit def reads: Reads[HtmlString]

  object HtmlString {
    def apply(html: HtmlFormat.Appendable): HtmlString =
      tagger[HtmlStringTag][String](html.body)
  }

  def twirlHtml(exampleName: String): Either[String, HtmlString] = {
    val inputJson = readInputJson(exampleName)

    Json.parse(inputJson).validate[HtmlString] match {
      case JsSuccess(htmlString, _) => Right(htmlString)
      case e: JsError               => Left(s"failed to validate params: $e")
    }
  }

  def nunjucksHtml(exampleName: String): String =
    readOutputFile(exampleName)

  private val govukFrontendVersion = "2.11.0"

  private def readOutputFile(exampleName: String): String =
    readFileAsString("output.html", exampleName)

  private def readInputJson(exampleName: String): String =
    readFileAsString("input.json", exampleName)

  private def readFileAsString(fileName: String, exampleName: String): String =
    Source
      .fromInputStream(
        getClass.getResourceAsStream(s"/fixtures/test-fixtures-$govukFrontendVersion/$exampleName/$fileName"))
      .getLines
      .mkString("\n")
}

