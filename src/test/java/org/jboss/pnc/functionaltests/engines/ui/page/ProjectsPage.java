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

import static org.jboss.pnc.functionaltests.engines.ui.UiResource.BASE_UI_URL;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/4/17
 * Time: 10:12 AM
 */
public class ProjectsPage extends AbstractPncPage {
    // mstodo remove or use to reuse project

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    protected String getUrl() {
        return BASE_UI_URL + "projects";
    }
}
