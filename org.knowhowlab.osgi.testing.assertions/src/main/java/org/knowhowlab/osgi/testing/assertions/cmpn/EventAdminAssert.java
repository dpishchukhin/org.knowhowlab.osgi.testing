package org.knowhowlab.osgi.testing.assertions.cmpn;

import org.knowhowlab.osgi.testing.assertions.OSGiAssert;

/**
 * A set of OSGi EventAdmin specific assertion methods useful for writing tests.
 * <p/>
 * Before use it could be initialized with default BundleContext
 * {@link OSGiAssert#setDefaultBundleContext(org.osgi.framework.BundleContext)}
 *
 * @author dmytro.pishchukhin
 * @version 1.0
 * @see java.lang.AssertionError
 * @see org.knowhowlab.osgi.testing.assertions.OSGiAssert
 */
public class EventAdminAssert extends OSGiAssert {
    /**
     * Utility class. Only static methods are available.
     */
    private EventAdminAssert() {
    }
}
