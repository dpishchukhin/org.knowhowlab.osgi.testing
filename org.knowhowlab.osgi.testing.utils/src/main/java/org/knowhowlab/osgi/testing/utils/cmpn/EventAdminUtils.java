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

import org.knowhowlab.osgi.testing.utils.ServiceUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

/**
 * OSGi EventAdmin utilities class
 *
 * @author dpishchukhin
 * @version 1.0
 * @see org.osgi.framework.Bundle
 * @see org.osgi.service.event.EventAdmin
 * @see org.osgi.service.event.Event
 * @see org.osgi.service.event.EventConstants
 * @see org.osgi.service.event.EventHandler
 */
public class EventAdminUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private EventAdminUtils() {
    }

    // post with delay
    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, long delayInMillis) {
        return postEvent(eventAdmin, topic, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, Dictionary properties, long delayInMillis) {
        return postEvent(eventAdmin, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, Map properties, long delayInMillis) {
        return postEvent(eventAdmin, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, Event event, long delayInMillis) {
        return postEvent(eventAdmin, event, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, long delayInMillis) {
        return postEvent(bc, topic, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, Dictionary properties, long delayInMillis) {
        return postEvent(bc, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, Map properties, long delayInMillis) {
        return postEvent(bc, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(BundleContext bc, Event event, long delayInMillis) {
        return postEvent(bc, event, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, long delay, TimeUnit timeUnit) {
        return postEvent(eventAdmin, new Event(topic, (Map) null), delay, timeUnit);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, Dictionary properties, long delay, TimeUnit timeUnit) {
        return postEvent(eventAdmin, new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> postEvent(EventAdmin eventAdmin, String topic, Map properties, long delay, TimeUnit timeUnit) {
        return postEvent(eventAdmin, new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, long delay, TimeUnit timeUnit) {
        return postEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, (Map) null), delay, timeUnit);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, Dictionary properties, long delay, TimeUnit timeUnit) {
        return postEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> postEvent(BundleContext bc, String topic, Map properties, long delay, TimeUnit timeUnit) {
        return postEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> postEvent(BundleContext bc, final Event event, long delay, TimeUnit timeUnit) {
        return postEvent(ServiceUtils.getService(bc, EventAdmin.class), event, delay, timeUnit);
    }

    public static Future<?> postEvent(final EventAdmin eventAdmin, final Event event, long delay, TimeUnit timeUnit) {
        if (eventAdmin == null) {
            throw new NullPointerException("EventAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                eventAdmin.postEvent(event);
                return null;
            }
        }, delay, timeUnit);
    }

    // send with delay
    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, long delayInMillis) {
        return sendEvent(eventAdmin, topic, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, Dictionary properties, long delayInMillis) {
        return sendEvent(eventAdmin, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, Map properties, long delayInMillis) {
        return sendEvent(eventAdmin, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, Event event, long delayInMillis) {
        return sendEvent(eventAdmin, event, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, long delayInMillis) {
        return sendEvent(bc, topic, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, Dictionary properties, long delayInMillis) {
        return sendEvent(bc, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, Map properties, long delayInMillis) {
        return sendEvent(bc, topic, properties, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(BundleContext bc, Event event, long delayInMillis) {
        return sendEvent(bc, event, delayInMillis, TimeUnit.MILLISECONDS);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, long delay, TimeUnit timeUnit) {
        return sendEvent(eventAdmin, new Event(topic, (Map) null), delay, timeUnit);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, Dictionary properties, long delay, TimeUnit timeUnit) {
        return sendEvent(eventAdmin, new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> sendEvent(EventAdmin eventAdmin, String topic, Map properties, long delay, TimeUnit timeUnit) {
        return sendEvent(eventAdmin, new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, long delay, TimeUnit timeUnit) {
        return sendEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, (Map) null), delay, timeUnit);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, Dictionary properties, long delay, TimeUnit timeUnit) {
        return sendEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> sendEvent(BundleContext bc, String topic, Map properties, long delay, TimeUnit timeUnit) {
        return sendEvent(ServiceUtils.getService(bc, EventAdmin.class), new Event(topic, properties), delay, timeUnit);
    }

    public static Future<?> sendEvent(BundleContext bc, final Event event, long delay, TimeUnit timeUnit) {
        return sendEvent(ServiceUtils.getService(bc, EventAdmin.class), event, delay, timeUnit);
    }

    public static Future<?> sendEvent(final EventAdmin eventAdmin, final Event event, long delay, TimeUnit timeUnit) {
        if (eventAdmin == null) {
            throw new NullPointerException("EventAdmin is null");
        }
        return Executors.newSingleThreadScheduledExecutor().schedule(new Callable<Object>() {
            public Object call() throws Exception {
                eventAdmin.sendEvent(event);
                return null;
            }
        }, delay, timeUnit);
    }

    // wait for event
    public static Event waitForEvent(BundleContext bc, String topic, long timeoutInMillis) {
        return waitForEvent(bc, topic, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static Event waitForEvent(BundleContext bc, String topic, Filter filter, long timeoutInMillis) {
        return waitForEvent(bc, topic, filter, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static Event waitForEvent(BundleContext bc, String[] topics, long timeoutInMillis) {
        return waitForEvent(bc, topics, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static Event waitForEvent(BundleContext bc, String[] topics, Filter filter, long timeoutInMillis) {
        return waitForEvent(bc, topics, filter, timeoutInMillis, TimeUnit.MILLISECONDS);
    }

    public static Event waitForEvent(BundleContext bc, String topic, long timeout, TimeUnit timeUnit) {
        return waitForEvent(bc, topic, null, timeout, timeUnit);
    }

    public static Event waitForEvent(BundleContext bc, String topic, Filter filter, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        EventHandlerImpl handler = new EventHandlerImpl(latch);
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        if (topic != null) {
            props.put(EventConstants.EVENT_TOPIC, topic);
        }
        if (filter != null) {
            props.put(EventConstants.EVENT_FILTER, filter.toString());
        }
        ServiceRegistration registration = bc.registerService(EventHandler.class.getName(), handler, props);

        try {
            return waitForEvent(handler, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            registration.unregister();
        }
    }

    public static Event waitForEvent(BundleContext bc, String[] topics, long timeout, TimeUnit timeUnit) {
        return waitForEvent(bc, topics, null, timeout, timeUnit);
    }

    public static Event waitForEvent(BundleContext bc, String[] topics, Filter filter, long timeout, TimeUnit timeUnit) {
        CountDownLatch latch = new CountDownLatch(1);

        long timeoutInMillis = timeUnit.toMillis(timeout);
        EventHandlerImpl handler = new EventHandlerImpl(latch);
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        if (topics != null) {
            props.put(EventConstants.EVENT_TOPIC, topics);
        }
        if (filter != null) {
            props.put(EventConstants.EVENT_FILTER, filter.toString());
        }
        ServiceRegistration registration = bc.registerService(EventHandler.class.getName(), handler, props);

        try {
            return waitForEvent(handler, timeoutInMillis, latch);
        } catch (InterruptedException e) {
            return null;
        } finally {
            registration.unregister();
        }
    }

    private static Event waitForEvent(EventHandlerImpl handler, long timeoutInMillis, CountDownLatch latch)
            throws InterruptedException {
        if (timeoutInMillis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (latch.await(timeoutInMillis, TimeUnit.MILLISECONDS)) {
            return handler.getEvent();
        } else {
            return null;
        }
    }

    private static class EventHandlerImpl implements EventHandler {
        private CountDownLatch latch;

        private Event event;

        public EventHandlerImpl(CountDownLatch latch) {
            this.latch = latch;
        }

        public void handleEvent(Event event) {
            this.event = event;
            latch.countDown();
        }

        public Event getEvent() {
            return event;
        }
    }
}
