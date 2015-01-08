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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.service.packageadmin.PackageAdmin;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
        assertBundleState(format("Invalid state of bundle: %s", bundleId), state, bundleId);
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
        assertThat(format("Unknown bundle with ID: %d", bundleId), bundle, notNullValue());
        assertThat(message, bundle.getState(), equalTo(state));
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
        assertBundleState(format("Invalid state of bundle: %s", symbolicName), state, symbolicName);
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
        assertBundleState(format("Invalid state %s of bundle: %s within timeout: %sms", state, symbolicName, timeoutInMillis),
                state, symbolicName, null, timeoutInMillis, MILLISECONDS);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertThat(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle, notNullValue());
        assertThat(message, bundle.getState(), equalTo(state));
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version, stateMask, timeout, timeUnit);
        assertThat(message, bundle, notNullValue());
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
        assertBundleState(format("Invalid state of bundle: %s[%s]", symbolicName, version), state, symbolicName, version);
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
        assertBundleState(format("Invalid state %s of bundle: %s[%s] within timeout: %s%s", stateMask, symbolicName, version, timeout, timeUnit),
                stateMask, symbolicName, version, timeout, timeUnit);
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
        assertBundleState(format("Invalid state %s of bundle: %s within timeout: %s%s", stateMask, symbolicName, timeout, timeUnit),
                stateMask, symbolicName, null, timeout, timeUnit);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertThat(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle, notNullValue());
        assertThat(message, bundle.getState(), equalTo(state));
    }

    /**
     * Asserts that Bundle with bundleId is available in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleAvailable(long bundleId) {
        assertBundleAvailable(format("Bundle: %s is unavailable", bundleId), bundleId);
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
        assertThat(message, bundle, notNullValue());
    }

    /**
     * Asserts that Bundle with symbolic name is available in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleAvailable(String symbolicName) {
        assertBundleAvailable(format("Bundle: %s is unavailable", symbolicName), symbolicName);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertThat(message, bundle, notNullValue());
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
        assertBundleAvailable(format("Bundle: %s[%s] is unavailable", symbolicName, version), symbolicName, version);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertThat(message, bundle, notNullValue());
    }

    /**
     * Asserts that Bundle with bundleId is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertBundleUnavailable(long bundleId) {
        assertBundleUnavailable(format("Bundle: %s is available", bundleId), bundleId);
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
        assertThat(message, bundle, nullValue());
    }

    /**
     * Asserts that Bundle with symbolic name is unavailable in OSGi framework. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertBundleUnavailable(String symbolicName) {
        assertBundleUnavailable(format("Bundle: %s is available", symbolicName), symbolicName);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertThat(message, bundle, nullValue());
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
        assertBundleUnavailable(format("Bundle: %s[%s] is available", symbolicName, version), symbolicName, version);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertThat(message, bundle, nullValue());
    }

    /**
     * Asserts that Bundle with bundleId is fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertFragment(long bundleId) {
        assertFragment(format("Bundle: %s is not a fragment", bundleId), bundleId);
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
        assertThat(format("Unknown bundle with ID: %d", bundleId), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
    }

    /**
     * Asserts that Bundle with symbolic name is fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertFragment(String symbolicName) {
        assertFragment(format("Bundle: %s is not a fragment", symbolicName), symbolicName);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertThat(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
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
        assertFragment(format("Bundle: %s[%s] is not a fragment", symbolicName, version), symbolicName, version);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertThat(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
    }

    /**
     * Asserts that Bundle with bundleId is not a fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bundleId bundle id
     * @since 1.0
     */
    public static void assertNotFragment(long bundleId) {
        assertNotFragment(format("Bundle: %s is a fragment", bundleId), bundleId);
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
        assertThat(format("Unknown bundle with ID: %d", bundleId), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
    }

    /**
     * Asserts that Bundle with symbolic name is not a fragment bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param symbolicName symbolic name
     * @since 1.0
     */
    public static void assertNotFragment(String symbolicName) {
        assertNotFragment(format("Bundle: %s is a fragment", symbolicName), symbolicName);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName);
        assertThat(format("Unknown bundle with SymbolicName: %s", symbolicName), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
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
        assertNotFragment(format("Bundle: %s[%s] is a fragment", symbolicName, version), symbolicName, version);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        Bundle bundle = findBundle(getBundleContext(), symbolicName, version);
        assertThat(format("Unknown bundle with SymbolicName: %s and version: %s", symbolicName, version), bundle, notNullValue());
        PackageAdmin packageAdmin = getService(getBundleContext(), PackageAdmin.class);
        assertThat("PackageAdmin is unavailable", packageAdmin, notNullValue());
        int type = packageAdmin.getBundleType(bundle);
        assertThat(message, type & BUNDLE_TYPE_FRAGMENT, is(not(0)));
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s within timeout: %sms", eventTypeMask, bundleId, timeoutInMillis), eventTypeMask, bundleId, timeoutInMillis);
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s within timeout: %s%s", eventTypeMask, bundleId, timeout, timeUnit), eventTypeMask, bundleId, timeout, timeUnit);
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
        assertThat(format("Unknown bundle with ID: %d", bundleId), bundle, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        BundleEvent event = waitForBundleEvent(getBundleContext(), bundleId, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s within timeout: %sms", eventTypeMask, symbolicName, timeoutInMillis), eventTypeMask, symbolicName, timeoutInMillis);
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s within timeout: %s%s", eventTypeMask, symbolicName, timeout, timeUnit), eventTypeMask, symbolicName, timeout, timeUnit);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        BundleEvent event = waitForBundleEvent(getBundleContext(), symbolicName, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s[%s] within timeout: %sms", eventTypeMask, symbolicName, version, timeoutInMillis),
                eventTypeMask, symbolicName, version, timeoutInMillis);
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
        assertBundleEvent(format("BundleEvent is unavailable: %s - %s[%s] within timeout: %s%s", eventTypeMask, symbolicName, version, timeout, timeUnit), eventTypeMask, symbolicName, version, timeout, timeUnit);
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
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        BundleEvent event = waitForBundleEvent(getBundleContext(), symbolicName, version, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }
}
