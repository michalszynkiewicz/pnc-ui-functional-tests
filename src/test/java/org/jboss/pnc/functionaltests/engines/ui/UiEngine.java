package org.jboss.pnc.functionaltests.engines.ui;

import org.jboss.pnc.functionaltests.engines.ui.page.BuildConfigurationPage;
import org.jboss.pnc.functionaltests.engines.ui.page.CreateBuildConfigurationPage;
import org.jboss.pnc.functionaltests.engines.ui.page.CreateProjectPage;
import org.jboss.pnc.functionaltests.engines.ui.page.KeycloakLoginPage;
import org.jboss.pnc.functionaltests.engines.ui.page.ProjectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 9:40 AM
 */
public class UiEngine {
    private WebDriver driver;

    public void setUp() {
        driver = initDriver();
        driver.get(UiResource.BASE_UI_URL);
        logIn();
    }

    private WebDriver initDriver() {
        System.setProperty("webdriver.gecko.driver", "/home/michal/job/pnc-functional-tests/geckodriver");
        FirefoxProfile profile = new ProfilesIni().getProfile("selenium-tests");
        profile.setAssumeUntrustedCertificateIssuer(false);
        profile.setAcceptUntrustedCertificates(true);

//        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//        desiredCapabilities.setCapability("acceptSslCerts", true);
        return new FirefoxDriver(profile);
    }


    protected void logIn() {
//        driver.get("https://pnc-keycloak-vip.host.stage.eng.bos.redhat.com/");
        By locator = By.xpath("//a[text()[contains(.,'Login')]]");
        WebElement loginButton = waitFor(elementToBeClickable(locator));
//        driver.findElement(locator); // mstodo move to pacge object
        loginButton.click();

        KeycloakLoginPage keycloakPage = new KeycloakLoginPage(driver);
        waitFor(elementToBeClickable(keycloakPage.username));
        keycloakPage.logIn(UiResource.USERNAME, UiResource.PASSWORD); // mstodo remove before committing!!!
    }

    private <T> T waitFor(ExpectedCondition<T> condition) {
        return UiUtils.waitFor(driver, condition);
    }

    public Integer createBuildConfiguration() {
        ProjectPage projectPage = createProject();
        CreateBuildConfigurationPage createBCPage = projectPage.createBuildConfig();

        String buildConfigName = "ui-functionaltest-bc-" + randomAlphabetic(5);
        createBCPage.setName(buildConfigName);
        createBCPage.setExternalUrl("https://github.com/michalszynkiewicz/empty");
        createBCPage.setExternalRevision("master");
//        createBCPage.setBuildScript("mvn clean deploy");
        createBCPage.setBuildScript("exit 1");
        createBCPage.setEnvironment("Demo Environment 1");
        createBCPage.create();

        projectPage.waitForLoad();
        String buildConfigurationUrl = projectPage.waitForForConfiguration(buildConfigName);
        String[] split = buildConfigurationUrl.split("/");
        String configIdAsString = split[split.length - 1];
        return Integer.valueOf(configIdAsString);
    }

    private ProjectPage createProject() {
        CreateProjectPage createProjectPage = new CreateProjectPage(driver);
        createProjectPage.goAndInit();
        return createProjectPage.createRandomProject();
    }

    public Integer triggerBuild(Integer configId) {
        BuildConfigurationPage configurationPage = new BuildConfigurationPage(configId, driver);
        configurationPage.goAndInit();
        List<WebElement> originalBuildLinks = new ArrayList<>(configurationPage.buildLinks);
        configurationPage.build.click();

        String link = waitFor(d -> {
            List<WebElement> currentLinks = new ArrayList<>(configurationPage.buildLinks);
            currentLinks.removeAll(originalBuildLinks);
            if (currentLinks.isEmpty()) {
                return null;
            } else {
                return currentLinks.get(0).getAttribute("href");
            }
        });
        String[] split = link.split("/");
        String recordIdAsString = split[split.length - 1];
        return Integer.valueOf(recordIdAsString);
    }


    public void waitForBuild(Integer configId, Integer buildId) {
        BuildConfigurationPage configurationPage = new BuildConfigurationPage(configId, driver);
        if (!configurationPage.isAt()) {
            configurationPage.goAndInit();
        }

        UiUtils.waitFor(driver, 1200, d -> {      //button[contains(text(), 'Abort')]
            System.out.println("waiting for build");
            List<WebElement> tableRows = d.findElements(By.xpath("//tr"));
            Optional<WebElement> maybeRow = tableRows.stream().filter(r -> r.getText().contains(buildId.toString())).findAny();

            if (!maybeRow.isPresent()) {
                return false;
            } else {
                WebElement row = maybeRow.get();
                System.out.println("found buildRow" + row);
                WebElement statusImg = row.findElement(By.cssSelector("td:first-child span img"));
                System.out.println("found statusImg" + statusImg);

                String alt = statusImg.getAttribute("alt");
                System.out.println("Alt: " + alt);

                switch (alt) {
                    case "Building":
                        System.out.println("building...");
                        return false;
                    case "Build completed successfully":
                        System.out.println("Success!");
                        return true;
                    default:
                        fail("Unexpected build status caption: " + alt);
                        System.out.println("Failure");
                        return null;
                }
            }
        });
    }
}
