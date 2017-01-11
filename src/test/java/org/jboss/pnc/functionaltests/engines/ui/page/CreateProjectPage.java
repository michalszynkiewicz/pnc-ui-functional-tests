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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.jboss.pnc.functionaltests.engines.ui.UiResource.BASE_UI_URL;
import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 9:00 AM
 */
public class CreateProjectPage extends AbstractPncPage {
    @FindBy(id = "input-name")     // mstodo data-tests!!!
    public WebElement name;

    @FindBy(css = "input.btn-primary")
    public WebElement submit;


    public CreateProjectPage(WebDriver driver) {
        super(driver);
    }

    protected String getUrl() {
        return BASE_UI_URL + "projects/create";
    }

    public ProjectPage createRandomProject() {
        String projectName = "ui-functionaltest-" + randomAlphabetic(5);
        name.sendKeys(projectName);
        waitFor(driver, elementToBeClickable(name));
        submit.click();
        return ProjectPage.waitForLoad(driver);
    }

    @Override
    protected void waitForLoad() {
        waitFor(driver, elementToBeClickable(name));
    }
}
