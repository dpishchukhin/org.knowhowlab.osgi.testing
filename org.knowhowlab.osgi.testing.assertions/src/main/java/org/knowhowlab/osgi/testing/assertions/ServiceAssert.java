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
import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;

import java.util.concurrent.TimeUnit;

/**
 * A set of OSGi services specific assertion methods useful for writing tests.
 * <p/>
 * Before use it should be initialized
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.0
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
     */
    public static void assertServiceAvailable(Class clazz) {
        assertServiceAvailable(null, clazz);
    }

    /**
     * Asserts that service with class is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz   service class
     */
    public static void assertServiceAvailable(String message, Class clazz) {
        Assert.assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(Class clazz, long timeoutInMillis) {
        assertServiceAvailable(null, clazz, timeoutInMillis);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(String message, Class clazz, long timeoutInMillis) {
        assertServiceAvailable(message, clazz, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(String message, Class clazz, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class is null", clazz);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz, timeout, timeUnit);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class name is available in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     */
    public static void assertServiceAvailable(String className) {
        assertServiceAvailable(null, className);
    }

    /**
     * Asserts that service with class name is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     */
    public static void assertServiceAvailable(String message, String className) {
        Assert.assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(String className, long timeoutInMillis) {
        assertServiceAvailable(null, className, timeoutInMillis);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(String message, String className, long timeoutInMillis) {
        assertServiceAvailable(message, className, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     */
    public static void assertServiceAvailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     */
    public static void assertServiceAvailable(String message, String className, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class name is null", className);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className, timeout, timeUnit);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with filter is available in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     */
    public static void assertServiceAvailable(Filter filter) {
        assertServiceAvailable(null, filter);
    }

    /**
     * Asserts that service with filter is available in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  service filter
     */
    public static void assertServiceAvailable(String message, Filter filter) {
        Assert.assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(Filter filter, long timeoutInMillis) {
        assertServiceAvailable(null, filter, timeoutInMillis);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceAvailable(String message, Filter filter, long timeoutInMillis) {
        assertServiceAvailable(message, filter, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(String message, Filter filter, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Filter is null", filter);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter, timeout, timeUnit);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     */
    public static void assertServiceUnavailable(Class clazz) {
        assertServiceUnavailable(null, clazz);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz   service class
     */
    public static void assertServiceUnavailable(String message, Class clazz) {
        Assert.assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(Class clazz, long timeoutInMillis) {
        assertServiceUnavailable(null, clazz, timeoutInMillis);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param clazz           service class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(String message, Class clazz, long timeoutInMillis) {
        assertServiceUnavailable(message, clazz, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param clazz    service class
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(String message, Class clazz, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class is null", clazz);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     */
    public static void assertServiceUnavailable(String className) {
        assertServiceUnavailable(null, className);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     */
    public static void assertServiceUnavailable(String message, String className) {
        Assert.assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(String className, long timeoutInMillis) {
        assertServiceUnavailable(null, className, timeoutInMillis);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param className       service class name
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(String message, String className, long timeoutInMillis) {
        assertServiceUnavailable(message, className, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     */
    public static void assertServiceUnavailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message   message
     * @param className service class name
     * @param timeout   time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  timeout time unit
     */
    public static void assertServiceUnavailable(String message, String className, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class name is null", className);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     */
    public static void assertServiceUnavailable(Filter filter) {
        assertServiceUnavailable(null, filter);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  service filter
     */
    public static void assertServiceUnavailable(String message, Filter filter) {
        Assert.assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(Filter filter, long timeoutInMillis) {
        assertServiceUnavailable(null, filter, timeoutInMillis);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param filter          service filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeoutInMillis) {
        assertServiceUnavailable(message, filter, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param filter   service filter
     * @param timeout  time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Filter is null", filter);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter, timeout, timeUnit);
        Assert.assertNull(message, service);
    }

    public static void assertServiceEvent(int eventTypeMask, Filter filter, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, filter, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, filter, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, filter, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, filter, false, timeout, timeUnit);
    }

    public static void assertServiceEvent(int eventTypeMask, Filter filter, boolean all, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, filter, all, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, boolean all, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, filter, all, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, Filter filter, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, filter, all, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Filter filter, boolean all, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Filter is null", filter);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        try {
            ServiceEvent event = ServiceUtils.waitForServiceEvent(getBundleContext(), filter, eventTypeMask, all, timeout, timeUnit);
            Assert.assertNotNull(message, event);
        } catch (InvalidSyntaxException e) {
            Assert.fail("Invalid filter: " + filter);
        }
    }

    public static void assertServiceEvent(int eventTypeMask, String className, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, className, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, String className, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, className, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, String className, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, className, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, String className, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, className, false, timeout, timeUnit);
    }

    public static void assertServiceEvent(int eventTypeMask, String className, boolean all, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, className, all, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, String className, boolean all, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, className, all, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, String className, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, className, all, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, String className, boolean all, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class name is null", className);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        try {
            ServiceEvent event = ServiceUtils.waitForServiceEvent(getBundleContext(), className, eventTypeMask, all, timeout, timeUnit);
            Assert.assertNotNull(message, event);
        } catch (InvalidSyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }

    public static void assertServiceEvent(int eventTypeMask, Class clazz, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, clazz, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, clazz, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, clazz, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(message, eventTypeMask, clazz, false, timeout, timeUnit);
    }

    public static void assertServiceEvent(int eventTypeMask, Class clazz, boolean all, long timeoutInMills) {
        assertServiceEvent(null, eventTypeMask, clazz, all, timeoutInMills);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, boolean all, long timeoutInMills) {
        assertServiceEvent(message, eventTypeMask, clazz, all, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertServiceEvent(int eventTypeMask, Class clazz, boolean all, long timeout, TimeUnit timeUnit) {
        assertServiceEvent(null, eventTypeMask, clazz, all, timeout, timeUnit);
    }

    public static void assertServiceEvent(String message, int eventTypeMask, Class clazz, boolean all, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Class is null", clazz);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        try {
            ServiceEvent event = ServiceUtils.waitForServiceEvent(getBundleContext(), clazz, eventTypeMask, all, timeout, timeUnit);
            Assert.assertNotNull(message, event);
        } catch (InvalidSyntaxException e) {
            Assert.fail(e.getMessage());
        }
    }
}
