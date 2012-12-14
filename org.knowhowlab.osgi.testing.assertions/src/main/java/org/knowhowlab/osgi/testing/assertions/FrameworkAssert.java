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
import org.knowhowlab.osgi.testing.utils.FrameworkUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.Version;

import java.util.concurrent.TimeUnit;

/**
 * @author dpishchukhin
 */
public class FrameworkAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private FrameworkAssert() {
    }

    public static void assertFrameworkEvent(int eventTypeMask, int bundleId, long timeoutInMills) {
        assertFrameworkEvent(null, eventTypeMask, bundleId, timeoutInMills);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, int bundleId, long timeoutInMills) {
        assertFrameworkEvent(message, eventTypeMask, bundleId, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertFrameworkEvent(int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(null, eventTypeMask, bundleId, timeout, timeUnit);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, int bundleId, long timeout, TimeUnit timeUnit) {
        Bundle bundle = BundleUtils.findBundle(getBundleContext(), bundleId);
        Assert.assertNotNull(String.format("Unknown bundle with ID: %d", bundleId), bundle);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        FrameworkEvent event = FrameworkUtils.waitForFrameworkEvent(getBundleContext(), bundleId, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }

    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, long timeoutInMills) {
        assertFrameworkEvent(null, eventTypeMask, symbolicName, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, long timeoutInMills) {
        assertFrameworkEvent(eventTypeMask, symbolicName, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(null, eventTypeMask, symbolicName, timeout, timeUnit);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        FrameworkEvent event = FrameworkUtils.waitForFrameworkEvent(getBundleContext(), symbolicName, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }

    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, Version version, long timeoutInMills) {
        assertFrameworkEvent(eventTypeMask, symbolicName, version, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeoutInMills) {
        assertFrameworkEvent(message, eventTypeMask, symbolicName, version, timeoutInMills, TimeUnit.MILLISECONDS);
    }

    public static void assertFrameworkEvent(int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        assertFrameworkEvent(null, eventTypeMask, symbolicName, version, timeout, timeUnit);
    }

    public static void assertFrameworkEvent(String message, int eventTypeMask, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        Assert.assertNotNull("SymbolicName is null", symbolicName);
        Assert.assertNotNull("TimeUnit is null", timeUnit);
        FrameworkEvent event = FrameworkUtils.waitForFrameworkEvent(getBundleContext(), symbolicName, version, eventTypeMask, timeout, timeUnit);
        Assert.assertNotNull(message, event);
    }
}
