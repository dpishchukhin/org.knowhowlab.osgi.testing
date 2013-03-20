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

import org.junit.Before;
import org.junit.runner.RunWith;
import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

import javax.inject.Inject;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * Abstract test with all initializations
 *
 * @author dmytro.pishchukhin
 */
@RunWith(JUnit4TestRunner.class)
public abstract class AbstractTest {
    /**
     * Injected BundleContext
     */
    @Inject
    protected BundleContext bc;

    @Before
    public void init() {
        OSGiAssert.setDefaultBundleContext(bc);
    }

    /**
     * Runner config
     *
     * @return config
     */
    protected static Option[] baseConfiguration() {
        return options(
                workingDirectory("target/paxexam/"),
                junitBundles(),
                cleanCaches(false),
                // list of frameworks to test
                allFrameworks(),
                // list of bundles that should be installed
                mavenBundle().groupId("org.osgi").artifactId("org.osgi.compendium").version("4.2.0"),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.utils").version(System.getProperty("project.version")),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.assertions").version(System.getProperty("project.version")),

                systemProperty("project.version").value(System.getProperty("project.version"))
        );
    }
}
