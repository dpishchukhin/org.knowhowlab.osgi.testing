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
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.packageadmin.PackageAdmin;
import org.knowhowlab.osgi.testing.assertions.ServiceAssert;
import org.knowhowlab.osgi.testing.utils.FilterUtils;

import java.util.concurrent.TimeUnit;

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
        ServiceAssert.assertServiceAvailable(PackageAdmin.class, 5, TimeUnit.SECONDS);
        // assert MonitorAdmin service is unavailable in OSGi registry
        ServiceAssert.assertServiceUnavailable("org.osgi.service.monitor.MonitorAdmin");

        // assert PackageAdmin service is available in OSGi registry
        ServiceAssert.assertServiceAvailable(FilterUtils.create(PackageAdmin.class));
        // assert MonitorAdmin service is unavailable in OSGi registry
        ServiceAssert.assertServiceUnavailable(FilterUtils.create("org.osgi.service.monitor.MonitorAdmin"));
    }

    @Test
    public void simpleTest2() throws InvalidSyntaxException {
        try {
            ServiceAssert.assertServiceAvailable(FilterUtils.create("org.osgi.service.monitor.MonitorAdmin"), 5, TimeUnit.SECONDS);
        } catch (AssertionFailedError e) {
        }
    }
}
