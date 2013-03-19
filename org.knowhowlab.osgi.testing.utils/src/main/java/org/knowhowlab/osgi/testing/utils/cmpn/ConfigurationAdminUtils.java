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

package org.knowhowlab.osgi.testing.utils.cmpn;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.knowhowlab.osgi.testing.utils.FilterUtils.and;
import static org.knowhowlab.osgi.testing.utils.FilterUtils.eq;
import static org.knowhowlab.osgi.testing.utils.ServiceUtils.getService;
import static org.osgi.framework.Constants.SERVICE_PID;
import static org.osgi.service.cm.ConfigurationAdmin.SERVICE_BUNDLELOCATION;
import static org.osgi.service.cm.ConfigurationEvent.CM_DELETED;
import static org.osgi.service.cm.ConfigurationEvent.CM_UPDATED;
import static org.osgi.service.cm.ConfigurationPlugin.CM_RANKING;
import static org.osgi.service.cm.ConfigurationPlugin.CM_TARGET;

/**
 * OSGi ConfigurationAdmin utilities class
 *
 * @author dpishchukhin
 * @version 1.0
 * @see org.osgi.framework.Bundle
 * @see org.osgi.service.cm.ConfigurationAdmin
 * @see org.osgi.service.cm.Configuration
 */
public class ConfigurationAdminUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private ConfigurationAdminUtils() {
    }

    // create config
    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, Dictionary properties, long delayInMillis) {
        return supplyConfiguration(configurationAdmin, pid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, Map properties, long delayInMillis) {
        return supplyConfiguration(configurationAdmin, pid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, String location, Dictionary properties, long delayInMillis) {
        return supplyConfiguration(configurationAdmin, pid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, String location, Map properties, long delayInMillis) {
        return supplyConfiguration(configurationAdmin, pid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, Dictionary properties, long delayInMillis) {
        return supplyConfiguration(bc, pid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, Map properties, long delayInMillis) {
        return supplyConfiguration(bc, pid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, String location, Dictionary properties, long delayInMillis) {
        return supplyConfiguration(bc, pid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, String location, Map properties, long delayInMillis) {
        return supplyConfiguration(bc, pid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyConfiguration(final ConfigurationAdmin configurationAdmin, final String pid, final Dictionary properties, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Configuration>() {
            public Configuration call() throws Exception {
                Configuration configuration = configurationAdmin.getConfiguration(pid);
                configuration.update(properties);
                return configuration;
            }
        }, delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, Map properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(configurationAdmin, pid, toDictionary(properties), delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(final ConfigurationAdmin configurationAdmin, final String pid, final String location, final Dictionary properties, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Configuration>() {
            public Configuration call() throws Exception {
                Configuration configuration = configurationAdmin.getConfiguration(pid, location);
                configuration.update(properties);
                return configuration;
            }
        }, delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(ConfigurationAdmin configurationAdmin, String pid, String location, Map properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(configurationAdmin, pid, location, toDictionary(properties), delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, Dictionary properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(getService(bc, ConfigurationAdmin.class), pid, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, Map properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(getService(bc, ConfigurationAdmin.class), pid, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, String location, Dictionary properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(getService(bc, ConfigurationAdmin.class), pid, location, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyConfiguration(BundleContext bc, String pid, String location, Map properties, long delay, TimeUnit timeUnit) {
        return supplyConfiguration(getService(bc, ConfigurationAdmin.class), pid, location, properties, delay, timeUnit);
    }

    // create config for factory
    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, Dictionary properties, long delayInMillis) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, Map properties, long delayInMillis) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, String location, Dictionary properties, long delayInMillis) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, String location, Map properties, long delayInMillis) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, Dictionary properties, long delayInMillis) {
        return supplyFactoryConfiguration(bc, factoryPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, Map properties, long delayInMillis) {
        return supplyFactoryConfiguration(bc, factoryPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, String location, Dictionary properties, long delayInMillis) {
        return supplyFactoryConfiguration(bc, factoryPid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, String location, Map properties, long delayInMillis) {
        return supplyFactoryConfiguration(bc, factoryPid, location, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Configuration> supplyFactoryConfiguration(final ConfigurationAdmin configurationAdmin, final String factoryPid, final Dictionary properties, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Configuration>() {
            public Configuration call() throws Exception {
                Configuration configuration = configurationAdmin.createFactoryConfiguration(factoryPid);
                configuration.update(properties);
                return configuration;
            }
        }, delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, Map properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, toDictionary(properties), delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(final ConfigurationAdmin configurationAdmin, final String factoryPid, final String location, final Dictionary properties, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Configuration>() {
            public Configuration call() throws Exception {
                Configuration configuration = configurationAdmin.createFactoryConfiguration(factoryPid, location);
                configuration.update(properties);
                return configuration;
            }
        }, delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(ConfigurationAdmin configurationAdmin, String factoryPid, String location, Map properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(configurationAdmin, factoryPid, location, toDictionary(properties), delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, Dictionary properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(getService(bc, ConfigurationAdmin.class), factoryPid, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, Map properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(getService(bc, ConfigurationAdmin.class), factoryPid, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, String location, Dictionary properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(getService(bc, ConfigurationAdmin.class), factoryPid, location, properties, delay, timeUnit);
    }

    public static Future<Configuration> supplyFactoryConfiguration(BundleContext bc, String factoryPid, String location, Map properties, long delay, TimeUnit timeUnit) {
        return supplyFactoryConfiguration(getService(bc, ConfigurationAdmin.class), factoryPid, location, properties, delay, timeUnit);
    }

    // get config
    public static Configuration[] listConfiguration(ConfigurationAdmin configurationAdmin, Filter filter) throws IOException, InvalidSyntaxException {
        return configurationAdmin.listConfigurations(filter.toString());
    }

    public static Configuration[] listConfiguration(BundleContext bc, Filter filter) throws IOException, InvalidSyntaxException {
        return listConfiguration(getService(bc, ConfigurationAdmin.class), filter);
    }

    // config events
    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, long timeoutInMillis) {
        return waitForConfigurationEvent(bc, timeoutInMillis, MILLISECONDS);
    }

    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, int eventTypeMask, long timeoutInMillis) {
        return waitForConfigurationEvent(bc, eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, int eventTypeMask, String pid, String factoryPid, String location, long timeoutInMillis) {
        return waitForConfigurationEvent(bc, eventTypeMask, pid, factoryPid, location, timeoutInMillis, MILLISECONDS);
    }

    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, long timeout, TimeUnit timeUnit) {
        return waitForConfigurationEvent(bc, CM_DELETED | CM_UPDATED, timeout, timeUnit);
    }

    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, int eventTypeMask, long timeout, TimeUnit timeUnit) {
        return waitForConfigurationEvent(bc, eventTypeMask, null, null, null, timeout, timeUnit);
    }

    public static ConfigurationEvent waitForConfigurationEvent(BundleContext bc, int eventTypeMask, String pid, String factoryPid, String location, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        ConfigurationListenerImpl listener = new ConfigurationListenerImpl(eventTypeMask, pid, factoryPid, location, latch);
        ServiceRegistration registration = bc.registerService(ConfigurationListener.class.getName(), listener, null);

        try {
            return waitForConfigurationEvent(listener, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            registration.unregister();
        }
    }

    private static ConfigurationEvent waitForConfigurationEvent(ConfigurationListenerImpl listener, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (latch.await(timeoutInMillis, MILLISECONDS)) {
            return listener.getConfigurationEvent();
        } else {
            return null;
        }
    }

    // delete config
    public static Future<String> deleteConfiguration(ConfigurationAdmin configurationAdmin, String pid, long delayInMillis) {
        return deleteConfiguration(configurationAdmin, pid, delayInMillis, MILLISECONDS);
    }

    public static Future<String> deleteConfiguration(ConfigurationAdmin configurationAdmin, String pid, String location, long delayInMillis) {
        return deleteConfiguration(configurationAdmin, pid, location, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteConfigurations(ConfigurationAdmin configurationAdmin, Filter filter, long delayInMillis) {
        return deleteConfigurations(configurationAdmin, filter, delayInMillis, MILLISECONDS);
    }

    public static Future<String> deleteConfiguration(BundleContext bc, String pid, long delayInMillis) {
        return deleteConfiguration(bc, pid, delayInMillis, MILLISECONDS);
    }

    public static Future<String> deleteConfiguration(BundleContext bc, String pid, String location, long delayInMillis) {
        return deleteConfiguration(bc, pid, location, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteConfigurations(BundleContext bc, Filter filter, long delayInMillis) {
        return deleteConfigurations(bc, filter, delayInMillis, MILLISECONDS);
    }

    public static Future<String> deleteConfiguration(final ConfigurationAdmin configurationAdmin, final String pid, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        if (pid == null) {
            throw new NullPointerException("service.pid is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<String>() {
            public String call() throws Exception {
                Configuration[] configurations = listConfiguration(configurationAdmin, eq(SERVICE_PID, pid));
                if (configurations != null && configurations.length == 1) {
                    configurations[0].delete();
                }
                return pid;
            }
        }, delay, timeUnit);
    }

    public static Future<String> deleteConfiguration(final ConfigurationAdmin configurationAdmin, final String pid, final String location, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        if (pid == null) {
            throw new NullPointerException("service.pid is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<String>() {
            public String call() throws Exception {
                Filter filter = eq(SERVICE_PID, pid);
                if (location != null) {
                    filter = and(filter, eq(SERVICE_BUNDLELOCATION, location));
                }
                Configuration[] configurations = listConfiguration(configurationAdmin, filter);
                if (configurations != null && configurations.length == 1) {
                    configurations[0].delete();
                }
                return pid;
            }
        }, delay, timeUnit);
    }

    public static Future<String[]> deleteConfigurations(final ConfigurationAdmin configurationAdmin, final Filter filter, long delay, TimeUnit timeUnit) {
        if (configurationAdmin == null) {
            throw new NullPointerException("ConfigurationAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<String[]>() {
            public String[] call() throws Exception {
                Configuration[] configurations = listConfiguration(configurationAdmin, filter);
                String[] result = null;
                if (configurations != null) {
                    List<String> deletedPids = new ArrayList<String>();
                    for (Configuration configuration : configurations) {
                        String pid = configuration.getPid();
                        try {
                            configuration.delete();
                            deletedPids.add(pid);
                        } catch (IOException e) {
                            // unable to remove configuration
                        }
                    }
                    result = deletedPids.toArray(new String[deletedPids.size()]);
                }
                return result;
            }
        }, delay, timeUnit);
    }

    public static Future<String> deleteConfiguration(BundleContext bc, String pid, long delay, TimeUnit timeUnit) {
        return deleteConfiguration(getService(bc, ConfigurationAdmin.class), pid, delay, timeUnit);
    }

    public static Future<String> deleteConfiguration(BundleContext bc, String pid, String location, long delay, TimeUnit timeUnit) {
        return deleteConfiguration(getService(bc, ConfigurationAdmin.class), pid, location, delay, timeUnit);
    }

    public static Future<String[]> deleteConfigurations(BundleContext bc, Filter filter, long delay, TimeUnit timeUnit) {
        return deleteConfigurations(getService(bc, ConfigurationAdmin.class), filter, delay, timeUnit);
    }

    // config plugins
    public static ServiceRegistration applyConfigurationPlugin(BundleContext bc, ConfigurationPlugin plugin) {
        return applyConfigurationPlugin(bc, 0, null, plugin);
    }

    public static ServiceRegistration applyConfigurationPlugin(BundleContext bc, int ranking, String[] targets, ConfigurationPlugin plugin) {
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put(CM_RANKING, ranking);
        if (targets != null) {
            props.put(CM_TARGET, targets);
        }
        return bc.registerService(ConfigurationPlugin.class.getName(), plugin, props);
    }

    private static Dictionary toDictionary(Map properties) {
        if (properties == null) {
            return null;
        }
        return new Hashtable<Object, Object>(properties);
    }

    private static class ConfigurationListenerImpl implements ConfigurationListener {
        private int eventTypeMask;
        private String pid;
        private String factoryPid;
        private String location;
        private CountDownLatch latch;

        private ConfigurationEvent event;

        public ConfigurationListenerImpl(int eventTypeMask, String pid, String factoryPid, String location, CountDownLatch latch) {
            this.eventTypeMask = eventTypeMask;
            this.pid = pid;
            this.factoryPid = factoryPid;
            this.location = location;
            this.latch = latch;
        }

        public void configurationEvent(ConfigurationEvent event) {
            if (match(event)) {
                this.event = event;
                latch.countDown();
            }
        }

        private boolean match(ConfigurationEvent event) {
            return (pid == null || event.getPid().equals(pid))
                    && (factoryPid == null || event.getFactoryPid().equals(factoryPid))
                    && (location == null || event.getReference().getBundle().getLocation().equals(location))
                    && (eventTypeMask & event.getType()) != 0;
        }

        public ConfigurationEvent getConfigurationEvent() {
            return event;
        }

    }

}
