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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
     * Find bundle by SymbolicName and Version and state within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version
     * @param state           Bundle state
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, int state, long timeoutInMillis) {
        return findBundle(bc, symbolicName, version, state, timeoutInMillis, TimeUnit.MILLISECONDS);
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
     * Find bundle by SymbolicName and state within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param state           Bundle state
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, int state, long timeoutInMillis) {
        return findBundle(bc, symbolicName, null, state, timeoutInMillis, TimeUnit.MILLISECONDS);
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
     * Find bundle by SymbolicName and Version and state within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param state        Bundle state
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, int state, long timeout, TimeUnit timeUnit) {
        return findBundle(bc, symbolicName, null, state, timeout, timeUnit);
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
     * Find bundle by SymbolicName and Version and state within timeout
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param state        Bundle state
     * @param timeout      time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit     time unit for the time interval
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, int state, long timeout, TimeUnit timeUnit) {
        ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        long timeoutInMillis = timeUnit.toMillis(timeout);
        BundleTracker tracker = new BundleTracker(bc, state,
                new SymbolicNameVersionBundleTrackerCustomizer(bc, lock, condition, symbolicName, version));
        tracker.open();
        try {
            lock.lock();
            return waitForBundle(tracker, timeoutInMillis, condition);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
            lock.unlock();
        }
    }

    /**
     * Wait for at least one Bundle to be tracked by BundleTracker
     *
     *
     * @param tracker         BundleTracker
     * @param timeoutInMillis time interval in milliseconds to wait.
     *                        If zero, the method will wait indefinitely.
     * @param lock            external lock that is used to handle new service adding to ServiceTracker
     * @return Bundle instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative.
     * @throws InterruptedException     If another thread has interrupted the current thread.
     */
    private static Bundle waitForBundle(BundleTracker tracker, long timeoutInMillis, Condition lock)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        Bundle[] bundles = tracker.getBundles();
        if (bundles == null) {
            lock.await(timeoutInMillis, TimeUnit.MILLISECONDS);
            bundles = tracker.getBundles();
            return bundles == null ? null : bundles[0];
        } else {
            // returns first bundle
            return bundles[0];
        }
    }


    /**
     * BundleTrackerCustomizer with lock support.
     *
     * @see java.util.concurrent.locks.ReentrantLock
     * @see org.osgi.util.tracker.BundleTrackerCustomizer
     */
    private abstract static class BundleTrackerCustomizerWithLock implements BundleTrackerCustomizer {
        protected final BundleContext bc;
        private ReentrantLock lock;
        private final Condition condition;

        public BundleTrackerCustomizerWithLock(BundleContext bc, ReentrantLock lock, Condition condition) {
            this.bc = bc;
            this.lock = lock;
            this.condition = condition;
        }

        public Object addingBundle(Bundle bundle, BundleEvent event) {
            try {
                lock.lock();

                boolean found = false;
                try {
                    Object trackedBundle = isTrackedBundle(bundle, event);
                    found = trackedBundle != null;
                    return trackedBundle;
                } finally {
                    // unlock the lock
                    if (found) {
                        condition.signalAll();
                    }
                }
            } finally {
                lock.unlock();
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

        public SymbolicNameVersionBundleTrackerCustomizer(BundleContext bc, ReentrantLock lock,Condition condition, String symbolicName, Version version) {
            super(bc, lock, condition);
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
