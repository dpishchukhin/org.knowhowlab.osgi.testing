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

import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.exam.testng.listener.PaxExam;
import org.osgi.framework.BundleContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import javax.inject.Inject;

import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;

/**
 * Abstract test with all initializations
 *
 * @author dmytro.pishchukhin
 */
@Listeners(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public abstract class AbstractTest {
    /**
     * Injected BundleContext
     */
    @Inject
    protected BundleContext bc;

    @BeforeTest
    public void init() {
        OSGiAssert.setDefaultBundleContext(bc);
    }

    /**
     * Runner config
     *
     * @return config
     */
    protected static Option[] baseConfiguration(Option... extraOptions) {
        Option[] options = options(
                mavenBundle("org.testng", "testng", "6.3.1"),
                // list of bundles that should be installed
                mavenBundle("org.osgi", "org.osgi.compendium", "4.2.0"),
                mavenBundle().groupId("org.knowhowlab.osgi").artifactId("org.knowhowlab.osgi.testing.all").version(System.getProperty("project.version")),

                systemProperty("project.version").value(System.getProperty("project.version")),
                systemProperty("org.ops4j.pax.logging.DefaultServiceLog.level").value("WARN")
        );
        if (extraOptions != null) {
            options = combine(options, extraOptions);
        }
        return options;
    }
}
