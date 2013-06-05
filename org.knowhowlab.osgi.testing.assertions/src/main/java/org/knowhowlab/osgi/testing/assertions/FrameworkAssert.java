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
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.Version;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.knowhowlab.osgi.testing.utils.BundleUtils.findBundle;
import static org.knowhowlab.osgi.testing.utils.FrameworkUtils.waitForFrameworkEvent;

/**
 * A set of OSGi Framework specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class FrameworkAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private FrameworkAssert() {
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   FrameworkEvent type mask
     * @param bundleId        bundle id
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, int bundleId, long timeoutInMillis) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s within timeout: %sms", eventTypeMask, bundleId, timeoutInMillis), eventTypeMask, bundleId, timeoutInMillis);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param bundleId        bundle id
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, int bundleId, long timeoutInMillis) {
        assertFrameworkEvent(message, eventTypeMask, bundleId, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask FrameworkEvent type mask
     * @param bundleId        bundle id
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s within timeout: %s%s", eventTypeMask, bundleId, timeout, timeUnit), eventTypeMask, bundleId, timeout, timeUnit);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param bundleId        bundle id
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        Bundle bundle = findBundle(getBundleContext(), bundleId);
        assertThat(format("Unknown bundle with ID: %d", bundleId), bundle, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        FrameworkEvent event = waitForFrameworkEvent(getBundleContext(), bundleId, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, long timeoutInMillis) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s within timeout: %sms", eventTypeMask, symbolicName, timeoutInMillis), eventTypeMask, symbolicName, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, long timeoutInMillis) {
        assertFrameworkEvent(message, eventTypeMask, symbolicName, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s within timeout: %s%s", eventTypeMask, symbolicName, timeout, timeUnit), eventTypeMask, symbolicName, timeout, timeUnit);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        FrameworkEvent event = waitForFrameworkEvent(getBundleContext(), symbolicName, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param version       version
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, Version version, long timeoutInMillis) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s[%s] within timeout: %sms", eventTypeMask, symbolicName, version, timeoutInMillis), eventTypeMask, symbolicName, version, timeoutInMillis);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param version       version
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeoutInMillis) {
        assertFrameworkEvent(message, eventTypeMask, symbolicName, version, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param version       version
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(format("FrameworkEvent is unavailable: %s for bundle: %s[%s] within timeout: %s%s", eventTypeMask, symbolicName, version, timeout, timeUnit), eventTypeMask, symbolicName, version, timeout, timeUnit);
    }

    /**
     * Asserts that FrameworkEvent for defined bundle will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask FrameworkEvent type mask
     * @param symbolicName  symbolicName
     * @param version       version
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        assertThat("SymbolicName is null", symbolicName, notNullValue());
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        FrameworkEvent event = waitForFrameworkEvent(getBundleContext(), symbolicName, version, eventTypeMask, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }
}
