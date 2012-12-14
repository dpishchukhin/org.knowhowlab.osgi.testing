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

import org.junit.Test;
import org.knowhowlab.osgi.testing.it.paxexam.service.TestService;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.osgi.framework.*;
import org.osgi.service.startlevel.StartLevel;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.BundleAssert.assertBundleEvent;
import static org.knowhowlab.osgi.testing.assertions.BundleAssert.assertBundleState;
import static org.knowhowlab.osgi.testing.assertions.FrameworkAssert.assertFrameworkEvent;
import static org.knowhowlab.osgi.testing.assertions.OSGiAssert.getBundleContext;
import static org.knowhowlab.osgi.testing.assertions.ServiceAssert.*;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.startBundleAsync;
import static org.knowhowlab.osgi.testing.utils.ServiceUtils.registerServiceAsync;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * This is JUnit test that shows difference between tests
 * with OSGi assertions/utils and without
 *
 * @author dmytro.pishchukhin
 */
public class NewIn_1_1_IntegrationTest extends AbstractTest {
    /**
     * Install test bundle
     *
     * @return config
     */
    @Configuration
    public static Option[] customTestConfiguration() {
        return combine(baseConfiguration(),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.it.test.bundle").
                        version(System.getProperty("project.version")).noUpdate().noStart());
    }

    @Test
    public void test_Start_Bundle_Async() {
        // bundle stopped
        assertBundleState(Bundle.RESOLVED | Bundle.INSTALLED, "org.knowhowlab.osgi.testing.it.test.bundle", 1L, TimeUnit.MILLISECONDS);
        // start bundle in 2 sec
        startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 2, TimeUnit.SECONDS);
        // bundle is still stopped
        assertBundleState(Bundle.RESOLVED | Bundle.INSTALLED, "org.knowhowlab.osgi.testing.it.test.bundle", 1L, TimeUnit.MILLISECONDS);
        // bundle is active after 5 sec
        assertBundleState(Bundle.ACTIVE, "org.knowhowlab.osgi.testing.it.test.bundle", 5, TimeUnit.SECONDS);
    }

    @Test
    public void test_Bundle_Event() {
        // start bundle in 2 sec
        startBundleAsync(getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle", 2, TimeUnit.SECONDS);
        // expect bundle event within 5 sec
        assertBundleEvent(BundleEvent.STARTED, "org.knowhowlab.osgi.testing.it.test.bundle", 5, TimeUnit.SECONDS);
    }

    @Test
    public void test_RegisterService_Async() {
        // service unregistered
        assertServiceUnavailable(TestService.class);
        // register service in 2 sec
        registerServiceAsync(getBundleContext(), TestService.class, new TestService(), null, 2, TimeUnit.SECONDS);
        // service still unregistered
        assertServiceUnavailable(TestService.class);
        // service registered
        assertServiceAvailable(TestService.class, 5, TimeUnit.SECONDS);
    }

    @Test
    public void test_Service_Event() {
        // register service in 2 sec
        registerServiceAsync(getBundleContext(), TestService.class, new TestService(), null, 2, TimeUnit.SECONDS);
        // service registered within 5 sec
        assertServiceEvent(ServiceEvent.REGISTERED, TestService.class, 5, TimeUnit.SECONDS);
    }

    @Test
    public void test_Framework_Event() {
        // change start level
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
            public void run() {
                StartLevel startLevel = ServiceUtils.getService(getBundleContext(), StartLevel.class);
                startLevel.setStartLevel(10);
            }
        }, 2, TimeUnit.SECONDS);

        // start level changed within 5 sec
        assertFrameworkEvent(FrameworkEvent.STARTLEVEL_CHANGED, 0, 5, TimeUnit.SECONDS);
    }
}
