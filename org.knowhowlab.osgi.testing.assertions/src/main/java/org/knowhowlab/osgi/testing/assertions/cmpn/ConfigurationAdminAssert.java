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
import org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.*;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.createConfigurationFilter;
import static org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils.waitForConfigurationEvent;
import static org.osgi.service.cm.ConfigurationEvent.CM_DELETED;
import static org.osgi.service.cm.ConfigurationEvent.CM_UPDATED;

/**
 * A set of OSGi ConfigurationAdmin specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class ConfigurationAdminAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private ConfigurationAdminAssert() {
    }

    /**
     * Asserts that Configuration with PID, FactoryPID and bundle location is available. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @since 1.0
     */
    public static void assertConfigurationAvailable(String pid, String factoryPid, String location) {
        assertConfigurationAvailable(format("Configuration is unavailable for PID: %s, FactoryPID: %s, Location: %s", pid, factoryPid, location),
                pid, factoryPid, location);
    }

    /**
     * Asserts that Configuration with PID, FactoryPID and bundle location is available. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message    message
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     */
    public static void assertConfigurationAvailable(String message, String pid, String factoryPid, String location) {
        assertConfigurationAvailable(message, createConfigurationFilter(pid, factoryPid, location));
    }

    /**
     * Asserts that Configuration by filter is available. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter filter
     * @since 1.0
     */
    public static void assertConfigurationAvailable(Filter filter) {
        assertConfigurationAvailable(format("Configuration is unavailable for Filter: %s", filter), filter);
    }

    /**
     * Asserts that Configuration by filter is available. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message message
     * @param filter  filter
     * @since 1.0
     */
    public static void assertConfigurationAvailable(String message, Filter filter) {
        try {
            Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(getBundleContext(), filter);
            assertNotNull(message, configurations);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Asserts that Configuration with PID, FactoryPID and bundle location is unavailable. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @since 1.0
     */
    public static void assertConfigurationUnavailable(String pid, String factoryPid, String location) {
        assertConfigurationUnavailable(format("Configuration is available for PID: %s, FactoryPID: %s, Location: %s", pid, factoryPid, location),
                pid, factoryPid, location);
    }

    /**
     * Asserts that Configuration with PID, FactoryPID and bundle location is unavailable. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message    message
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @since 1.0
     */
    public static void assertConfigurationUnavailable(String message, String pid, String factoryPid, String location) {
        assertConfigurationUnavailable(message, createConfigurationFilter(pid, factoryPid, location));
    }

    /**
     * Asserts that Configuration by filter is unavailable. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter filter
     * @since 1.0
     */
    public static void assertConfigurationUnavailable(Filter filter) {
        assertConfigurationUnavailable(format("Configuration is unavailable for Filter: %s", filter), filter);
    }

    /**
     * Asserts that Configuration by filter is available. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message message
     * @param filter  filter
     * @since 1.0
     */
    public static void assertConfigurationUnavailable(String message, Filter filter) {
        try {
            Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(getBundleContext(), filter);
            assertNull(message, configurations);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Asserts that Configuration with PID, FactoryPID is available for specific Bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bc         BundleContext
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @since 1.1
     */
    public static void assertConfigurationAvailable(BundleContext bc, String pid, String factoryPid) {
        assertNotNull("BundleContext is null", bc);
        assertConfigurationAvailable(format("Configuration is unavailable for BundleId: %s, PID: %s, FactoryPID: %s", bc.getBundle().getBundleId(), pid, factoryPid), bc, pid, factoryPid);
    }

    /**
     * Asserts that Configuration with PID, FactoryPID is available for specific Bundle. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message    message
     * @param bc         BundleContext
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @since 1.1
     */
    public static void assertConfigurationAvailable(String message, BundleContext bc, String pid, String factoryPid) {
        assertConfigurationAvailable(message, bc, createConfigurationFilter(pid, factoryPid, bc.getBundle().getLocation()));
    }

    /**
     * Asserts that Configuration by filter is available for specific Bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bc     BundleContext
     * @param filter filter
     * @since 1.1
     */
    public static void assertConfigurationAvailable(BundleContext bc, Filter filter) {
        assertNotNull("BundleContext is null", bc);
        assertConfigurationAvailable(format("Configuration is unavailable for BundleId: %s, Filter: %s", bc.getBundle().getBundleId(), filter), bc, filter);
    }

    /**
     * Asserts that Configuration by filter is available for specific Bundle. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message message
     * @param bc      BundleContext
     * @param filter  filter
     * @since 1.1
     */
    public static void assertConfigurationAvailable(String message, BundleContext bc, Filter filter) {
        assertNotNull("BundleContext is null", bc);
        try {
            Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(bc, filter);
            assertNotNull(message, configurations);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Asserts that Configuration with PID, FactoryPID is unavailable for specific Bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bc         BundleContext
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @since 1.1
     */
    public static void assertConfigurationUnavailable(BundleContext bc, String pid, String factoryPid) {
        assertNotNull("BundleContext is null", bc);
        assertConfigurationUnavailable(format("Configuration is available for BundleId: %s, PID: %s, FactoryPID: %s", bc.getBundle().getBundleId(), pid, factoryPid), bc, pid, factoryPid);
    }

    /**
     * Asserts that Configuration with PID, FactoryPID is unavailable for specific Bundle. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param message    message
     * @param bc         BundleContext
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @since 1.1
     */
    public static void assertConfigurationUnavailable(String message, BundleContext bc, String pid, String factoryPid) {
        assertConfigurationUnavailable(message, bc, createConfigurationFilter(pid, factoryPid, bc.getBundle().getLocation()));
    }

    /**
     * Asserts that Configuration by filter is unavailable for specific Bundle. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param bc     BundleContext
     * @param filter filter
     * @since 1.1
     */
    public static void assertConfigurationUnavailable(BundleContext bc, Filter filter) {
        assertNotNull("BundleContext is null", bc);
        assertConfigurationUnavailable(format("Configuration is unavailable for BundleId: %s, Filter: %s", bc.getBundle().getBundleId(), filter), bc, filter);
    }

    /**
     * Asserts that Configuration by filter is unavailable for specific Bundle. If it not as expected
     * {@link AssertionError} with the given message
     *
     * @param bc      BundleContext
     * @param message message
     * @param filter  filter
     * @since 1.1
     */
    public static void assertConfigurationUnavailable(String message, BundleContext bc, Filter filter) {
        assertNotNull("BundleContext is null", bc);
        try {
            Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(bc, filter);
            assertNull(message, configurations);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Asserts that ConfigurationEvent with event type will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationEvent(int eventTypeMask, long timeoutInMillis) {
        assertConfigurationEvent(format("ConfigurationEvent is unavailable: %s within timeout: %sms", eventTypeMask, timeoutInMillis), eventTypeMask, timeoutInMillis);
    }

    /**
     * Asserts that ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param eventTypeMask   ServiceEvent type mask
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationEvent(int eventTypeMask, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationEvent(format("ConfigurationEvent is unavailable: %s for PID: %s, FactoryPID: %s, Location: %s within timeout: %sms",
                eventTypeMask, pid, factoryPid, location, timeoutInMillis), eventTypeMask, pid, factoryPid, location, timeoutInMillis);
    }

    /**
     * Asserts that UPDATE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationUpdated(String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationUpdated(format("ConfigurationEvent is unavailable: CM_UPDATED for PID: %s, FactoryPID: %s, Location: %s within timeout: %sms",
                pid, factoryPid, location, timeoutInMillis), pid, factoryPid, location, timeoutInMillis);
    }

    /**
     * Asserts that DELETE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationDeleted(String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationDeleted(format("ConfigurationEvent is unavailable: CM_DELETED for PID: %s, FactoryPID: %s, Location: %s within timeout: %sms",
                pid, factoryPid, location, timeoutInMillis), pid, factoryPid, location, timeoutInMillis);
    }

    /**
     * Asserts that ConfigurationEvent with event type will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationEvent(String message, int eventTypeMask, long timeoutInMillis) {
        assertConfigurationEvent(message, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param eventTypeMask   ServiceEvent type mask
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationEvent(String message, int eventTypeMask, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationEvent(message, eventTypeMask, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that UPDATE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationUpdated(String message, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationUpdated(message, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that DELETE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message         message
     * @param pid             PID
     * @param factoryPid      FactoryPID
     * @param location        bundle location
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @since 1.0
     */
    public static void assertConfigurationDeleted(String message, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationDeleted(message, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Asserts that ConfigurationEvent with event type will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationEvent(int eventTypeMask, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationEvent(format("ConfigurationEvent is unavailable: %s within timeout: %s%s", eventTypeMask, timeout, timeUnit),
                eventTypeMask, null, null, null, timeout, timeUnit);
    }

    /**
     * Asserts that ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param eventTypeMask ServiceEvent type mask
     * @param pid           PID
     * @param factoryPid    FactoryPID
     * @param location      bundle location
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationEvent(int eventTypeMask, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationEvent(format("ConfigurationEvent is unavailable: %s for PID: %s, FactoryPID: %s, Location: %s within timeout: %s%s",
                eventTypeMask, pid, factoryPid, location, timeout, timeUnit), eventTypeMask, pid, factoryPid, location, timeout, timeUnit);
    }

    /**
     * Asserts that UPDATE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @param timeout    time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit   time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationUpdated(String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationUpdated(format("ConfigurationEvent is unavailable: CM_UPDATED for PID: %s, FactoryPID: %s, Location: %s within timeout: %s%s",
                pid, factoryPid, location, timeout, timeUnit), pid, factoryPid, location, timeout, timeUnit);
    }

    /**
     * Asserts that DELETE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @param timeout    time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit   time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationDeleted(String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationDeleted(format("ConfigurationEvent is unavailable: CM_DELETED for PID: %s, FactoryPID: %s, Location: %s within timeout: %s%s",
                pid, factoryPid, location, timeout, timeUnit), pid, factoryPid, location, timeout, timeUnit);
    }

    /**
     * Asserts that ConfigurationEvent with event type will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationEvent(String message, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationEvent(message, eventTypeMask, null, null, null, timeout, timeUnit);
    }

    /**
     * Asserts that ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message       message
     * @param eventTypeMask ServiceEvent type mask
     * @param pid           PID
     * @param factoryPid    FactoryPID
     * @param location      bundle location
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationEvent(String message, int eventTypeMask, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        ConfigurationEvent event = waitForConfigurationEvent(getBundleContext(), eventTypeMask, pid, factoryPid, location, timeout, timeUnit);
        assertNotNull(message, event);
    }

    /**
     * Asserts that UPDATE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message    message
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @param timeout    time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit   time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationUpdated(String message, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationEvent(message, CM_UPDATED, pid, factoryPid, location, timeout, timeUnit);
    }

    /**
     * Asserts that DELETE ConfigurationEvent with event filter will be fired within given timeoutInMillis. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message    message
     * @param pid        PID
     * @param factoryPid FactoryPID
     * @param location   bundle location
     * @param timeout    time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit   time unit for the time interval
     * @since 1.0
     */
    public static void assertConfigurationDeleted(String message, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertNotNull("TimeUnit is null", timeUnit);
        assertConfigurationEvent(message, CM_DELETED, pid, factoryPid, location, timeout, timeUnit);
    }

}
