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

import org.osgi.framework.Filter;
import org.osgi.service.wireadmin.Wire;
import org.osgi.service.wireadmin.WireAdminEvent;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * OSGi WireAdmin utilities class
 *
 * @author dpishchukhin
 * @see org.osgi.service.wireadmin.Wire
 * @see org.osgi.service.wireadmin.WireAdmin
 * @see org.osgi.service.wireadmin.WireAdminEvent
 * @see org.osgi.service.wireadmin.WireAdminListener
 * @see org.osgi.service.wireadmin.WireConstants
 */
public class WireAdminUtils {
    /**
     * Utility class. Only static methods are available.
     */
    private WireAdminUtils() {
    }

    // wires create
    public static Future<Wire> createWire(String producerPid, String consumerPid, Dictionary properties, long delayInMillis) {
        return createWire(producerPid, consumerPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Wire> createWire(String producerPid, String consumerPid, Map properties, long delayInMillis) {
        return createWire(producerPid, consumerPid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<Wire> createWire(String producerPid, String consumerPid, Dictionary properties, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<Wire> createWire(String producerPid, String consumerPid, Map properties, long delay, TimeUnit timeUnit) {
        return createWire(producerPid, consumerPid, toDictionary(properties), delay, timeUnit);
    }

    // wire find
    public static Wire getWire(String wirePid) {
        // todo
        return null;
    }

    public static Wire[] getWires(String producerPid, String consumerPid) {
        // todo
        return null;
    }

    public static Wire[] getWires(String producerPid, String consumerPid, String filter) {
        // todo
        return null;
    }

    public static Wire[] getWires(String producerPid, String consumerPid, Filter filter) {
        // todo
        return null;
    }

    public static Wire[] getWires(Filter filter) {
        // todo
        return null;
    }

    // wire delete
    public static Future<String> deleteWire(String wirePid, long delayInMillis) {
        return deleteWire(wirePid, delayInMillis, MILLISECONDS);
    }

    public static Future<String> deleteWire(String wirePid, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, long delayInMillis) {
        return deleteWires(producerPid, consumerPid, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, String filter, long delayInMillis) {
        return deleteWires(producerPid, consumerPid, filter, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, Filter filter, long delayInMillis) {
        return deleteWires(producerPid, consumerPid, filter, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteWires(Filter filter, long delayInMillis) {
        return deleteWires(filter, delayInMillis, MILLISECONDS);
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, String filter, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<String[]> deleteWires(String producerPid, String consumerPid, Filter filter, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<String[]> deleteWires(Filter filter, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    // wire update
    public static Future<String> updateWire(String wirePid, Dictionary properties, long delayInMillis) {
        return updateWire(wirePid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<String> updateWire(String wirePid, Map properties, long delayInMillis) {
        return updateWire(wirePid, properties, delayInMillis, MILLISECONDS);
    }

    public static Future<String> updateWire(String wirePid, Dictionary properties, long delay, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static Future<String> updateWire(String wirePid, Map properties, long delay, TimeUnit timeUnit) {
        return updateWire(wirePid, toDictionary(properties), delay, timeUnit);
    }

    // wire events
    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String wirePid, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, wirePid, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String wirePid, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, producerPid, consumerPid, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, String filter, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, producerPid, consumerPid, filter, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, String filter, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, Filter filter, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, producerPid, consumerPid, filter, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, String producerPid, String consumerPid, Filter filter, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, Filter filter, long timeoutInMillis) {
        return waitForWireAdminEvent(eventTypeMask, filter, timeoutInMillis, MILLISECONDS);
    }

    public static WireAdminEvent waitForWireAdminEvent(int eventTypeMask, Filter filter, long timeout, TimeUnit timeUnit) {
        // todo
        return null;
    }

    private static Dictionary toDictionary(Map properties) {
        if (properties == null) {
            return null;
        }
        return new Hashtable<Object, Object>(properties);
    }
}
