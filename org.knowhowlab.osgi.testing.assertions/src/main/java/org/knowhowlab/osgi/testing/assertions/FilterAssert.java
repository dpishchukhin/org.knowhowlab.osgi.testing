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

package org.knowhowlab.osgi.testing.assertions;

import org.junit.Assert;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;

/**
 * A set of OSGi Filter specific assertion methods useful for writing tests.
 * <p/>
 * Before use it should be initialized
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.0
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class FilterAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private FilterAssert() {
    }

    /**
     * Asserts that given filter is correct. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter filter
     */
    public static void assertFilterCorrect(String filter) {
        assertFilterCorrect(null, filter);
    }

    /**
     * Asserts that given filter is correct. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  filter
     */
    public static void assertFilterCorrect(String message, String filter) {
        Assert.assertNotNull("Filter is null", filter);
        try {
            FrameworkUtil.createFilter(filter);
        } catch (InvalidSyntaxException e) {
            Assert.fail(message);
        }
    }

    /**
     * Asserts that given filter is incorrect. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter filter
     */
    public static void assertFilterIncorrect(String filter) {
        assertFilterIncorrect(null, filter);
    }

    /**
     * Asserts that given filter is incorrect. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  filter
     */
    public static void assertFilterIncorrect(String message, String filter) {
        Assert.assertNotNull("Filter is null", filter);
        try {
            FrameworkUtil.createFilter(filter);
            Assert.fail(message);
        } catch (InvalidSyntaxException e) {
            // do nothing
        }
    }
}
