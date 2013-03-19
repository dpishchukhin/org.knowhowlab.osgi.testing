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

package org.knowhowlab.osgi.testing.assertions;

import org.junit.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.service.packageadmin.PackageAdmin;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.findBundle;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.waitForBundleEvent;
import static org.knowhowlab.osgi.testing.utils.ServiceUtils.getService;
import static org.osgi.service.packageadmin.PackageAdmin.BUNDLE_TYPE_FRAGMENT;

/**
 * A set of OSGi Bundle specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.1
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
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertNotNull(format("Unknown bundle with ID: %d", bundleId), bundle);
        assertEquals(message, state, bundle.getState());
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
        assertBundleState(null, state, symbolicName, null, timeoutInMillis, MILLISECONDS);
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
        assertBundleState(message, stateMask, symbolicName, null, timeoutInMillis, MILLISECONDS);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertNotNull(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        assertEquals(message, state, bundle.getState());
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
        assertNotNull("SymbolicName is null", symbolicName);
        assertNotNull("TimeUnit is null", timeUnit);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version, stateMask, timeout, timeUnit);
        assertNotNull(message, bundle);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertNotNull(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        assertEquals(message, state, bundle.getState());
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
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertNotNull(message, bundle);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertNotNull(message, bundle);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertNotNull(message, bundle);
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
        Bundle bundle = findBundle(getBundleContext(), bundleId);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
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
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertNotNull(format("Unknown bundle with ID: %d", bundleId), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertNotNull(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertNotNull(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
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
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertNotNull(format("Unknown bundle with ID: %d", bundleId), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertNotNull(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
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
        assertNotNull("SymbolicName is null", symbolicName);
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertNotNull(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle);
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertNotNull("PackageAdmin is unavailable", packageAdmin);
        int type = packageAdmin.getBundleType(bundle);
        Assert.assertTrue(message, (type & BUNDLE_TYPE_FRAGMENT) != 0);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   BundleEvent type mask
     * @param bundleId        bundle id
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, int bundleId, long timeoutInMillis) {
        assertBundleEvent(null, eventTypeMask, bundleId, timeoutInMillis);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   BundleEvent type mask
     * @param bundleId        bundle id
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, int bundleId, long timeoutInMillis) {
        assertBundleEvent(message, eventTypeMask, bundleId, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask BundleEvent type mask
     * @param bundleId      bundle id
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, bundleId, timeout, timeUnit);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask BundleEvent type mask
     * @param bundleId      bundle id
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertNotNull(format("Unknown bundle with ID: %d", bundleId), bundle);
        assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = waitForBundleEvent(getBundleContext(), bundleId, eventTypeMask, timeout, timeUnit);
        assertNotNull(message, event);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeoutInMillis) {
        assertBundleEvent(null, eventTypeMask, symbolicName, timeoutInMillis);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, long timeoutInMillis) {
        assertBundleEvent(message, eventTypeMask, symbolicName, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, symbolicName, timeout, timeUnit);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertNotNull("SymbolicName is null", symbolicName);
        assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = waitForBundleEvent(getBundleContext(), symbolicName, eventTypeMask, timeout, timeUnit);
        assertNotNull(message, event);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param version         version
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, Version version, long timeoutInMillis) {
        assertBundleEvent(null, eventTypeMask, symbolicName, version, timeoutInMillis);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param version         version
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeoutInMillis) {
        assertBundleEvent(message, eventTypeMask, symbolicName, version, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param version         version
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(int eventTypeMask, String symbolicName, long timeout, Version version, TimeUnit timeUnit) {
        assertBundleEvent(null, eventTypeMask, symbolicName, version, timeout, timeUnit);
    }

    /**
     * Asserts that BundleEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   BundleEvent type mask
     * @param symbolicName    symbolicName
     * @param version         version
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertBundleEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        assertNotNull("SymbolicName is null", symbolicName);
        assertNotNull("TimeUnit is null", timeUnit);
        BundleEvent event = waitForBundleEvent(getBundleContext(), symbolicName, version, eventTypeMask, timeout, timeUnit);
        assertNotNull(message, event);
    }
}
