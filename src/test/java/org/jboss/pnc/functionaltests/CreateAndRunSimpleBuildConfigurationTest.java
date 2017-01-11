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
package org.jboss.pnc.functionaltests;

import org.jboss.pnc.functionaltests.engines.ui.UiEngine;
import org.junit.Test;

/**
 * Author: Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * Date: 1/3/17
 * Time: 10:18 PM
 */
public class CreateAndRunSimpleBuildConfigurationTest {

    private UiEngine ui = new UiEngine();

    @Test
    public void shouldRunBuild() {
        ui.setUp();
        Integer configId = ui.createBuildConfiguration();
        Integer buildId = ui.triggerBuild(configId);
        ui.waitForBuild(configId, buildId);
    }


}
