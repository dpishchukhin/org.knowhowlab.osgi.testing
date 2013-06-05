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

package org.knowhowlab.osgi.testing.assertions.cmpn;

import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.osgi.framework.Filter;
import org.osgi.service.event.Event;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.knowhowlab.osgi.testing.utils.cmpn.EventAdminUtils.waitForEvent;

/**
 * A set of OSGi EventAdmin specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class EventAdminAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private EventAdminAssert() {
    }

    /**
     * Asserts that Event for defined topic will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topic           topic
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String topic, long timeoutInMillis) {
        assertEvent(format("Event is unavailable: topic: %s within timeout: %sms", topic, timeoutInMillis), topic, timeoutInMillis);
    }

    /**
     * Asserts that Event for defined topic will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param topic           topic
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String message, String topic, long timeoutInMillis) {
        assertEvent(message, topic, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that Event for defined topic and filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topic           topic
     * @param filter          filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String topic, Filter filter, long timeoutInMillis) {
        assertEvent(format("Event is unavailable: topic: %s, filter: %s within timeout: %sms", topic, filter, timeoutInMillis),
                topic, filter, timeoutInMillis);
    }

    /**
     * Asserts that Event for defined topic and filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param topic           topic
     * @param filter          filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String message, String topic, Filter filter, long timeoutInMillis) {
        assertEvent(message, topic, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that Event for defined topics will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topics          topics
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String[] topics, long timeoutInMillis) {
        assertEvent(format("Event is unavailable: topics: %s within timeout: %sms", Arrays.toString(topics), timeoutInMillis), topics, timeoutInMillis);
    }

    /**
     * Asserts that Event for defined topics will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param topics          topics
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String message, String[] topics, long timeoutInMillis) {
        assertEvent(message, topics, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that Event for defined topics and filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topics          topics
     * @param filter          filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String[] topics, Filter filter, long timeoutInMillis) {
        assertEvent(format("Event is unavailable: topics: %s, filter: %s within timeout: %sms", Arrays.toString(topics), filter, timeoutInMillis),
                topics, filter, timeoutInMillis);
    }

    /**
     * Asserts that Event for defined topics and filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param topics          topics
     * @param filter          filter
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertEvent(String message, String[] topics, Filter filter, long timeoutInMillis) {
        assertEvent(message, topics, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that Event for defined topic will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topic    topic
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String topic, long timeout, TimeUnit timeUnit) {
        assertEvent(format("Event is unavailable: topic: %s within timeout: %s%s", topic, timeout, timeUnit), topic, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topic will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param topic    topic
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String message, String topic, long timeout, TimeUnit timeUnit) {
        assertEvent(message, topic, null, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topic and filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topic    topic
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String topic, Filter filter, long timeout, TimeUnit timeUnit) {
        assertEvent(format("Event is unavailable: topic: %s, filter: %s within timeout: %s%s", topic, filter, timeout, timeUnit),
                topic, filter, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topic and filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param topic    topic
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String message, String topic, Filter filter, long timeout, TimeUnit timeUnit) {
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        Event event = waitForEvent(getBundleContext(), topic, filter, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }

    /**
     * Asserts that Event for defined topics will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topics   topics
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String[] topics, long timeout, TimeUnit timeUnit) {
        assertEvent(format("Event is unavailable: topics: %s within timeout: %s%s", Arrays.toString(topics), timeout, timeUnit), topics, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topics will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param topics   topics
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String message, String[] topics, long timeout, TimeUnit timeUnit) {
        assertEvent(message, topics, null, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topics and filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown
     *
     * @param topics   topics
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String[] topics, Filter filter, long timeout, TimeUnit timeUnit) {
        assertEvent(format("Event is unavailable: topics: %s, filter: %s within timeout: %s%s", Arrays.toString(topics), filter, timeout, timeUnit),
                topics, filter, timeout, timeUnit);
    }

    /**
     * Asserts that Event for defined topics and filter will be fired within given timeout. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message  message
     * @param topics   topics
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @since 1.0
     */
    public static void assertEvent(String message, String[] topics, Filter filter, long timeout, TimeUnit timeUnit) {
        assertThat("TimeUnit is null", timeUnit, notNullValue());
        Event event = waitForEvent(getBundleContext(), topics, filter, timeout, timeUnit);
        assertThat(message, event, notNullValue());
    }
}
