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
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import java.util.Dictionary;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.knowhowlab.osgi.testing.utils.FilterUtils.create;
import static org.osgi.framework.FrameworkUtil.createFilter;

/**
 * OSGi Services utilities class
 *
 * @author dmytro.pishchukhin
 * @version 1.1
 * @see org.osgi.framework.BundleContext
 * @see org.osgi.util.tracker.ServiceTracker
 * @see org.osgi.util.tracker.ServiceTrackerCustomizer
 * @see org.osgi.framework.ServiceEvent
 * @see org.osgi.framework.ServiceListener
 * @see org.osgi.framework.Filter
 */
public class ServiceUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private ServiceUtils() {
    }

    /**
     * Get ServiceReference by filter
     *
     * @param bc     BundleContext
     * @param filter filter
     * @return ServiceReference instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>filter</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by filter with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param filter          filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter, long timeoutInMillis) {
        return getServiceReference(bc, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get ServiceReference by filter with timeout.
     *
     * @param bc       BundleContext
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Filter filter, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceTracker tracker = new ServiceTracker(bc, filter, new ServiceTrackerCustomizerWithLock(bc, latch));
        tracker.open();
        try {
            return waitForServiceReference(tracker, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class name
     *
     * @param bc        BundleContext
     * @param className className
     * @return ServiceReference instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>className</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class name with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param className       className
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>className</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className, long timeoutInMillis) {
        return getServiceReference(bc, className, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get ServiceReference by class name with timeout.
     *
     * @param bc        BundleContext
     * @param className className
     * @param timeout   time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>className</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, String className, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceTracker tracker = new ServiceTracker(bc, className, new ServiceTrackerCustomizerWithLock(bc, latch));
        tracker.open();
        try {
            return waitForServiceReference(tracker, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class
     *
     * @param bc    BundleContext
     * @param clazz Class
     * @return ServiceReference instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            return tracker.getServiceReference();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get ServiceReference by class with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param clazz           Class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz, long timeoutInMillis) {
        return getServiceReference(bc, clazz, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get ServiceReference by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static ServiceReference getServiceReference(BundleContext bc, Class clazz, long timeout, TimeUnit timeUnit) {
        return getServiceReference(bc, clazz.getName(), timeout, timeUnit);
    }

    /**
     * Get service instance by filter
     *
     * @param bc     BundleContext
     * @param filter filter
     * @return service instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>filter</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, Filter filter) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by filter with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param filter          filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>filter</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, Filter filter, long timeoutInMillis) {
        return getService(bc, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get service instance by filter with timeout.
     *
     * @param bc       BundleContext
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>filter</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, Filter filter, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, filter, null);
        tracker.open();
        try {
            return tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class name
     *
     * @param bc        BundleContext
     * @param className className
     * @return service instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>className</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, String className) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class name with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param className       className
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>className</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, String className, long timeoutInMillis) {
        return getService(bc, className, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get service instance by className with timeout.
     *
     * @param bc        BundleContext
     * @param className className
     * @param timeout   time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit  time unit for the time interval
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>className</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static Object getService(BundleContext bc, String className, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, className, null);
        tracker.open();
        try {
            return tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class
     *
     * @param bc    BundleContext
     * @param clazz Class
     * @return service instance instance or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param clazz           Class
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, long timeoutInMillis) {
        return getService(bc, clazz, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, long timeout, TimeUnit timeUnit) {
        ServiceTracker tracker = new ServiceTracker(bc, clazz.getName(), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class
     *
     * @param bc     BundleContext
     * @param clazz  Class
     * @param filter filter
     * @return service instance instance or <code>null</code>
     * @throws NullPointerException   If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException If <code>filter</code> contains an
     *                                invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter) throws InvalidSyntaxException {
        return getService(bc, clazz, createFilter(filter));
    }

    /**
     * Get service instance by class with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param clazz           Class
     * @param filter          filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter, long timeoutInMillis) throws InvalidSyntaxException {
        return getService(bc, clazz, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, String filter, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        return getService(bc, clazz, createFilter(filter), timeout, timeUnit);
    }

    /**
     * Get service instance by class
     *
     * @param bc     BundleContext
     * @param clazz  Class
     * @param filter filter
     * @return service instance instance or <code>null</code>
     * @throws NullPointerException   If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException If <code>filter</code> contains an
     *                                invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter) throws InvalidSyntaxException {
        ServiceTracker tracker = new ServiceTracker(bc, create(clazz, filter), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.getService();
        } finally {
            tracker.close();
        }
    }

    /**
     * Get service instance by class with timeoutInMillis.
     *
     * @param bc              BundleContext
     * @param clazz           Class
     * @param filter          filter
     * @param timeoutInMillis time interval in milliseconds to wait. If zero, the method will wait indefinitely.
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeoutInMillis is negative
     * @throws NullPointerException     If <code>bc</code> or <code>clazz</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter, long timeoutInMillis) throws InvalidSyntaxException {
        return getService(bc, clazz, filter, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Get service instance by class with timeout.
     *
     * @param bc       BundleContext
     * @param clazz    Class
     * @param filter   filter
     * @param timeout  time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit time unit for the time interval
     * @return service instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative
     * @throws NullPointerException     If <code>bc</code>, <code>clazz</code> or
     *                                  <code>timeUnit</code> are <code>null</code>
     * @throws InvalidSyntaxException   If <code>filter</code> contains an
     *                                  invalid filter string that cannot be parsed
     * @since 1.0
     */
    public static <T> T getService(BundleContext bc, Class<T> clazz, Filter filter, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        ServiceTracker tracker = new ServiceTracker(bc, create(clazz, filter), null);
        tracker.open();
        try {
            //noinspection unchecked
            return (T) tracker.waitForService(timeUnit.toMillis(timeout));
        } catch (InterruptedException e) {
            return null;
        } finally {
            tracker.close();
        }
    }

    /**
     * Registers service asynchronously
     *
     * @param bc         BundleContext
     * @param clazz      The class under which the service can be located.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static <T> Future<ServiceRegistration> registerServiceAsync(BundleContext bc, Class<T> clazz, T service, Dictionary properties) {
        return registerServiceAsync(bc, clazz, service, properties, 0L);
    }

    /**
     * Registers service asynchronously
     *
     * @param bc         BundleContext
     * @param clazz      The class name under which the service can be located.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(BundleContext bc, String clazz, Object service, Dictionary properties) {
        return registerServiceAsync(bc, clazz, service, properties, 0L);
    }

    /**
     * Registers service asynchronously
     *
     * @param bc         BundleContext
     * @param clazzes    The class names under which the service can be located.
     *                   The class names in this array will be stored in the service's
     *                   properties under the key {@link Constants#OBJECTCLASS}.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(BundleContext bc, String[] clazzes, Object service, Dictionary properties) {
        return registerServiceAsync(bc, clazzes, service, properties, 0L);
    }

    /**
     * Registers service asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param clazz         The class under which the service can be located.
     * @param service       The service object or a <code>ServiceFactory</code> object.
     * @param properties    The properties for this service.
     * @param delayInMillis time interval in millis to wait before registration. If zero, the method will not wait.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static <T> Future<ServiceRegistration> registerServiceAsync(BundleContext bc, Class<T> clazz, T service, Dictionary properties, long delayInMillis) {
        return registerServiceAsync(bc, clazz, service, properties, delayInMillis, MILLISECONDS);
    }

    /**
     * Registers service asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param clazz         The class name under which the service can be located.
     * @param service       The service object or a <code>ServiceFactory</code> object.
     * @param properties    The properties for this service.
     * @param delayInMillis time interval in millis to wait before registration. If zero, the method will not wait.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(BundleContext bc, String clazz, Object service, Dictionary properties, long delayInMillis) {
        return registerServiceAsync(bc, clazz, service, properties, delayInMillis, MILLISECONDS);
    }

    /**
     * Registers service asynchronously with delayInMillis
     *
     * @param bc            BundleContext
     * @param clazzes       The class names under which the service can be located.
     *                      The class names in this array will be stored in the service's
     *                      properties under the key {@link Constants#OBJECTCLASS}.
     * @param service       The service object or a <code>ServiceFactory</code> object.
     * @param properties    The properties for this service.
     * @param delayInMillis time interval in millis to wait before registration. If zero, the method will not wait.
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(BundleContext bc, String[] clazzes, Object service, Dictionary properties, long delayInMillis) {
        return registerServiceAsync(bc, clazzes, service, properties, delayInMillis, MILLISECONDS);
    }

    /**
     * Registers service asynchronously with delay
     *
     * @param bc         BundleContext
     * @param clazz      The class under which the service can be located.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @param delay      time interval to wait before registration. If zero, the method will not wait.
     * @param timeUnit   time unit for the time interval
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static <T> Future<ServiceRegistration> registerServiceAsync(final BundleContext bc, final Class<T> clazz, final T service, final Dictionary properties, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<ServiceRegistration>() {
            public ServiceRegistration call() throws Exception {
                return bc.registerService(clazz.getName(), service, properties);
            }
        }, delay, timeUnit);
    }

    /**
     * Registers service asynchronously with delay
     *
     * @param bc         BundleContext
     * @param clazz      The class name under which the service can be located.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @param delay      time interval to wait before registration. If zero, the method will not wait.
     * @param timeUnit   time unit for the time interval
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(final BundleContext bc, final String clazz, final Object service, final Dictionary properties, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<ServiceRegistration>() {
            public ServiceRegistration call() throws Exception {
                return bc.registerService(clazz, service, properties);
            }
        }, delay, timeUnit);
    }

    /**
     * Registers service asynchronously with delay
     *
     * @param bc         BundleContext
     * @param clazzes    The class names under which the service can be located.
     *                   The class names in this array will be stored in the service's
     *                   properties under the key {@link Constants#OBJECTCLASS}.
     * @param service    The service object or a <code>ServiceFactory</code> object.
     * @param properties The properties for this service.
     * @param delay      time interval to wait before registration. If zero, the method will not wait.
     * @param timeUnit   time unit for the time interval
     * @return A <code>Future&lt;ServiceRegistration&gt;</code> object for use by the bundle
     *         registering the service to update the service's properties or to
     *         unregister the service.
     * @since 1.1
     */
    public static Future<ServiceRegistration> registerServiceAsync(final BundleContext bc, final String[] clazzes, final Object service, final Dictionary properties, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<ServiceRegistration>() {
            public ServiceRegistration call() throws Exception {
                return bc.registerService(clazzes, service, properties);
            }
        }, delay, timeUnit);
    }

    /**
     * Updates registered service properties asynchronously
     *
     * @param registration Service registration
     * @param properties   The properties for this service. See {@link Constants}
     *                     for a list of standard service property keys. Changes should not
     *                     be made to this object after calling this method. To update the
     *                     service's properties this method should be called again.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateServiceAsync(ServiceRegistration registration, Dictionary properties) {
        return updateServiceAsync(registration, properties, 0L);
    }

    /**
     * Updates registered service properties asynchronously with delayInMillis
     *
     * @param registration Service registration
     * @param properties   The properties for this service. See {@link Constants}
     *                     for a list of standard service property keys. Changes should not
     *                     be made to this object after calling this method. To update the
     *                     service's properties this method should be called again.
     * @param delayInMillis time interval in millis to wait before update. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateServiceAsync(ServiceRegistration registration, Dictionary properties, long delayInMillis) {
        return updateServiceAsync(registration, properties, delayInMillis, MILLISECONDS);
    }

    /**
     * Updates registered service properties asynchronously with delay
     *
     * @param registration Service registration
     * @param properties   The properties for this service. See {@link Constants}
     *                     for a list of standard service property keys. Changes should not
     *                     be made to this object after calling this method. To update the
     *                     service's properties this method should be called again.
     * @param delay      time interval to wait before update. If zero, the method will not wait.
     * @param timeUnit   time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> updateServiceAsync(final ServiceRegistration registration, final Dictionary properties, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                registration.setProperties(properties);
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Unregisters a service asynchronously
     *
     * @param registration Service registration
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> unregisterServiceAsync(ServiceRegistration registration) {
        return unregisterServiceAsync(registration, 0L);
    }

    /**
     * Unregisters a service asynchronously with delayInMillis
     *
     * @param registration Service registration
     * @param delayInMillis time interval in millis to wait before unregistration. If zero, the method will not wait.
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> unregisterServiceAsync(ServiceRegistration registration, long delayInMillis) {
        return unregisterServiceAsync(registration, delayInMillis, MILLISECONDS);
    }

    /**
     * Unregisters a service asynchronously with delay
     *
     * @param registration Service registration
     * @param delay      time interval to wait before unregistration. If zero, the method will not wait.
     * @param timeUnit   time unit for the time interval
     * @return The Future's <tt>get</tt> method will return <tt>null</tt> upon <em>successful</em> completion.
     * @since 1.1
     */
    public static Future<?> unregisterServiceAsync(final ServiceRegistration registration, long delay, TimeUnit timeUnit) {
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                registration.unregister();
                return null;
            }
        }, delay, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined filter
     *
     * @param bc              BundleContext
     * @param filter          filter (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Filter filter, int eventTypeMask, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, filter, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class name
     *
     * @param bc              BundleContext
     * @param className       class name (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, String className, int eventTypeMask, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, className, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class
     *
     * @param bc              BundleContext
     * @param clazz           class (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Class clazz, int eventTypeMask, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, clazz, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class name
     *
     * @param bc            BundleContext
     * @param className     class name (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, String className, int eventTypeMask, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, className, eventTypeMask, false, timeout, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class
     *
     * @param bc            BundleContext
     * @param clazz         class (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Class clazz, int eventTypeMask, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, clazz, eventTypeMask, false, timeout, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined filter
     *
     * @param bc            BundleContext
     * @param filter        filter (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Filter filter, int eventTypeMask, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, filter, eventTypeMask, false, timeout, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined filter
     *
     * @param bc              BundleContext
     * @param filter          filter (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Filter filter, int eventTypeMask, boolean all, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, filter, eventTypeMask, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class name
     *
     * @param bc              BundleContext
     * @param className       class name (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, String className, int eventTypeMask, boolean all, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, className, eventTypeMask, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class
     *
     * @param bc              BundleContext
     * @param clazz           class (could be null)
     * @param eventTypeMask   ServiceEvent type mask
     * @param all             use AllServiceListener
     * @param timeoutInMillis time interval in millis to wait. If zero, the method will wait indefinitely.
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> is <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Class clazz, int eventTypeMask, boolean all, long timeoutInMillis) throws InvalidSyntaxException {
        return waitForServiceEvent(bc, clazz, eventTypeMask, all, timeoutInMillis, MILLISECONDS);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class name
     *
     * @param bc            BundleContext
     * @param className     class name (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, String className, int eventTypeMask, boolean all, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        Filter filter = null;
        if (className != null) {
            filter = create(className);
        }
        return waitForServiceEvent(bc, filter, eventTypeMask, all, timeout, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined class
     *
     * @param bc            BundleContext
     * @param clazz         class (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Class clazz, int eventTypeMask, boolean all, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        Filter filter = null;
        if (clazz != null) {
            filter = create(clazz);
        }
        return waitForServiceEvent(bc, filter, eventTypeMask, all, timeout, timeUnit);
    }

    /**
     * Wait for ServiceEvent with event type mask for defined filter
     *
     * @param bc            BundleContext
     * @param filter        filter (could be null)
     * @param eventTypeMask ServiceEvent type mask
     * @param all           use AllServiceListener
     * @param timeout       time interval to wait. If zero, the method will wait indefinitely.
     * @param timeUnit      time unit for the time interval
     * @return ServiceEvent or <code>null</code>
     * @throws NullPointerException If <code>bc</code> or <code>timeUnit</code> are <code>null</code>
     * @since 1.1
     */
    public static ServiceEvent waitForServiceEvent(BundleContext bc, Filter filter, int eventTypeMask, boolean all, long timeout, TimeUnit timeUnit) throws InvalidSyntaxException {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        ServiceListenerImpl listener;
        if (all) {
            listener = new AllServiceListenerImpl(eventTypeMask, latch);
        } else {
            listener = new ServiceListenerImpl(eventTypeMask, latch);
        }
        bc.addServiceListener(listener, filter != null ? filter.toString() : null);

        try {
            return waitForServiceEvent(listener, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            bc.removeServiceListener(listener);
        }
    }

    private static ServiceEvent waitForServiceEvent(ServiceListenerImpl listener, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (latch.await(timeoutInMillis, MILLISECONDS)) {
            return listener.getServiceEvent();
        } else {
            return null;
        }
    }

    /**
     * Wait for at least one ServiceReference to be tracked by ServiceTracker
     *
     * @param tracker         ServiceTracker
     * @param timeoutInMillis time interval in milliseconds to wait.
     *                        If zero, the method will wait indefinitely.
     * @param latch           external latch that is used to handle new service adding to ServiceTracker
     * @return ServiceReference instance or <code>null</code>
     * @throws IllegalArgumentException If the value of timeout is negative.
     * @throws InterruptedException     If another thread has interrupted the current thread.
     */
    private static ServiceReference waitForServiceReference(ServiceTracker tracker, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        ServiceReference reference = tracker.getServiceReference();
        if (reference == null) {
            if (latch.await(timeoutInMillis, MILLISECONDS)) {
                return tracker.getServiceReference();
            } else {
                return null;
            }
        } else {
            return reference;
        }
    }

    /**
     * ServiceTrackerCustomizer with lock support.
     *
     * @see java.util.concurrent.locks.ReentrantLock
     * @see org.osgi.util.tracker.ServiceTrackerCustomizer
     */
    private static class ServiceTrackerCustomizerWithLock implements ServiceTrackerCustomizer {
        private final BundleContext bc;
        private final CountDownLatch latch;

        public ServiceTrackerCustomizerWithLock(BundleContext bc, CountDownLatch latch) {
            this.bc = bc;
            this.latch = latch;
        }

        public Object addingService(ServiceReference serviceReference) {
            try {
                return bc.getService(serviceReference);
            } finally {
                latch.countDown();
            }
        }

        public void modifiedService(ServiceReference serviceReference, Object o) {
        }

        public void removedService(ServiceReference serviceReference, Object o) {
        }
    }

    private static class ServiceListenerImpl implements ServiceListener {
        private int eventTypeMask;
        private CountDownLatch latch;

        private ServiceEvent event;

        public ServiceListenerImpl(int eventTypeMask, CountDownLatch latch) {
            this.eventTypeMask = eventTypeMask;
            this.latch = latch;
        }

        public void serviceChanged(ServiceEvent event) {
            if (match(event)) {
                this.event = event;
                latch.countDown();
            }
        }

        private boolean match(ServiceEvent event) {
            return (eventTypeMask & event.getType()) != 0;
        }

        public ServiceEvent getServiceEvent() {
            return event;
        }
    }

    private static class AllServiceListenerImpl extends ServiceListenerImpl implements AllServiceListener {
        public AllServiceListenerImpl(int eventTypeMask, CountDownLatch latch) {
            super(eventTypeMask, latch);
        }
    }
}
