/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2017 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.functionaltests.engines.ui.page;

import org.jboss.pnc.functionaltests.engines.ui.UiResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/9/17
 * Time: 1:20 PM
 */
public class BuildRecordPage extends AbstractPncPage {

    public final Integer recordId;

    @FindBy(xpath = "//button[contains(text(), 'Abort')]")
    public WebElement abort;
    @FindBy(xpath = "//a[text()='Log']")
    public WebElement log;

    public BuildRecordPage(WebDriver driver, Integer recordId) {
        super(driver);
        this.recordId = recordId;
    }

    @Override
    protected void waitForLoad() {
        waitFor(driver, elementToBeClickable(abort));
    }

    @Override
    protected String getUrl() {
        return UiResource.BASE_UI_URL + "build-records/" + recordId; 
    }

}
