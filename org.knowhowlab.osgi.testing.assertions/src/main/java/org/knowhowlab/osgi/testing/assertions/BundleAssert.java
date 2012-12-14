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

package org.knowhowlab.osgi.testing.assertions;

import org.junit.Assert;
import org.knowhowlab.osgi.testing.utils.BundleUtils;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.service.packageadmin.PackageAdmin;

import java.util.concurrent.TimeUnit;

/**
 * A set of OSGi Bundle specific assertion methods useful for writing tests.
 * <p/>
 * Before use it should be initialized
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.0
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class BundleAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private BundleAssert() {
    }

    /**
     * Asserts that Bundle with bundleId has given state value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param state    bundle state value
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleState(int state, long bundleId) {
        assertBundleState(null, state, bundleId);
    }

    /**
     * Asserts that Bundle with bundleId has given state value. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param state    bundle state value
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleState(String message, int state, long bundleId) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(String.format("Unknown bundle with ID: %d", bundleId), bundle);
        Assert.assertEquals(message, state, bundle.getState());
    }

    /**
     * Asserts that Bundle with symbolic name has given state value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param state        bundle state value
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleState(int state, String symbolicName) {
        assertBundleState(null, state, symbolicName);
    }

    /**
     * Asserts that Bundle with symbolic name has given state value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param state           bundle state value
     * @param symbolicName    symbolic name
     * @param timeoutInMillis time interval to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertBundleState(int state, String symbolicName, long timeoutInMillis) {
        assertBundleState(null, state, symbolicName, null, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that Bundle with symbolic name has given stateMask value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param message         message
     * @param stateMask       The bit mask of the ORing of the bundle states to be tracked.
     * @param symbolicName    symbolic name
     * @param timeoutInMillis time interval to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertBundleState(String message, int stateMask, String symbolicName, long timeoutInMillis) {
        assertBundleState(message, stateMask, symbolicName, null, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that Bundle with symbolic name has given stateMask value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param message      message
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param symbolicName symbolic name
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     timeout time unit
     * @since 1.0
     */
    public static void assertBundleState(String message, int stateMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertBundleState(message, stateMask, symbolicName, null, timeout, timeUnit);
    }

    /**
     * Asserts that Bundle with symbolic name has given state value. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param state        bundle state value
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleState(String message, int state, String symbolicName) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        Assert.assertEquals(message, state, bundle.getState());
    }

    /**
     * Asserts that Bundle with symbolic name has given stateMask value. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param symbolicName symbolic name
     * @param version      version
     * @param timeout      time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     timeout time unit
     * @since 1.0
     */
    public static void assertBundleState(String message, int stateMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version, stateMask, timeout, timeUnit);
        Assert.assertNotNull(message, bundle);
    }

    /**
     * Asserts that Bundle with symbolic name and version has given state value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param state        bundle state value
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleState(int state, String symbolicName, Version version) {
        assertBundleState(null, state, symbolicName, version);
    }

    /**
     * Asserts that Bundle with symbolic name and version has given stateMask value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param symbolicName symbolic name
     * @param version      version
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     timeout time unit
     * @since 1.0
     */
    public static void assertBundleState(int stateMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        assertBundleState(null, stateMask, symbolicName, version, timeout, timeUnit);
    }

    /**
     * Asserts that Bundle with symbolic name has given stateMask value. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param symbolicName symbolic name
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     timeout time unit
     * @since 1.0
     */
    public static void assertBundleState(int stateMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertBundleState(null, stateMask, symbolicName, null, timeout, timeUnit);
    }

    /**
     * Asserts that Bundle with symbolic name and version has given state value. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param state        bundle state value
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleState(String message, int state, String symbolicName, Version version) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        Assert.assertEquals(message, state, bundle.getState());
    }

    /**
     * Asserts that Bundle with bundleId is available in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleAvailable(long bundleId) {
        assertBundleAvailable(null, bundleId);
    }

    /**
     * Asserts that Bundle with bundleId is available in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleAvailable(String message, long bundleId) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(message, bundle);
    }

    /**
     * Asserts that Bundle with symbolic name is available in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleAvailable(String symbolicName) {
        assertBundleAvailable(null, symbolicName);
    }

    /**
     * Asserts that Bundle with symbolic name is available in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleAvailable(String message, String symbolicName) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName);
        Assert.assertNotNull(message, bundle);
    }

    /**
     * Asserts that Bundle with symbolic name and version is available in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleAvailable(String symbolicName, Version version) {
        assertBundleAvailable(null, symbolicName, version);
    }

    /**
     * Asserts that Bundle with symbolic name and version is available in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleAvailable(String message, String symbolicName, Version version) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version);
        Assert.assertNotNull(message, bundle);
    }

    /**
     * Asserts that Bundle with bundleId is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleUnavailable(long bundleId) {
        assertBundleUnavailable(null, bundleId);
    }

    /**
     * Asserts that Bundle with bundleId is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleUnavailable(String message, long bundleId) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNull(message, bundle);
    }

    /**
     * Asserts that Bundle with symbolic name is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleUnavailable(String symbolicName) {
        assertBundleUnavailable(null, symbolicName);
    }

    /**
     * Asserts that Bundle with symbolic name is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleUnavailable(String message, String symbolicName) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName);
        Assert.assertNull(message, bundle);
    }

    /**
     * Asserts that Bundle with symbolic name and version is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleUnavailable(String symbolicName, Version version) {
        assertBundleUnavailable(null, symbolicName, version);
    }

    /**
     * Asserts that Bundle with symbolic name and version is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertBundleUnavailable(String message, String symbolicName, Version version) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version);
        Assert.assertNull(message, bundle);
    }

    /**
     * Asserts that Bundle with bundleId is fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertFragment(long bundleId) {
        assertFragment(null, bundleId);
    }

    /**
     * Asserts that Bundle with bundleId is fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertFragment(String message, long bundleId) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(String.format("Unknown bundle with ID: %d", bundleId), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that Bundle with symbolic name is fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertFragment(String symbolicName) {
        assertFragment(null, symbolicName);
    }

    /**
     * Asserts that Bundle with symbolic name is fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertFragment(String message, String symbolicName) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that Bundle with symbolic name and version is fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertFragment(String symbolicName, Version version) {
        assertFragment(null, symbolicName, version);
    }

    /**
     * Asserts that Bundle with symbolic name and version is fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertFragment(String message, String symbolicName, Version version) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that Bundle with bundleId is not a fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertNotFragment(long bundleId) {
        assertNotFragment(null, bundleId);
    }

    /**
     * Asserts that Bundle with bundleId is not a fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertNotFragment(String message, long bundleId) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(String.format("Unknown bundle with ID: %d", bundleId), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that Bundle with symbolic name is not a fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertNotFragment(String symbolicName) {
        assertNotFragment(null, symbolicName);
    }

    /**
     * Asserts that Bundle with symbolic name is not a fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertNotFragment(String message, String symbolicName) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that Bundle with symbolic name and version is not a fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertNotFragment(String symbolicName, Version version) {
        assertNotFragment(null, symbolicName, version);
    }

    /**
     * Asserts that Bundle with symbolic name and version is not a fragment bundle. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message      message
     * @param symbolicName symbolic name
     * @param version      version
     * @since 1.0
     */
    public static void assertNotFragment(String message, String symbolicName, Version version) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), symbolicName, version);
        Assert.assertNotNull(String.format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        PackageAdmin packageAdmin = ServiceUtils.getService(getBundleContext(), PackageAdmin.class);
        Assert.assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & PackageAdmin.BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, int bundleId, long timeoutInMills) {
        assertBundleEvent(null, eventTypeMask, bundleId, timeoutInMills);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, int bundleId, long timeoutInMills) {
        assertBundleEvent(message, eventTypeMask, bundleId, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, bundleId, timeout, timeUnit);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(String.format("Unknown bundle with ID: %d", bundleId), bundle);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = BundleUtils.waitForBundleEvent(getBundleContext(), bundleId, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeoutInMills) {
        assertBundleEvent(null, eventTypeMask, symbolicName, timeoutInMills);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, long timeoutInMills) {
        assertBundleEvent(message, eventTypeMask, symbolicName, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, symbolicName, timeout, timeUnit);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = BundleUtils.waitForBundleEvent(getBundleContext(), symbolicName, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, Version version, long timeoutInMills) {
        assertBundleEvent(null, eventTypeMask, symbolicName, version, timeoutInMills);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeoutInMills) {
        assertBundleEvent(message, eventTypeMask, symbolicName, version, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeout, Version version, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, symbolicName, version, timeout, timeUnit);
    }

    /**
     * TODO
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = BundleUtils.waitForBundleEvent(getBundleContext(), symbolicName, version, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }
}
