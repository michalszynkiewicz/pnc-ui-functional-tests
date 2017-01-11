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

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.regex.Pattern;

import static org.jboss.pnc.functionaltests.engines.ui.UiResource.BASE_UI_URL;
import static org.jboss.pnc.functionaltests.engines.ui.UiUtils.waitFor;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 10:21 AM
 */
public class ProjectPage extends AbstractPncPage{

    @FindBy(css = "button[title='Create Build Config']")
    WebElement createBuildConfig;

    @FindBy(css = "input.form-control")
    WebElement buildConfigFilter;

    private Integer projectId;

    public ProjectPage(WebDriver driver, Integer projectId) {
        super(driver);
        this.projectId = projectId;
        initialize();
    }

    @Override
    public void waitForLoad() {
        waitFor(driver, visibilityOf(buildConfigFilter));
    }

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    protected String getUrl() {
        return BASE_UI_URL + "projects/" + projectId;
    }

    public static ProjectPage waitForLoad(WebDriver webDriver) {
        String url = Pattern.quote(BASE_UI_URL + "projects/") + "\\d+";
        waitFor(webDriver, driver -> driver.getCurrentUrl().matches(url)) ;
        String projectIdAsString = webDriver.getCurrentUrl().replaceAll(BASE_UI_URL + "projects", "").replaceAll("/", "");
        Integer projectId = Integer.valueOf(projectIdAsString);
        return new ProjectPage(webDriver, projectId);
    }

    public CreateBuildConfigurationPage createBuildConfig() {
        waitFor(driver, elementToBeClickable(createBuildConfig));
        createBuildConfig.click();
        return CreateBuildConfigurationPage.waitForLoad(driver, projectId);
    }
    public void waitForNotification(Integer buildId) {
        waitFor(driver, d -> d.findElements(By.cssSelector("a[href='" + BASE_UI_URL + "/projects/" + projectId + "'/build-configs/'" + buildId + "']")));
    }

    /**
     *
     * @param buildConfigName name of the configuration
     * @return the url for the configuration
     */
    public String waitForForConfiguration(String buildConfigName) {
        for (int i = 0; i < 20; i++) {
            try {
                System.out.println("Waiting for build cofngig ansmdfa: " + buildConfigName);
                waitFor(driver, ExpectedConditions.visibilityOf(buildConfigFilter));
                buildConfigFilter.clear();
                buildConfigFilter.sendKeys(buildConfigName);
                System.out.println("Filled the filter");
                try {
                    WebElement element = waitFor(driver, d -> {
                        System.out.println("checking");
                        List<WebElement> elements = d.findElements(By.cssSelector("table.table-striped td:first-child a"));
                        System.out.println("found: " + elements.size() + " elements");
                        if (elements.size() == 1) {
                            return elements.get(0);
                        } else {
                            return null;
                        }
                    });
                    return element.getAttribute("href");
                } catch (TimeoutException timeout) {
                    continue;
                }
            } catch (StaleElementReferenceException ignored) {
                continue;
            }
        }
        throw new TimeoutException("Timed out waiting for build configuration");
    }
}
