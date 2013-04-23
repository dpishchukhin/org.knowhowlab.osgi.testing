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
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceEvent;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertNotNull;
import static org.knowhowlab.osgi.testing.utils.ServiceUtils.getService;
import static org.knowhowlab.osgi.testing.utils.ServiceUtils.waitForServiceEvent;

/**
 * A set of OSGi services specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class ServiceAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private ServiceAssert() {
    }

    /**
     * Asserts that service with class is available in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @since 1.0
     */
    public static void assertServiceAvailable(Class clazz) {
        assertServiceAvailable(format("Service is unavailable: %s", clazz.getName()), clazz);
    }

    /**
     * Asserts that service with class is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz   service class
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Class clazz) {
        assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = getService(getBundleContext(), clazz);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(Class clazz, long timeoutInMillis) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %sms", clazz.getName(), timeoutInMillis), clazz, timeoutInMillis);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Class clazz, long timeoutInMillis) {
        assertServiceAvailable(message, clazz, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %s%s", clazz.getName(), timeout, timeUnit), clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Class clazz, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class is null", clazz);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), clazz, timeout, timeUnit);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with class name is available in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @since 1.0
     */
    public static void assertServiceAvailable(String className) {
        assertServiceAvailable(format("Service is unavailable: %s", className), className);
    }

    /**
     * Asserts that service with class name is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, String className) {
        assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = getService(getBundleContext(), className);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(String className, long timeoutInMillis) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %sms", className, timeoutInMillis), className, timeoutInMillis);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, String className, long timeoutInMillis) {
        assertServiceAvailable(message, className, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %s%s", className, timeout, timeUnit), className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, String className, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class name is null", className);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), className, timeout, timeUnit);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with filter is available in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @since 1.0
     */
    public static void assertServiceAvailable(Filter filter) {
        assertServiceAvailable(format("Service is unavailable: %s", filter), filter);
    }

    /**
     * Asserts that service with filter is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  service filter
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Filter filter) {
        assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = getService(getBundleContext(), filter);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(Filter filter, long timeoutInMillis) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %sms", filter, timeoutInMillis), filter, timeoutInMillis);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Filter filter, long timeoutInMillis) {
        assertServiceAvailable(message, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(format("Service is unavailable: %s within timeout: %s%s", filter, timeout, timeUnit), filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceAvailable(String message, Filter filter, long timeout, TimeUnit timeUnit) {
        assertNotNull("Filter is null", filter);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), filter, timeout, timeUnit);
        assertNotNull(message, service);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @since 1.0
     */
    public static void assertServiceUnavailable(Class clazz) {
        assertServiceUnavailable(format("Service is available: %s", clazz.getName()), clazz);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz   service class
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Class clazz) {
        assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = getService(getBundleContext(), clazz);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(Class clazz, long timeoutInMillis) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %sms", clazz.getName(), timeoutInMillis), clazz, timeoutInMillis);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Class clazz, long timeoutInMillis) {
        assertServiceUnavailable(message, clazz, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %s%s", clazz.getName(), timeout, timeUnit), clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Class clazz, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class is null", clazz);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), clazz, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @since 1.0
     */
    public static void assertServiceUnavailable(String className) {
        assertServiceUnavailable(format("Service is available: %s", className), className);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, String className) {
        assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = getService(getBundleContext(), className);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(String className, long timeoutInMillis) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %sms", className, timeoutInMillis), className, timeoutInMillis);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, String className, long timeoutInMillis) {
        assertServiceUnavailable(message, className, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %s%s", className, timeout, timeUnit), className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, String className, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class name is null", className);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), className, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @since 1.0
     */
    public static void assertServiceUnavailable(Filter filter) {
        assertServiceUnavailable(format("Service is available: %s", filter), filter);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  service filter
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Filter filter) {
        assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = getService(getBundleContext(), filter);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(Filter filter, long timeoutInMillis) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %sms", filter, timeoutInMillis), filter, timeoutInMillis);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeoutInMillis) {
        assertServiceUnavailable(message, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(format("Service is available: %s within timeout: %s%s", filter, timeout, timeUnit), filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     * @since 1.0
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeout, TimeUnit timeUnit) {
        assertNotNull("Filter is null", filter);
        assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = getService(getBundleContext(), filter, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param filter          service filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Filter filter, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %sms", eventTypeMask, filter, timeoutInMillis), eventTypeMask, filter, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param filter          service filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param filter        service filter
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %s%s", eventTypeMask, filter, timeout, timeUnit), eventTypeMask, filter, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param filter        service filter
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, filter, false, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param filter          service filter
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Filter filter, boolean all, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %sms", eventTypeMask, filter, all, timeoutInMillis), eventTypeMask, filter, all, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param filter          service filter
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, boolean all, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, filter, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param filter        service filter
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Filter filter, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %s%s", eventTypeMask, filter, all, timeout, timeUnit), eventTypeMask, filter, all, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param filter        service filter
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, boolean all, long timeout, TimeUnit timeUnit) {
        assertNotNull("Filter is null", filter);
        assertNotNull("TimeUnit is null", timeUnit);
        ServiceEvent event = waitForServiceEvent(getBundleContext(), filter, eventTypeMask, all, timeout, timeUnit);
        assertNotNull(message, event);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param className       service class name
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, String className, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %sms", eventTypeMask, className, timeoutInMillis), eventTypeMask, className, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param className       service class name
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, String className, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, className, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param className     service class name
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, String className, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %s%s", eventTypeMask, className, timeout, timeUnit), eventTypeMask, className, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param className     service class name
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, String className, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, className, false, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param className       service class name
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, String className, boolean all, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %sms", eventTypeMask, className, all, timeoutInMillis), eventTypeMask, className, all, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param className       service class name
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, String className, boolean all, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, className, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param className     service class name
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, String className, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %s%s", eventTypeMask, className, all, timeout, timeUnit), eventTypeMask, className, all, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class name filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param className     service class name
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, String className, boolean all, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class name is null", className);
        assertNotNull("TimeUnit is null", timeUnit);
        ServiceEvent event = waitForServiceEvent(getBundleContext(), className, eventTypeMask, all, timeout, timeUnit);
        assertNotNull(message, event);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param clazz           service class
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Class clazz, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %sms", eventTypeMask, clazz.getName(), timeoutInMillis), eventTypeMask, clazz, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param clazz           service class
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, clazz, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param clazz         service class
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s within timeout: %s%s", eventTypeMask, clazz.getName(), timeout, timeUnit), eventTypeMask, clazz, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param clazz         service class
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, clazz, false, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param clazz           service class
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Class clazz, boolean all, long timeoutInMillis) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %sms", eventTypeMask, clazz.getName(), all, timeoutInMillis), eventTypeMask, clazz, all, timeoutInMillis);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param clazz           service class
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, boolean all, long timeoutInMillis) {
        assertServiceEvent(message, eventTypeMask, clazz, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param clazz         service class
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(int eventTypeMask, Class clazz, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(format("ServiceEvent is unavailable: %s - %s; all=%s within timeout: %s%s", eventTypeMask, clazz.getName(), all, timeout, timeUnit), eventTypeMask, clazz, all, timeout, timeUnit);
    }

    /**
     * Asserts that ServiceEvent with class filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param clazz         service class
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.1
     */
    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, boolean all, long timeout, TimeUnit timeUnit) {
        assertNotNull("Class is null", clazz);
        assertNotNull("TimeUnit is null", timeUnit);
        ServiceEvent event = waitForServiceEvent(getBundleContext(), clazz, eventTypeMask, all, timeout, timeUnit);
        assertNotNull(message, event);
    }
}
