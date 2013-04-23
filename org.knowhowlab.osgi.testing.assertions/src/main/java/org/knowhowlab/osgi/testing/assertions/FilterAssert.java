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

import org.osgi.framework.InvalidSyntaxException;

import static java.lang.String.format;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.osgi.framework.FrameworkUtil.createFilter;

/**
 * A set of OSGi Filter specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
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
     * @since 1.0
     */
    public static void assertFilterCorrect(String filter) {
        assertFilterCorrect(format("Filter is incorrect: %s", filter), filter);
    }

    /**
     * Asserts that given filter is correct. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  filter
     * @since 1.0
     */
    public static void assertFilterCorrect(String message, String filter) {
        assertNotNull("Filter is null", filter);
        try {
            createFilter(filter);
        } catch (InvalidSyntaxException e) {
            fail(message);
        }
    }

    /**
     * Asserts that given filter is incorrect. If it not as expected
     * {@link AssertionError} without a message is thrown
     *
     * @param filter filter
     * @since 1.0
     */
    public static void assertFilterIncorrect(String filter) {
        assertFilterIncorrect(format("Filter is correct: %s", filter), filter);
    }

    /**
     * Asserts that given filter is incorrect. If it not as expected
     * {@link AssertionError} is thrown with the given message
     *
     * @param message message
     * @param filter  filter
     * @since 1.0
     */
    public static void assertFilterIncorrect(String message, String filter) {
        assertNotNull("Filter is null", filter);
        try {
            createFilter(filter);
            fail(message);
        } catch (InvalidSyntaxException e) {
            // do nothing
        }
    }
}
