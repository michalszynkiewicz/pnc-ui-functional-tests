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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 9:39 AM
 */
public abstract class AbstractPncPage {
    protected WebDriver driver;

    public AbstractPncPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get(getUrl());
    }

    protected abstract String getUrl();

    public void initialize() {
        PageFactory.initElements(driver, this);
    }

    public void goAndInit() {
        goTo();
        initialize();
        waitForLoad();
    }

    protected void waitForLoad() {
    }


    protected void fillInput(WebElement element, String content) {
        waitFor(driver, ExpectedConditions.elementToBeClickable(element));
        element.clear();
        element.sendKeys(content);
    }
}
