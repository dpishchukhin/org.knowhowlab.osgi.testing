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

package org.knowhowlab.osgi.testing.assertions;

import org.junit.Assert;
import org.osgi.framework.BundleContext;

import static org.osgi.framework.FrameworkUtil.getBundle;

/**
 * Abstract OSGi Asset class with BundleContext Handling
 *
 * @author dpishchukhin
 * @see java.lang.AssertionError
 * @see org.osgi.framework.BundleContext
 */
public abstract class OSGiAssert {
    /**
     * Default BundleContext value
     */
    private static BundleContext defaultBundleContext;

    /**
     * Set default BundleContext for OSGi assertions
     *
     * @param defaultBundleContext BundleContext value
     * @since 1.0
     */
    public static void setDefaultBundleContext(BundleContext defaultBundleContext) {
        OSGiAssert.defaultBundleContext = defaultBundleContext;
    }

    /**
     * Asserts BundleContext before return.
     *
     * @return BundleContext
     * @since 1.0
     */
    public static BundleContext getBundleContext() {
        BundleContext bc = defaultBundleContext == null
                ? getBundle(OSGiAssert.class).getBundleContext()
                : defaultBundleContext;
        Assert.assertNotNull("BundleContext is null", bc);
        return bc;
    }
}
