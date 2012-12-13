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

package org.knowhowlab.osgi.testing.utils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.Version;

import java.util.concurrent.TimeUnit;

/**
 * OSGi Framework utilities class
 * @version 1.1
 * @see org.osgi.framework.BundleContext
 * @see org.osgi.framework.FrameworkListener
 * @see org.osgi.framework.FrameworkEvent
 */
public class FrameworkUtils {
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, bundleId, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, symbolicName, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, symbolicName, version, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return null; // todo
    }

    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return null; // todo
    }

    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return null; // todo
    }

}
