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

package org.knowhowlab.osgi.testing.it.arquillian.external;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.ScopeFilter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.knowhowlab.osgi.testing.it.testbundle.service.Echo;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;

import javax.inject.Inject;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.knowhowlab.osgi.testing.assertions.BundleAssert.assertBundleAvailable;
import static org.knowhowlab.osgi.testing.assertions.BundleAssert.assertBundleState;
import static org.knowhowlab.osgi.testing.assertions.OSGiAssert.getBundleContext;
import static org.knowhowlab.osgi.testing.assertions.ServiceAssert.assertServiceAvailable;
import static org.knowhowlab.osgi.testing.assertions.ServiceAssert.assertServiceUnavailable;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.findBundle;
import static org.knowhowlab.osgi.testing.utils.FilterUtils.*;

@RunWith(Arquillian.class)
@Ignore
public class ExternalBundleIntegrationTest {
    @Inject
    public BundleContext bundleContext;

    @Before
    public void before() {
        OSGiAssert.setDefaultBundleContext(bundleContext);
    }

    @Deployment
    public static JavaArchive createDeployment() {
/*
        File[] files = DependencyResolvers.use(MavenDependencyResolver.class).artifact("org.knowhowlab.osgi:org.knowhowlab.osgi.testing.it.test.bundle:1.0.1-SNAPSHOT").resolveAsFiles(new ScopeFilter());
        return ShrinkWrap.createFromZipFile(JavaArchive.class, files[0]);
*/
        File[] files = DependencyResolvers.use(MavenDependencyResolver.class).artifact("org.knowhowlab.osgi:org.knowhowlab.osgi.testing.it.test.bundle:1.0.1-SNAPSHOT").resolveAsFiles(new ScopeFilter());
        return ShrinkWrap.create(ZipImporter.class).importFrom(files[0]).as(JavaArchive.class);
    }
    /**
     * The same Test bundle and service with OSGi assertions/utils
     *
     * @throws org.osgi.framework.BundleException
     *          bundle start/stop problems
     * @throws org.osgi.framework.InvalidSyntaxException
     *          filter creation problems
     */
    @Test
    public void test_With_OSGiAssertions() throws BundleException, InvalidSyntaxException {
        // asserts that test bundle is installed
        assertBundleAvailable("org.knowhowlab.osgi.testing.it.test.bundle");
        // asserts that test bundle is resolved
        assertBundleState(Bundle.INSTALLED | Bundle.RESOLVED, "org.knowhowlab.osgi.testing.it.test.bundle", 5, TimeUnit.SECONDS);
        // gets bundle instance
        Bundle bundle = findBundle(OSGiAssert.getBundleContext(), "org.knowhowlab.osgi.testing.it.test.bundle");
        // asserts that test service is unavailable
        assertServiceUnavailable("org.knowhowlab.osgi.testing.it.testbundle.service.Echo");
        // start bundle
        bundle.start();
        // asserts that test bundle is active
        assertBundleState(Bundle.ACTIVE, "org.knowhowlab.osgi.testing.it.test.bundle");
        // asserts that test service is available within 2 seconds
        assertServiceAvailable("org.knowhowlab.osgi.testing.it.testbundle.service.Echo", 2, TimeUnit.SECONDS);
        // asserts that test service with custom properties is available
        assertServiceAvailable(and(create(Echo.class), eq("testkey", "testvalue")));
        // gets service by class and filter
        Echo echo = ServiceUtils.getService(getBundleContext(), Echo.class, eq("testkey", "testvalue"));
        // asserts service method call
        assertEquals("test", echo.echo("test"));
        // stops bundle
        bundle.stop();
        // asserts that test bundle is resolved
        assertBundleState(Bundle.RESOLVED, "org.knowhowlab.osgi.testing.it.test.bundle");
        // asserts that test service is unregistered
        assertServiceUnavailable(Echo.class);
    }

}
