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

package org.knowhowlab.osgi.testing.it.testbundle;

import org.knowhowlab.osgi.testing.it.testbundle.service.Echo;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.Dictionary;
import java.util.Hashtable;

import static java.lang.Thread.sleep;

/**
 * Test bundle activator
 *
 * @author dmytro.pishchukhin
 */
public class Activator implements BundleActivator {
    private ServiceRegistration registration;

    public void start(final BundleContext bundleContext) throws Exception {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing
                }
                Dictionary<String, String> props = new Hashtable<String, String>();
                props.put("testkey", "testvalue");
                registration = bundleContext.registerService(Echo.class.getName(), new EchoImpl(), props);
            }
        }.start();
    }

    public void stop(BundleContext bundleContext) throws Exception {
        if (registration == null) {
            sleep(1000);
        }
        registration.unregister();
    }
}
