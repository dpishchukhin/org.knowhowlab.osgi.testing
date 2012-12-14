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

import org.osgi.framework.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * OSGi Framework utilities class
 *
 * @version 1.1
 * @see org.osgi.framework.BundleContext
 * @see org.osgi.framework.FrameworkListener
 * @see org.osgi.framework.FrameworkEvent
 */
public class FrameworkUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private FrameworkUtils() {
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param bundleId        bundle id
     * @param eventTypeMask   FrameworkEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException     If <code>bc</code> is <code>null</code>
     * @throws IllegalArgumentException If <code>bundleId</code> is invalid
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, bundleId, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param eventTypeMask   FrameworkEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, symbolicName, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version
     * @param eventTypeMask   FrameworkEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeoutInMillis) {
        return waitForFrameworkEvent(bc, symbolicName, version, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param eventTypeMask FrameworkEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException     If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @throws IllegalArgumentException If <code>bundleId</code> is invalid
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        Bundle bundle = BundleUtils.findBundle(bc, bundleId);
        if (bundle == null) {
            throw new IllegalArgumentException("bundleId is invalid");
        }
        return waitForFrameworkEvent(bc, bundle.getSymbolicName(), bundle.getVersion(), eventTypeMask, timeout, timeUnit);
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param eventTypeMask FrameworkEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return waitForFrameworkEvent(bc, symbolicName, null, eventTypeMask, timeout, timeUnit);
    }

    /**
     * Wait for FrameworkEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param eventTypeMask FrameworkEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return FrameworkEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static FrameworkEvent waitForFrameworkEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        FrameworkListenerImpl listener = new FrameworkListenerImpl(symbolicName, version, eventTypeMask, latch);
        bc.addFrameworkListener(listener);

        try {
            return waitForFrameworkEvent(listener, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            bc.removeFrameworkListener(listener);
        }
    }

    private static FrameworkEvent waitForFrameworkEvent(FrameworkListenerImpl listener, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (latch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
            return listener.getFrameworkEvent();
        } else {
            return null;
        }
    }

    private static class FrameworkListenerImpl implements FrameworkListener {
        private String symbolicName;
        private Version version;
        private int eventTypeMask;
        private CountDownLatch latch;

        private FrameworkEvent event;

        public FrameworkListenerImpl(String symbolicName, Version version, int eventTypeMask, CountDownLatch latch) {
            this.symbolicName = symbolicName;
            this.version = version;
            this.eventTypeMask = eventTypeMask;
            this.latch = latch;
        }

        public void frameworkEvent(FrameworkEvent event) {
            if (match(event)) {
                this.event = event;
                latch.countDown();
            }
        }

        private boolean match(FrameworkEvent event) {
            Bundle bundle = event.getBundle();
            return bundle.getSymbolicName().equals(symbolicName)
                    && (version == null || bundle.getVersion().equals(version))
                    && (eventTypeMask & event.getType()) != 0;
        }

        public FrameworkEvent getFrameworkEvent() {
            return event;
        }
    }
}
