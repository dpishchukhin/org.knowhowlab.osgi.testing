/*
 * Copyright (c) 2010-2012 Dmytro Pishchukhin (http://knowhowlab.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.knowhowlab.osgi.testing.it.paxexam;

import junit.framework.AssertionFailedError;
import org.junit.Test;
import org.knowhowlab.osgi.testing.utils.FilterUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.service.startlevel.StartLevel;

import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.ServiceAssert.assertServiceAvailable;
import static org.knowhowlab.osgi.testing.assertions.ServiceAssert.assertServiceUnavailable;

/**
 * ServiceAssert test
 *
 * @author dmytro.pishchukhin
 */
public class ServicesIntegrationTest extends AbstractTest {
    @Configuration
    public static Option[] customTestConfiguration() {
        return baseConfiguration();
    }

    @Test
    public void simpleTest() throws InvalidSyntaxException {
        // assert PackageAdmin service is available in OSGi registry
        assertServiceAvailable(PackageAdmin.class, 5, TimeUnit.SECONDS);
        // assert MonitorAdmin service is unavailable in OSGi registry
        assertServiceUnavailable("org.osgi.service.monitor.MonitorAdmin");
        // assert StartLevel service is available in OSGi registry
        assertServiceAvailable(StartLevel.class);
        // assert PackageAdmin service is available in OSGi registry
        assertServiceAvailable(FilterUtils.create(PackageAdmin.class));
        // assert MonitorAdmin service is unavailable in OSGi registry
        assertServiceUnavailable(FilterUtils.create("org.osgi.service.monitor.MonitorAdmin"));
    }

    @Test
    public void simpleTest2() throws InvalidSyntaxException {
        try {
            assertServiceAvailable(FilterUtils.create("org.osgi.service.monitor.MonitorAdmin"), 5, TimeUnit.SECONDS);
        } catch (AssertionFailedError e) {
        }
    }
}
