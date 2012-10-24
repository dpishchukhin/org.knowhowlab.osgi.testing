/*
 * Copyright (c) 2010 Dmytro Pishchukhin (http://knowhowlab.org)
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

import junit.framework.Assert;
import org.osgi.framework.Filter;
import org.knowhowlab.osgi.testing.utils.ServiceUtils;

import java.util.concurrent.TimeUnit;

/**
 * A set of OSGi services specific assertion methods useful for writing tests.
 * <p/>
 * Before use it should be initialized
 * {@link OSGiAssert#init(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.0
 * @see java.lang.AssertionError
 * @see OSGiAssert
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
     * @param clazz service class
     */
    public static void assertServiceAvailable(String message, Class clazz) {
        Assert.assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(Class clazz, long timeout) {
        assertServiceAvailable(null, clazz, timeout);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(String message, Class clazz, long timeout) {
        assertServiceAvailable(message, clazz, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
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
     * @param message message
     * @param className service class name
     */
    public static void assertServiceAvailable(String message, String className) {
        Assert.assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(String className, long timeout) {
        assertServiceAvailable(null, className, timeout);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(String message, String className, long timeout) {
        assertServiceAvailable(message, className, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
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
     * @param filter service filter
     */
    public static void assertServiceAvailable(String message, Filter filter) {
        Assert.assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter);
        Assert.assertNotNull(message, service);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(Filter filter, long timeout) {
        assertServiceAvailable(null, filter, timeout);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceAvailable(String message, Filter filter, long timeout) {
        assertServiceAvailable(message, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceAvailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceAvailable(null, filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is available in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
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
     * @param clazz service class
     */
    public static void assertServiceUnavailable(String message, Class clazz) {
        Assert.assertNotNull("Class is null", clazz);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), clazz);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(Class clazz, long timeout) {
        assertServiceUnavailable(null, clazz, timeout);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(String message, Class clazz, long timeout) {
        assertServiceUnavailable(message, clazz, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(Class clazz, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, clazz, timeout, timeUnit);
    }

    /**
     * Asserts that service with class is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param clazz service class
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
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
     * @param message message
     * @param className service class name
     */
    public static void assertServiceUnavailable(String message, String className) {
        Assert.assertNotNull("Class name is null", className);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), className);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(String className, long timeout) {
        assertServiceUnavailable(null, className, timeout);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(String message, String className, long timeout) {
        assertServiceUnavailable(message, className, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(String className, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, className, timeout, timeUnit);
    }

    /**
     * Asserts that service with class name is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param className service class name
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
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
     * @param filter service filter
     */
    public static void assertServiceUnavailable(String message, Filter filter) {
        Assert.assertNotNull("Filter is null", filter);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter);
        Assert.assertNull(message, service);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(Filter filter, long timeout) {
        assertServiceUnavailable(null, filter, timeout);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeout) {
        assertServiceUnavailable(message, filter, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(Filter filter, long timeout, TimeUnit timeUnit) {
        assertServiceUnavailable(null, filter, timeout, timeUnit);
    }

    /**
     * Asserts that service with filter is unavailable in OSGi registry within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter service filter
     * @param timeout time interval in milliseconds to wait. If zero, the method will wait indefinately.
     * @param timeUnit timeout time unit
     */
    public static void assertServiceUnavailable(String message, Filter filter, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("Filter is null", filter);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        //noinspection unchecked
        Object service = ServiceUtils.getService(getBundleContext(), filter, timeout, timeUnit);
        Assert.assertNull(message, service);
    }
}
