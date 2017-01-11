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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 11:54 AM
 */
public class CreateBuildConfigurationPage extends AbstractPncPage {

    @FindBy(id = "input-name")
    WebElement name;
    @FindBy(id = "input-scm-repo-url-external")
    WebElement externalUrl;
    @FindBy(id = "input-scm-revision-external")
    WebElement externalRevision;
    @FindBy(id = "input-build-script")
    WebElement buildScript;
    @FindBy(id = "environmentId")
    WebElement environment;

    @FindBy(css = "span.btn-primary")
    WebElement create;

    private Integer projectId;

    public CreateBuildConfigurationPage(Integer projectId, WebDriver driver) {
        super(driver);
        this.projectId = projectId;
    }

    @Override
    protected String getUrl() {
        return UiResource.BASE_UI_URL + "projects/" + projectId + "/create-bc";
    }

    public static CreateBuildConfigurationPage waitForLoad(WebDriver driver, Integer projectId) {
        CreateBuildConfigurationPage page = new CreateBuildConfigurationPage(projectId, driver);
        String url = page.getUrl();
        waitFor(driver, d -> d.getCurrentUrl().contains(url));
        page.initialize();
        return page;
    }

    public void setName(String buildConfigName) {
        fillInput(name, buildConfigName);
    }

    public void setExternalUrl(String url) {
        fillInput(externalUrl, url);
    }

    public void setExternalRevision(String revision) {
        fillInput(externalRevision, revision);
    }

    public void setBuildScript(String script) {
        fillInput(buildScript, script);
    }

    public void setEnvironment(String environmentSubstring) {
        waitFor(driver, elementToBeClickable(environment));
        environment.click();
        fillInput(environment, environmentSubstring);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();  
        }
        By selector = By.cssSelector("li.dropdown-item");
        WebElement env = waitFor(driver, d -> {
            List<WebElement> elements = d.findElements(selector)
                    .stream()
                    .filter(e -> {
                        try {
                            String text = e.getText();
                            return e.isDisplayed() && text.contains(environmentSubstring);
                        } catch (Exception any) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());
            System.out.println("Elements found: " + elements);
            if (elements.size() == 1) {
                return elements.get(0);
            } else {
                return null;
            }
        });
        env.click();
    }

    public void create() {
        waitFor(driver, elementToBeClickable(create));
        create.click();
    }
}
