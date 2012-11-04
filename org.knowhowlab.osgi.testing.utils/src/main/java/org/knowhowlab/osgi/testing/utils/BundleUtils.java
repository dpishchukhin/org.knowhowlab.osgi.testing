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

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.Version;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * OSGi Bundles utilities class
 *
 * @author dpishchukhin
 * @version 1.0
 * @see org.osgi.framework.Bundle
 * @see org.osgi.service.packageadmin.PackageAdmin
 * @see org.osgi.framework.BundleContext
 */
public class BundleUtils {
    /**
     * Bundle ANY state mask
     */
    public static final int ANY_STATE = Bundle.UNINSTALLED |
            Bundle.INSTALLED | Bundle.RESOLVED | Bundle.STARTING | Bundle.STOPPING | Bundle.ACTIVE;

    /**
     * Utility class. Only static methods are available.
     */
    private BundleUtils() {
    }

    /**
     * Find bundle by ID
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, long bundleId) {
        return bc.getBundle(bundleId);
    }

    /**
     * Find bundle by SymbolicName
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName) {
        return findBundle(bc, symbolicName, null);
    }

    /**
     * Find bundle by SymbolicName and Version
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version) {
        PackageAdmin packageAdmin = ServiceUtils.getService(bc, PackageAdmin.class);
        if (packageAdmin != null) {
            Bundle[] bundles = packageAdmin.getBundles(symbolicName, version != null ? version.toString() : null);
            if (bundles != null && bundles.length > 0) {
                return bundles[0];
            }
        }
        return null;
    }

    /**
     * Find bundle by SymbolicName and Version within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, long timeoutInMillis) {
        return findBundle(bc, symbolicName, version, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Find bundle by SymbolicName and Version and stateMask within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version
     * @param stateMask       The bit mask of the ORing of the bundle states to be tracked.
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, int stateMask, long timeoutInMillis) {
        return findBundle(bc, symbolicName, version, stateMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Find bundle by SymbolicName within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, long timeoutInMillis) {
        return findBundle(bc, symbolicName, null, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Find bundle by SymbolicName and stateMask within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param stateMask       The bit mask of the ORing of the bundle states to be tracked.
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, int stateMask, long timeoutInMillis) {
        return findBundle(bc, symbolicName, null, stateMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Find bundle by SymbolicName and Version within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, long timeout, TimeUnit timeUnit) {
        return findBundle(bc, symbolicName, null, timeout, timeUnit);
    }

    /**
     * Find bundle by SymbolicName and Version and stateMask within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, int stateMask, long timeout, TimeUnit timeUnit) {
        return findBundle(bc, symbolicName, null, stateMask, timeout, timeUnit);
    }

    /**
     * Find bundle by SymbolicName and Version within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, long timeout, TimeUnit timeUnit) {
        return findBundle(bc, symbolicName, version, ANY_STATE, timeout, timeUnit);
    }

    /**
     * Find bundle by SymbolicName and Version and stateMask within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param stateMask    The bit mask of the ORing of the bundle states to be tracked.
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, int stateMask, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        BundleTracker tracker = new BundleTracker(bc, stateMask,
                new SymbolicNameVersionBundleTrackerCustomizer(bc, latch, symbolicName, version));
        tracker.open();
        try {
            return waitForBundle(tracker, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Wait for at least one Bundle to be tracked by BundleTracker
     *
     * @param tracker         BundleTracker
     * @param timeoutInMillis time interval in milliseconds to wait.
     *                        If zero, the method will wait indefinitely.
     * @param latch           external latch that is used to handle new service adding to ServiceTracker
     * @return Bundle instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative.
     * @throws InterruptedException     If another thread has interrupted the current thread.
     */
    private static Bundle waitForBundle(BundleTracker tracker, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        Bundle[] bundles = tracker.getBundles();
        if (bundles == null) {
            if (latch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
                bundles = tracker.getBundles();
                return bundles == null ? null : bundles[0];
            } else {
                return null;
            }
        } else {
            // returns first bundle
            return bundles[0];
        }
    }


    /**
     * BundleTrackerCustomizer with latch support.
     *
     * @see java.util.concurrent.locks.ReentrantLock
     * @see org.osgi.util.tracker.BundleTrackerCustomizer
     */
    private abstract static class BundleTrackerCustomizerWithLock implements BundleTrackerCustomizer {
        protected final BundleContext bc;
        private CountDownLatch latch;

        public BundleTrackerCustomizerWithLock(BundleContext bc, CountDownLatch latch) {
            this.bc = bc;
            this.latch = latch;
        }

        public Object addingBundle(Bundle bundle, BundleEvent event) {
            boolean found = false;
            try {
                Object trackedBundle = isTrackedBundle(bundle, event);
                found = trackedBundle != null;
                return trackedBundle;
            } finally {
                // unlock the latch
                if (found) {
                    latch.countDown();
                }
            }
        }

        protected abstract Object isTrackedBundle(Bundle bundle, BundleEvent event);

        public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
        }

        public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
        }
    }

    private static class SymbolicNameVersionBundleTrackerCustomizer extends BundleTrackerCustomizerWithLock {
        private String symbolicName;
        private Version version;

        public SymbolicNameVersionBundleTrackerCustomizer(BundleContext bc, CountDownLatch latch, String symbolicName, Version version) {
            super(bc, latch);
            this.symbolicName = symbolicName;
            this.version = version;
        }

        @Override
        protected Object isTrackedBundle(Bundle bundle, BundleEvent event) {
            PackageAdmin packageAdmin = ServiceUtils.getService(bc, PackageAdmin.class);
            if (packageAdmin != null) {
                Bundle[] bundles = packageAdmin.getBundles(symbolicName, version != null ? version.toString() : null);
                if (bundles != null && bundles.length > 0) {
                    return bundles[0];
                }
            }
            return null;
        }
    }
}
