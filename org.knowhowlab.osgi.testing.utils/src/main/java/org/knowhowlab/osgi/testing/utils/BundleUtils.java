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

package org.knowhowlab.osgi.testing.utils;

import org.osgi.framework.*;
import org.osgi.service.packageadmin.PackageAdmin;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

import java.io.InputStream;
import java.util.concurrent.*;

/**
 * OSGi Bundles utilities class
 *
 * @author dpishchukhin
 * @version 1.1
 * @see org.osgi.framework.Bundle
 * @see org.osgi.service.packageadmin.PackageAdmin
 * @see org.osgi.framework.BundleContext
 * @see org.osgi.framework.BundleEvent
 * @see org.osgi.framework.BundleListener
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
     */
    public static Bundle findBundle(BundleContext bc, String symbolicName, Version version, long timeoutInMillis) {
        return findBundle(bc, symbolicName, version, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Find bundle by SymbolicName and Version and stateMask within timeoutInMillis
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version     * @param stateMask       The bit mask of the ORing of the bundle states to be tracked.
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return Bundle instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     * Installs a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param location The location identifier of the bundle to install.
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(BundleContext bc, String location) {
        return installBundleAsync(bc, location, 0);
    }

    /**
     * Installs a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param location The location identifier of the bundle to install.
     * @param input    The <code>InputStream</code> object from which this bundle
     *                 will be read or <code>null</code> to indicate the Framework must
     *                 create the input stream from the specified location identifier.
     *                 The input stream must always be closed when this method completes,
     *                 even if an exception is thrown.
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(BundleContext bc, String location, InputStream input) {
        return installBundleAsync(bc, location, input, 0);
    }

    /**
     * Installs a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param location      The location identifier of the bundle to install.
     * @param delayInMillis time interval in millis to wait before installation. If zero, the method will not wait.
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(BundleContext bc, String location, long delayInMillis) {
        return installBundleAsync(bc, location, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Installs a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param location      The location identifier of the bundle to install.
     * @param input         The <code>InputStream</code> object from which this bundle
     *                      will be read or <code>null</code> to indicate the Framework must
     *                      create the input stream from the specified location identifier.
     *                      The input stream must always be closed when this method completes,
     *                      even if an exception is thrown.
     * @param delayInMillis time interval in millis to wait before installation. If zero, the method will not wait.
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(BundleContext bc, String location, InputStream input, long delayInMillis) {
        return installBundleAsync(bc, location, input, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Installs a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param location The location identifier of the bundle to install.
     * @param delay    time interval to wait before installation. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(BundleContext bc, String location, long delay, TimeUnit timeUnit) {
        return installBundleAsync(bc, location, null, delay, timeUnit);
    }

    /**
     * Installs a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param location The location identifier of the bundle to install.
     * @param input    The <code>InputStream</code> object from which this bundle
     *                 will be read or <code>null</code> to indicate the Framework must
     *                 create the input stream from the specified location identifier.
     *                 The input stream must always be closed when this method completes,
     *                 even if an exception is thrown.
     * @param delay    time interval to wait before installation. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return A <code>Future&lt;Bundle&gt;</code> object of the installed bundle.
     * @since 1.1
     */
    public static Future<Bundle> installBundleAsync(final BundleContext bc, final String location, final InputStream input, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Bundle>() {
            public Bundle call() throws Exception {
                return bc.installBundle(location, input);
            }
        }, delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bundle Bundle
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(Bundle bundle) {
        return startBundleAsync(bundle, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId) {
        return startBundleAsync(bc, bundleId, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName) {
        return startBundleAsync(bc, symbolicName, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version) {
        return startBundleAsync(bc, symbolicName, version, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bundle  Bundle
     * @param options The options for starting this bundle. See
     *                {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                Framework must ignore unrecognized options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(Bundle bundle, int options) {
        return startBundleAsync(bundle, options, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param options  The options for starting this bundle. See
     *                 {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                 Framework must ignore unrecognized options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId, int options) {
        return startBundleAsync(bc, bundleId, options, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param options      The options for starting this bundle. See
     *                     {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                     Framework must ignore unrecognized options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, int options) {
        return startBundleAsync(bc, symbolicName, options, 0L);
    }

    /**
     * Starts a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param options      The options for starting this bundle. See
     *                     {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                     Framework must ignore unrecognized options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version, int options) {
        return startBundleAsync(bc, symbolicName, version, options, 0L);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(Bundle bundle, long delayInMillis) {
        return startBundleAsync(bundle, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId, long delayInMillis) {
        return startBundleAsync(bc, bundleId, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, long delayInMillis) {
        return startBundleAsync(bc, symbolicName, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version, long delayInMillis) {
        return startBundleAsync(bc, symbolicName, version, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param options       The options for starting this bundle. See
     *                      {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                      Framework must ignore unrecognized options.
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(Bundle bundle, int options, long delayInMillis) {
        return startBundleAsync(bundle, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param options       The options for starting this bundle. See
     *                      {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                      Framework must ignore unrecognized options.
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId, int options, long delayInMillis) {
        return startBundleAsync(bc, bundleId, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param options       The options for starting this bundle. See
     *                      {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                      Framework must ignore unrecognized options.
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, int options, long delayInMillis) {
        return startBundleAsync(bc, symbolicName, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param options       The options for starting this bundle. See
     *                      {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                      Framework must ignore unrecognized options.
     * @param delayInMillis time interval in millis to wait before start. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version, int options, long delayInMillis) {
        return startBundleAsync(bc, symbolicName, version, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param delay    time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(Bundle bundle, long delay, TimeUnit timeUnit) {
        return startBundleAsync(bundle, 0, delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id symbolicName
     * @param delay    time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, bundleId), delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param delay        time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, symbolicName), delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param delay        time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, symbolicName, version), delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param options  The options for starting this bundle. See
     *                 {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                 Framework must ignore unrecognized options.
     * @param delay    time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(final Bundle bundle, final int options, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                bundle.start(options);
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param options  The options for starting this bundle. See
     *                 {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                 Framework must ignore unrecognized options.
     * @param delay    time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, long bundleId, int options, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, bundleId), options, delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param options      The options for starting this bundle. See
     *                     {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                     Framework must ignore unrecognized options.
     * @param delay        time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, int options, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, symbolicName), options, delay, timeUnit);
    }

    /**
     * Starts a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param options      The options for starting this bundle. See
     *                     {@link Bundle#START_TRANSIENT} and {@link Bundle#START_ACTIVATION_POLICY}. The
     *                     Framework must ignore unrecognized options.
     * @param delay        time interval to wait before start. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> startBundleAsync(BundleContext bc, String symbolicName, Version version, int options, long delay, TimeUnit timeUnit) {
        return startBundleAsync(findBundle(bc, symbolicName, version), options, delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bundle Bundle
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(Bundle bundle) {
        return stopBundleAsync(bundle, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId) {
        return stopBundleAsync(bc, bundleId, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName) {
        return stopBundleAsync(bc, symbolicName, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version) {
        return stopBundleAsync(bc, symbolicName, version, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bundle  Bundle
     * @param options The options for stoping this bundle. See
     *                {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(Bundle bundle, int options) {
        return stopBundleAsync(bundle, options, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param options  The options for stoping this bundle. See
     *                 {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                 options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId, int options) {
        return stopBundleAsync(bc, bundleId, options, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param options      The options for stoping this bundle. See
     *                     {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                     options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, int options) {
        return stopBundleAsync(bc, symbolicName, options, 0L);
    }

    /**
     * Stops a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param options      The options for stoping this bundle. See
     *                     {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                     options.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version, int options) {
        return stopBundleAsync(bc, symbolicName, version, options, 0L);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(Bundle bundle, long delayInMillis) {
        return stopBundleAsync(bundle, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId, long delayInMillis) {
        return stopBundleAsync(bc, bundleId, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, long delayInMillis) {
        return stopBundleAsync(bc, symbolicName, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version, long delayInMillis) {
        return stopBundleAsync(bc, symbolicName, version, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param options       The options for stoping this bundle. See
     *                      {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                      options.
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(Bundle bundle, int options, long delayInMillis) {
        return stopBundleAsync(bundle, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param options       The options for stoping this bundle. See
     *                      {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                      options.
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId, int options, long delayInMillis) {
        return stopBundleAsync(bc, bundleId, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param options       The options for stoping this bundle. See
     *                      {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                      options.
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, int options, long delayInMillis) {
        return stopBundleAsync(bc, symbolicName, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param options       The options for stoping this bundle. See
     *                      {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                      options.
     * @param delayInMillis time interval in millis to wait before stop. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version, int options, long delayInMillis) {
        return stopBundleAsync(bc, symbolicName, version, options, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param delay    time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(Bundle bundle, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(bundle, 0, delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id symbolicName
     * @param delay    time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, bundleId), delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param delay        time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, symbolicName), delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param delay        time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, symbolicName, version), delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param options  The options for stoping this bundle. See
     *                 {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                 options.
     * @param delay    time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(final Bundle bundle, final int options, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                bundle.stop(options);
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param options  The options for stoping this bundle. See
     *                 {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                 options.
     * @param delay    time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, long bundleId, int options, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, bundleId), options, delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param options      The options for stoping this bundle. See
     *                     {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                     options.
     * @param delay        time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, int options, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, symbolicName), options, delay, timeUnit);
    }

    /**
     * Stops a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param options      The options for stoping this bundle. See
     *                     {@link Bundle#STOP_TRANSIENT}. The Framework must ignore unrecognized
     *                     options.
     * @param delay        time interval to wait before stop. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> stopBundleAsync(BundleContext bc, String symbolicName, Version version, int options, long delay, TimeUnit timeUnit) {
        return stopBundleAsync(findBundle(bc, symbolicName, version), options, delay, timeUnit);
    }

    /**
     * Uninstalls a bundle asynchronously
     *
     * @param bundle bundle
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(Bundle bundle) {
        return uninstallBundleAsync(bundle, 0L);
    }

    /**
     * Uninstalls a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, long bundleId) {
        return uninstallBundleAsync(bc, bundleId, 0L);
    }

    /**
     * Uninstalls a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName) {
        return uninstallBundleAsync(bc, symbolicName, 0L);
    }

    /**
     * Uninstalls a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName, Version version) {
        return uninstallBundleAsync(bc, symbolicName, version, 0L);
    }

    /**
     * Uninstalls a bundle asynchronously with delayInMillis
     *
     * @param bundle        bundle
     * @param delayInMillis time interval in millis to wait before uninstall. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(Bundle bundle, long delayInMillis) {
        return uninstallBundleAsync(bundle, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Uninstalls a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param delayInMillis time interval in millis to wait before uninstall. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, long bundleId, long delayInMillis) {
        return uninstallBundleAsync(bc, bundleId, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Uninstalls a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param delayInMillis time interval in millis to wait before uninstall. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName, long delayInMillis) {
        return uninstallBundleAsync(bc, symbolicName, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Uninstalls a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param delayInMillis time interval in millis to wait before uninstall. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName, Version version, long delayInMillis) {
        return uninstallBundleAsync(bc, symbolicName, version, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Uninstalls a bundle asynchronously with delay
     *
     * @param bundle   bundle
     * @param delay    time interval to wait before uninstall. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(final Bundle bundle, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                bundle.uninstall();
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Uninstalls a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param delay    time interval to wait before uninstall. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, long bundleId, long delay, TimeUnit timeUnit) {
        return uninstallBundleAsync(findBundle(bc, bundleId), delay, timeUnit);
    }

    /**
     * Uninstalls a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param delay        time interval to wait before uninstall. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName, long delay, TimeUnit timeUnit) {
        return uninstallBundleAsync(findBundle(bc, symbolicName), delay, timeUnit);
    }

    /**
     * Uninstalls a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param delay        time interval to wait before uninstall. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> uninstallBundleAsync(BundleContext bc, String symbolicName, Version version, long delay, TimeUnit timeUnit) {
        return uninstallBundleAsync(findBundle(bc, symbolicName, version), delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bundle bundle
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(Bundle bundle) {
        return updateBundleAsync(bundle, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId) {
        return updateBundleAsync(bc, bundleId, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName) {
        return updateBundleAsync(bc, symbolicName, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version) {
        return updateBundleAsync(bc, symbolicName, version, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bundle Bundle
     * @param input  The <code>InputStream</code> from which to read the new
     *               bundle or <code>null</code> to indicate the Framework must create
     *               the input stream from this bundle's
     *               {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *               Manifest header, if present, or this bundle's original location.
     *               The input stream must always be closed when this method completes,
     *               even if an exception is thrown.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(Bundle bundle, InputStream input) {
        return updateBundleAsync(bundle, input, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param input    The <code>InputStream</code> from which to read the new
     *                 bundle or <code>null</code> to indicate the Framework must create
     *                 the input stream from this bundle's
     *                 {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                 Manifest header, if present, or this bundle's original location.
     *                 The input stream must always be closed when this method completes,
     *                 even if an exception is thrown.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId, InputStream input) {
        return updateBundleAsync(bc, bundleId, input, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param input        The <code>InputStream</code> from which to read the new
     *                     bundle or <code>null</code> to indicate the Framework must create
     *                     the input stream from this bundle's
     *                     {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                     Manifest header, if present, or this bundle's original location.
     *                     The input stream must always be closed when this method completes,
     *                     even if an exception is thrown.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, InputStream input) {
        return updateBundleAsync(bc, symbolicName, input, 0L);
    }

    /**
     * Updates a bundle asynchronously
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param input        The <code>InputStream</code> from which to read the new
     *                     bundle or <code>null</code> to indicate the Framework must create
     *                     the input stream from this bundle's
     *                     {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                     Manifest header, if present, or this bundle's original location.
     *                     The input stream must always be closed when this method completes,
     *                     even if an exception is thrown.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version, InputStream input) {
        return updateBundleAsync(bc, symbolicName, version, input, 0L);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(Bundle bundle, long delayInMillis) {
        return updateBundleAsync(bundle, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId, long delayInMillis) {
        return updateBundleAsync(bc, bundleId, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, long delayInMillis) {
        return updateBundleAsync(bc, symbolicName, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version, long delayInMillis) {
        return updateBundleAsync(bc, symbolicName, version, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bundle        Bundle
     * @param input         The <code>InputStream</code> from which to read the new
     *                      bundle or <code>null</code> to indicate the Framework must create
     *                      the input stream from this bundle's
     *                      {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                      Manifest header, if present, or this bundle's original location.
     *                      The input stream must always be closed when this method completes,
     *                      even if an exception is thrown.
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(Bundle bundle, InputStream input, long delayInMillis) {
        return updateBundleAsync(bundle, input, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param input         The <code>InputStream</code> from which to read the new
     *                      bundle or <code>null</code> to indicate the Framework must create
     *                      the input stream from this bundle's
     *                      {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                      Manifest header, if present, or this bundle's original location.
     *                      The input stream must always be closed when this method completes,
     *                      even if an exception is thrown.
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId, InputStream input, long delayInMillis) {
        return updateBundleAsync(bc, bundleId, input, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param input         The <code>InputStream</code> from which to read the new
     *                      bundle or <code>null</code> to indicate the Framework must create
     *                      the input stream from this bundle's
     *                      {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                      Manifest header, if present, or this bundle's original location.
     *                      The input stream must always be closed when this method completes,
     *                      even if an exception is thrown.
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, InputStream input, long delayInMillis) {
        return updateBundleAsync(bc, symbolicName, input, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param input         The <code>InputStream</code> from which to read the new
     *                      bundle or <code>null</code> to indicate the Framework must create
     *                      the input stream from this bundle's
     *                      {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                      Manifest header, if present, or this bundle's original location.
     *                      The input stream must always be closed when this method completes,
     *                      even if an exception is thrown.
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version, InputStream input, long delayInMillis) {
        return updateBundleAsync(bc, symbolicName, version, input, delayInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param delay    time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(Bundle bundle, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(bundle, null, delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param delay    time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, bundleId), delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param delay        time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, symbolicName), delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param delay        time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, symbolicName, version), delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bundle   Bundle
     * @param input    The <code>InputStream</code> from which to read the new
     *                 bundle or <code>null</code> to indicate the Framework must create
     *                 the input stream from this bundle's
     *                 {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                 Manifest header, if present, or this bundle's original location.
     *                 The input stream must always be closed when this method completes,
     *                 even if an exception is thrown.
     * @param delay    time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(final Bundle bundle, final InputStream input, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                bundle.update(input);
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc       BundleContext
     * @param bundleId bundle id
     * @param input    The <code>InputStream</code> from which to read the new
     *                 bundle or <code>null</code> to indicate the Framework must create
     *                 the input stream from this bundle's
     *                 {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                 Manifest header, if present, or this bundle's original location.
     *                 The input stream must always be closed when this method completes,
     *                 even if an exception is thrown.
     * @param delay    time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, long bundleId, InputStream input, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, bundleId), input, delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param input        The <code>InputStream</code> from which to read the new
     *                     bundle or <code>null</code> to indicate the Framework must create
     *                     the input stream from this bundle's
     *                     {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                     Manifest header, if present, or this bundle's original location.
     *                     The input stream must always be closed when this method completes,
     *                     even if an exception is thrown.
     * @param delay        time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, InputStream input, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, symbolicName), input, delay, timeUnit);
    }

    /**
     * Updates a bundle asynchronously with delay
     *
     * @param bc           BundleContext
     * @param symbolicName symbolicName
     * @param version      version
     * @param input        The <code>InputStream</code> from which to read the new
     *                     bundle or <code>null</code> to indicate the Framework must create
     *                     the input stream from this bundle's
     *                     {@link Constants#BUNDLE_UPDATELOCATION Bundle-UpdateLocation}
     *                     Manifest header, if present, or this bundle's original location.
     *                     The input stream must always be closed when this method completes,
     *                     even if an exception is thrown.
     * @param delay        time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit     time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateBundleAsync(BundleContext bc, String symbolicName, Version version, InputStream input, long delay, TimeUnit timeUnit) {
        return updateBundleAsync(findBundle(bc, symbolicName, version), input, delay, timeUnit);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param bundleId        bundle id
     * @param eventTypeMask   BundleEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException     If <code>bc</code> is <code>null</code>
     * @throws IllegalArgumentException If <code>bundleId</code> is invalid
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeoutInMillis) {
        return waitForBundleEvent(bc, bundleId, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param eventTypeMask   BundleEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeoutInMillis) {
        return waitForBundleEvent(bc, symbolicName, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc              BundleContext
     * @param symbolicName    symbolicName
     * @param version         version
     * @param eventTypeMask   BundleEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> are <code>null</code>
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeoutInMillis) {
        return waitForBundleEvent(bc, symbolicName, version, eventTypeMask, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param bundleId      bundle id
     * @param eventTypeMask BundleEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException     If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @throws IllegalArgumentException If <code>bundleId</code> is invalid
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, int bundleId, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        Bundle bundle = findBundle(bc, bundleId);
        if (bundle == null) {
            throw new IllegalArgumentException("bundleId is invalid");
        }
        return waitForBundleEvent(bc, bundle.getSymbolicName(), bundle.getVersion(), eventTypeMask, timeout, timeUnit);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param eventTypeMask BundleEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, String symbolicName, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return waitForBundleEvent(bc, symbolicName, null, eventTypeMask, timeout, timeUnit);
    }

    /**
     * Wait for BundleEvent with event type mask for defined bundle
     *
     * @param bc            BundleContext
     * @param symbolicName  symbolicName
     * @param version       version
     * @param eventTypeMask BundleEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return BundleEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>symbolicName</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static BundleEvent waitForBundleEvent(BundleContext bc, String symbolicName, Version version, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        BundleListenerImpl listener = new BundleListenerImpl(symbolicName, version, eventTypeMask, latch);
        bc.addBundleListener(listener);

        try {
            return waitForBundleEvent(listener, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            bc.removeBundleListener(listener);
        }
    }

    private static BundleEvent waitForBundleEvent(BundleListenerImpl listener, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (latch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
            return listener.getBundleEvent();
        } else {
            return null;
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

    private static class BundleListenerImpl implements BundleListener {
        private String symbolicName;
        private Version version;
        private int eventTypeMask;
        private CountDownLatch latch;

        private BundleEvent event;

        public BundleListenerImpl(String symbolicName, Version version, int eventTypeMask, CountDownLatch latch) {
            this.symbolicName = symbolicName;
            this.version = version;
            this.eventTypeMask = eventTypeMask;
            this.latch = latch;
        }

        public void bundleChanged(BundleEvent event) {
            if (match(event)) {
                this.event = event;
                latch.countDown();
            }
        }

        private boolean match(BundleEvent event) {
            Bundle bundle = event.getBundle();
            return bundle.getSymbolicName().equals(symbolicName)
                    && (version == null || bundle.getVersion().equals(version))
                    && (eventTypeMask & event.getType()) != 0;
        }

        public BundleEvent getBundleEvent() {
            return event;
        }
    }
}
