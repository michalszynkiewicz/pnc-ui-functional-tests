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
import org.openqa.selenium.support.PageFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/3/17
 * Time: 11:54 PM
 */
public class KeycloakLoginPage extends AbstractPncPage {
    @FindBy(id="username")
    public WebElement username;
    @FindBy(id="password")
    public WebElement password;
    @FindBy(id = "kc-login")
    public WebElement logIn;

    public KeycloakLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected String getUrl() {
        throw new NotImplementedException();
    }

    public void logIn(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.logIn.click();

        waitFor(driver, d-> d.getCurrentUrl().contains(UiResource.BASE_UI_URL));
    }
}
