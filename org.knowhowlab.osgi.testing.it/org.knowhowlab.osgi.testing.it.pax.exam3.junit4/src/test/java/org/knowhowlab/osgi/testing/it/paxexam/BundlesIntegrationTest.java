/*
 * Copyright (c) 2010-2013 Dmytro Pishchukhin (http://knowhowlab.org)
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
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.util.concurrent.TimeUnit;

import static org.knowhowlab.osgi.testing.assertions.BundleAssert.*;

/**
 * BundleAssert test
 *
 * @author dmytro.pishchukhin
 */
public class BundlesIntegrationTest extends AbstractTest {
    @Configuration
    public static Option[] customTestConfiguration() {
        return baseConfiguration();
    }

    @Test
    public void simpleTest() {
        // assert bundle with id=1 is installed into OSGi framework
        assertBundleAvailable(1);
        // assert bundle with id=1 active
        assertBundleState(Bundle.ACTIVE, 1);
        // assert bundle with id=100 is not installed into OSGi framework
        assertBundleUnavailable(100);
        // assert bundle with symbolic name "org.knowhowlab.osgi.testing.all" is installed into OSGi framework
        assertBundleState(Bundle.ACTIVE, "org.knowhowlab.osgi.testing.all", 5, TimeUnit.SECONDS);
        // assert bundle with symbolic name "org.knowhowlab.osgi.testing.all" is installed into OSGi framework
        assertBundleAvailable("org.knowhowlab.osgi.testing.all");
        // assert bundle with symbolic name "org.knowhowlab.osgi.testing.all" and version System.getProperty("project.version")
        // is installed into OSGi framework
        assertBundleAvailable("org.knowhowlab.osgi.testing.all", new Version(System.getProperty("project.version")));
        // assert bundle with symbolic name "org.knowhowlab.osgi.testing.all" and version "2.0.0"
        // is not installed into OSGi framework
        assertBundleUnavailable("org.knowhowlab.osgi.testing.all", new Version("2.0.0"));
    }

    @Test
    public void simpleTest2() {
        try {
            assertBundleState(Bundle.ACTIVE, "org.knowhowlab.osgi.testing.all.unknown", 5, TimeUnit.SECONDS);
        } catch (AssertionError e) {
        }
    }
}
