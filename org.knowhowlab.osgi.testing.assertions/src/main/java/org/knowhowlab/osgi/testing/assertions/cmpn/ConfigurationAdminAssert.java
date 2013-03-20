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

import junit.framework.Assert;
import org.knowhowlab.osgi.testing.assertions.OSGiAssert;
import org.knowhowlab.osgi.testing.utils.cmpn.ConfigurationAdminUtils;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static junit.framework.Assert.assertNotNull;
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
 * @version 1.0
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class ConfigurationAdminAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private ConfigurationAdminAssert() {
    }

    public static void assertConfigurationAvailable(String pid, String factoryPid, String location) throws IOException, InvalidSyntaxException {
        assertConfigurationAvailable(null, pid, factoryPid, location);
    }

    public static void assertConfigurationAvailable(String message, String pid, String factoryPid, String location) throws IOException, InvalidSyntaxException {
        assertConfigurationAvailable(message, createConfigurationFilter(pid, factoryPid, location));
    }

    public static void assertConfigurationAvailable(Filter filter) throws IOException, InvalidSyntaxException {
        assertConfigurationAvailable(null, filter);
    }

    public static void assertConfigurationAvailable(String message, Filter filter) throws IOException, InvalidSyntaxException {
        Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(getBundleContext(), filter);
        Assert.assertNotNull(message, configurations);
    }

    public static void assertConfigurationUnavailable(String pid, String factoryPid, String location) throws IOException, InvalidSyntaxException {
        assertConfigurationUnavailable(null, pid, factoryPid, location);
    }

    public static void assertConfigurationUnavailable(String message, String pid, String factoryPid, String location) throws IOException, InvalidSyntaxException {
        assertConfigurationUnavailable(message, createConfigurationFilter(pid, factoryPid, location));
    }

    public static void assertConfigurationUnavailable(Filter filter) throws IOException, InvalidSyntaxException {
        assertConfigurationUnavailable(null, filter);
    }

    public static void assertConfigurationUnavailable(String message, Filter filter) throws IOException, InvalidSyntaxException {
        Configuration[] configurations = ConfigurationAdminUtils.listConfigurations(getBundleContext(), filter);
        Assert.assertNull(message, configurations);
    }

    public static void assertConfigurationEvent(int eventTypeMask, long timeoutInMillis) {
        assertConfigurationEvent(null, eventTypeMask, timeoutInMillis);
    }

    public static void assertConfigurationEvent(int eventTypeMask, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationEvent(null, eventTypeMask, pid, factoryPid, location, timeoutInMillis);
    }

    public static void assertConfigurationUpdated(String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationUpdated(null, pid, factoryPid, location, timeoutInMillis);
    }

    public static void assertConfigurationDeleted(String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationDeleted(null, pid, factoryPid, location, timeoutInMillis);
    }

    public static void assertConfigurationEvent(String message, int eventTypeMask, long timeoutInMillis) {
        assertConfigurationEvent(message, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    public static void assertConfigurationEvent(String message, int eventTypeMask, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationEvent(message, eventTypeMask, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    public static void assertConfigurationUpdated(String message, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationUpdated(message, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    public static void assertConfigurationDeleted(String message, String pid, String factoryPid, String location, long timeoutInMillis) {
        assertConfigurationDeleted(message, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    public static void assertConfigurationEvent(int eventTypeMask, long timeout, TimeUnit timeUnit) {
        assertConfigurationEvent(null, eventTypeMask, null, null, null, timeout, timeUnit);
    }

    public static void assertConfigurationEvent(int eventTypeMask, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertConfigurationEvent(null, eventTypeMask, pid, factoryPid, location, timeout, timeUnit);
    }

    public static void assertConfigurationUpdated(String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertConfigurationUpdated(null, pid, factoryPid, location, timeout, timeUnit);
    }

    public static void assertConfigurationDeleted(String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertConfigurationDeleted(null, pid, factoryPid, location, timeout, timeUnit);
    }

    public static void assertConfigurationEvent(String message, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        assertConfigurationEvent(message, eventTypeMask, null, null, null, timeout, timeUnit);
    }

    public static void assertConfigurationEvent(String message, int eventTypeMask, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        ConfigurationEvent event = waitForConfigurationEvent(getBundleContext(), eventTypeMask, pid, factoryPid, location, timeout, timeUnit);
        assertNotNull(message, event);
    }

    public static void assertConfigurationUpdated(String message, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertConfigurationEvent(message, CM_UPDATED, pid, factoryPid, location, timeout, timeUnit);
    }

    public static void assertConfigurationDeleted(String message, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        assertConfigurationEvent(message, CM_DELETED, pid, factoryPid, location, timeout, timeUnit);
    }

}
