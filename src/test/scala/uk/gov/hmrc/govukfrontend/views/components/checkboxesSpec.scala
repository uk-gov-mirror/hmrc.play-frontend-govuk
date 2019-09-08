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

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class checkboxesSpec
    extends RenderHtmlSpec(
      Seq(
        "checkboxes-default",
        "checkboxes-small",
        "checkboxes-small-with-conditional-reveal",
        "checkboxes-small-with-disabled",
        "checkboxes-small-with-error",
        "checkboxes-small-with-hint",
        "checkboxes-small-with-long-text",
        "checkboxes-with-a-medium-legend",
        "checkboxes-with-all-fieldset-attributes",
        "checkboxes-with-conditional-item-checked",
        "checkboxes-with-conditional-items",
        "checkboxes-with-disabled-item",
        "checkboxes-with-error-message",
        "checkboxes-with-error-message-and-hints-on-items",
        "checkboxes-with-hints-on-items",
        "checkboxes-with-id-and-name",
        "checkboxes-with-legend-as-a-page-heading",
        "checkboxes-with-optional-form-group-classes-showing-group-error",
        "checkboxes-with-single-option-(and-hint)-set-'aria-describedby'-on-input",
        "checkboxes-with-single-option-set-'aria-describedby'-on-input",
        "checkboxes-with-very-long-option-text",
        "checkboxes-without-fieldset"
      )
    ) {

  override implicit val reads: Reads[Html] = (
    (__ \ "fieldset").readNullable[FieldsetParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "idPrefix").readNullable[String] and
      (__ \ "name").readWithDefault[String]("") and
      (__ \ "items").read[Seq[CheckboxItem]] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(Checkboxes.apply _)
}